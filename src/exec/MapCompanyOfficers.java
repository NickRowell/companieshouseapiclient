package exec;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.Graph;
import org.jgrapht.graph.Multigraph;

import dm.Appointments;
import dm.Company;
import dm.CompanyLite;
import dm.CompanyOfficer;
import dm.CompanyOfficerLite;
import dm.CompanyOfficers;
import dm.Edge;
import dm.Vertex;
import enums.api.CompanyStatus;
import enums.api.OfficerRole;
import util.Counter;
import util.IOUtil;
import util.ParseUtil;
import util.QueryUtil;
import util.RecursiveFileStore;

/**
 * This application uses the Companies House API to map the network of companies and officers.
 *
 * TODO: add fields of the CompanyOfficer etc that are missing
 * TODO: update enum types
 * TODO: move some subclasses out to dm package and reuse
 *
 * @author nrowell
 * @version $Id$
 */
public class MapCompanyOfficers {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(MapCompanyOfficers.class.getName());
	
	/**
	 * The three mapping scenarios.
	 */
	public static enum Scenario {
		PRIVATE,
		PUBLIC_LSE,
		PUBLIC_NON_LSE;
	}
	
	/**
	 * List of {@link OfficerRoles} to process. Other roles will be ignored when building the graph.
	 */

	// All officer roles
	static List<OfficerRole> officerRoles = Arrays.asList(OfficerRole.values());
	
	// Directors only
//	static List<OfficerRole> officerRoles = Arrays.asList(OfficerRole.director);
	
	// Extended directors list
//	static List<OfficerRole> officerRoles = Arrays.asList(OfficerRole.director, OfficerRole.corporateDirector, 
//			OfficerRole.corporateNomineeDirector, OfficerRole.nomineeDirector);	
	
	/**
	 * Main application entry point.
	 * 
	 * @param args
	 * 	The command line args (ignored).
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ParseException
	 * 	If there's a problem parsing the epoch from a string.
	 */
	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
		
		// Pick the scenario to execute
//		Scenario scenario = Scenario.PUBLIC_LSE;
//		Scenario scenario = Scenario.PUBLIC_NON_LSE;
		Scenario scenario = Scenario.PRIVATE;
		
		// Input directory (containing company numbers lists)
		File inputDir = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/FreeDataProduct/");
		
		// Path to file containing company numbers for UK companies that trade shares on the London Stock Exchange Main Market
		File lseFile = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/LondonStockExchange/ukMainMarketCompanyNumbers.txt");
		
		// Output directory (for map data and serialised companies/officers)
		File outputDir = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/API/" + scenario.name());
		outputDir.mkdirs();
		
		// Top level directory for serialised Company types
		File companyDir = new File(outputDir, "Company");
		companyDir.mkdir();

		// Top level directory for serialised CompanyOfficer types
		File officerDir = new File(outputDir, "CompanyOfficer");
		officerDir.mkdir();
		
		// Path to file containing the company numbers to be mapped
		File companyNumbersFile = null;
		
		switch(scenario) {
		case PRIVATE:
			companyNumbersFile = new File(inputDir, "privCompanies.txt");
			break;
		case PUBLIC_LSE:
		case PUBLIC_NON_LSE:
			companyNumbersFile = new File(inputDir, "pubCompanies.txt");
		}
		
		// Read company numbers, convert to order-preserving Set
		Set<String> companyNumbersSet = new LinkedHashSet<String>(ParseUtil.parseCompanyNumbers(companyNumbersFile));
		
		logger.info("Loaded " + companyNumbersSet.size() + " company numbers to search.");
		
		// Optionally load details of companies trading on London Stock Exchange
		if(scenario == Scenario.PUBLIC_LSE || scenario == Scenario.PUBLIC_NON_LSE) {
			
			// Read company numbers, convert to order-preserving Set
			Set<String> lseCompanyNumbers = new LinkedHashSet<String>(ParseUtil.parseCompanyNumbers(lseFile));
			
			logger.info("Loaded " + lseCompanyNumbers.size() + " company numbers from the LSE data file");
			
			if(scenario == Scenario.PUBLIC_LSE) {
				// Process only company numbers IN London Stock Exchange file
				companyNumbersSet.retainAll(lseCompanyNumbers);
			}
			else {
				// Process only company numbers NOT IN London Stock Exchange file
				companyNumbersSet.removeAll(lseCompanyNumbers);
			}
			
			logger.info("Retained " + companyNumbersSet.size() + " company numbers to process");
		}
		
		// Create a file store used to store the complete Company data
		RecursiveFileStore companyFileStore = new RecursiveFileStore(companyDir, 16);
		RecursiveFileStore officerFileStore = new RecursiveFileStore(officerDir, 16);
		
		// Path to the file containing the map. Any existing map will be loaded and the region already mapped will be
		// eliminated from the current search. This allows the map to be built over several runs, to combat problems
		// with the connection.
		File mapFile = new File(outputDir, scenario.name() + ".ser");
		
		// Dump the graph to file every X companies added to the graph
		int graphSaveInterval = 100;
		
		Graph<Vertex, Edge> graph = null;
		
		if(mapFile.exists()) {
			
			logger.info("Loading partially completed graph from " + mapFile.getAbsolutePath());
			
			// Load partially completed graph from file
			graph = IOUtil.deserializeGraph(mapFile, Edge.class);
			
			// Purge company numbers already mapped
			int[] count = {0};
			graph.vertexSet().forEach((v) -> {if(v instanceof CompanyLite) {companyNumbersSet.remove(((CompanyLite)v).company_number); count[0]++;}});
			logger.info("Removed " + count[0] + " company numbers already mapped.");
			logger.info("Retained " + companyNumbersSet.size() + " unmapped company numbers to search.");
		}
		else {
			// Create empty graph
			graph = new Multigraph<>(Edge.class);
			logger.info("Created new empty graph");
		}
		
		// Types of fringe case detection:
		// 1) Officers link not found
		// 2) Officers with null date of birth
		Set<String> companiesWithOfficerLinksNotFound = new HashSet<>();
		// stats[0] - number of officers with null date of birth in officersList resource
		// stats[1] - of these, number of officers that also have null date of birth in appointments resource
		int[] stats = {0,0};
		
		int[] httpUrlResponseCode = new int[1];
		
		// Read company numbers to a queue so we can process them in sequence
		Queue<String> companyNumbersToMap = new LinkedList<>(companyNumbersSet);
		
		// Cache of Company and CompanyLites processed since last graph serialisation.
		List<Pair<Company, CompanyLite>> companiesProcessedSinceLastWrite = new LinkedList<>();

		// Cache of CompanyOfficer and CompanyOfficerLites processed since last graph serialisation.
		List<Pair<CompanyOfficer, CompanyOfficerLite>> companyOfficersProcessedSinceLastWrite = new LinkedList<>();
		
		// Log helpful progress messages
		Counter counter = new Counter(companyNumbersToMap.size());
        int companiesMapped = 0;
        
        counter.start(System.nanoTime());
        
		// Loop over company numbers to examine
		while(!companyNumbersToMap.isEmpty()) {

			// Pick the company number at the head of the queue to examine next
			String companyNumber = companyNumbersToMap.poll();
			
			// Look up the company
			Company company = QueryUtil.getCompany(companyNumber, httpUrlResponseCode);
			
			// Skip inactive companies. Due to time lag, a company listed as active in the Free Data Product may not be
			// by the time we look it up via the Companies House API.
			if(company.company_status != CompanyStatus.active) {
				continue;
			}
			
			// Company is being added to the graph - create a CompanyLite
			CompanyLite companyLite = new CompanyLite(company);
			
			// Cache these for storage to disk later
			companiesProcessedSinceLastWrite.add(Pair.of(company, companyLite));
			
			// Add the Company to the graph as a new Vertex
			graph.addVertex(companyLite);
			
			companiesMapped++;
			
			// Get the array of officers for this company
			CompanyOfficers officers = QueryUtil.getCompanyOfficers(companyNumber, httpUrlResponseCode);

			// Check for missing officers link
			if(httpUrlResponseCode[0] == HttpURLConnection.HTTP_NOT_FOUND) {
				companiesWithOfficerLinksNotFound.add(companyNumber);
				continue;
			}
			
			// Read into a Set
			Set<CompanyOfficer> officersSet = new HashSet<>();
			officersSet.addAll(Arrays.asList(officers.items));
			
			// Examine each officer of this company. We may have encountered some of these before but
			// they need to be processed again here in order to link them to the current company.
			// Multiple occurrences of the same officer with different appointments links will create
			// additional spurious vertices and contaminate the graph.
			for(CompanyOfficer officer : officersSet) {
				
				OfficerRole role = officer.officer_role;
				
				// Skip officers that have resigned
				if(officer.resigned_on != null) {
					// If we want to set an epoch for resignations:
					// Date epoch = new SimpleDateFormat( "yyyyMMdd" ).parse( "20200401");
					// if(officer.resigned_on.before(epoch)) {continue;}
					continue;
				}
				
				// Skip officers in irrelevant roles
				if(!officerRoles.contains(role)) {
					continue;
				}
				
				// Track officers with null date of birth fields in the officerList resource
				if(officer.date_of_birth == null) {
					stats[0]++;
					
					// Try and retrieve date of birth from the appointments resource
					Appointments appts = QueryUtil.getAppointments(officer.links.officer.appointments, httpUrlResponseCode);
					
					// Check for missing officers link
					if(httpUrlResponseCode[0] == HttpURLConnection.HTTP_NOT_FOUND || appts.date_of_birth == null) {
						// Date of birth still null
						stats[1]++;
					}
					else {
						// Copy date of birth from appointments resource
						officer.date_of_birth = appts.date_of_birth;
					}
				}
				
				// Add the new link(s) to the graph. These are matched by hashcode, so as long as two instances
				// of the same company/officer have the same hashcode then they'll only be stored in the graph
				// once.
				
				// CompanyOfficer is being added to the graph - create a CompanyOfficerLite
				CompanyOfficerLite companyOfficerLite = new CompanyOfficerLite(officer);

				// Cache these for storage to disk later
				companyOfficersProcessedSinceLastWrite.add(Pair.of(officer, companyOfficerLite));
				
				// The officer may already be in the graph if encountered when processing a previous company...
				graph.addVertex(companyOfficerLite);
				
				// ...however the connection is always new to the graph
				graph.addEdge(companyLite, companyOfficerLite, new Edge(role, companyLite, companyOfficerLite));
			}
			
			if(companiesMapped % graphSaveInterval == 0) {
				
				logger.info("Writing Company data to file...");
				
				// Store Companys to disk; set file paths in the CompanyLites; clear cache.
				for(Pair<Company, CompanyLite> pair : companiesProcessedSinceLastWrite) {
					pair.getRight().file_link = companyFileStore.storeObject(pair.getLeft()).getAbsolutePath();
				}
				companiesProcessedSinceLastWrite.clear();

				logger.info("Writing CompanyOfficer data to file...");
				
				// Store Companys to disk; set file paths in the CompanyLites; clear cache.
				for(Pair<CompanyOfficer, CompanyOfficerLite> pair : companyOfficersProcessedSinceLastWrite) {
					pair.getRight().file_link = officerFileStore.storeObject(pair.getLeft()).getAbsolutePath();
				}
				companyOfficersProcessedSinceLastWrite.clear();
				
				logger.info("Writing graph to file...");
				
				IOUtil.serialize(mapFile, graph);
				
				final int[] nCompanies = {0};
				final int[] nOfficers = {0};
				graph.vertexSet().forEach((v) -> {if(v instanceof CompanyLite) nCompanies[0]++; else nOfficers[0]++;});
				
				logger.info("Total number of companies / officers mapped = " + nCompanies[0] + " / " + nOfficers[0]);
				
				logger.info("Companies processed / still to process in this run: " + companiesMapped + " / " + companyNumbersToMap.size());
				logger.info("Estimated time remaining: " + counter.getTimeLeftMessage(companiesMapped));
				
				// Report officers with null date of birth
				logger.info("Officers with null date of birth in officerList resource:   " + stats[0] 
						+ " ("+String.format("%.2f%%", (100.0 * (double)stats[0]) / nOfficers[0])+")");
				logger.info(" - of these, number with null DoB in appointments resource: " + stats[1] 
						+ " ("+String.format("%.2f%%", (100.0 * (double)stats[1]) / nOfficers[0])+")");
				
				// Report companies with missing officers link
				logger.info("Companies with officers links not found in database, in this run: " + companiesWithOfficerLinksNotFound.size() 
				        + " ("+String.format("%.2f%%", (100.0 * (double)companiesWithOfficerLinksNotFound.size()) / companiesMapped)+")");
			}
		}
		
		// Final write of graph to file; overwrite existing graph

		logger.info("Writing Company data to file...");
		
		// Store Companys to disk; set file paths in the CompanyLites; clear cache.
		for(Pair<Company, CompanyLite> pair : companiesProcessedSinceLastWrite) {
			pair.getRight().file_link = companyFileStore.storeObject(pair.getLeft()).getAbsolutePath();
		}
		companiesProcessedSinceLastWrite.clear();

		logger.info("Writing CompanyOfficer data to file...");
		
		// Store Companys to disk; set file paths in the CompanyLites; clear cache.
		for(Pair<CompanyOfficer, CompanyOfficerLite> pair : companyOfficersProcessedSinceLastWrite) {
			pair.getRight().file_link = officerFileStore.storeObject(pair.getLeft()).getAbsolutePath();
		}
		companyOfficersProcessedSinceLastWrite.clear();
		
		logger.info("Writing graph to file...");
		
		IOUtil.serialize(mapFile, graph);
		
		final int[] nCompanies = {0};
		final int[] nOfficers = {0};
		graph.vertexSet().forEach((v) -> {if(v instanceof CompanyLite) nCompanies[0]++; else nOfficers[0]++;});
		
		logger.info("Total number of companies / officers mapped = " + nCompanies[0] + " / " + nOfficers[0]);
		
		logger.info("Companies processed / still to process in this run: " + companiesMapped + " / " + companyNumbersToMap.size());
		logger.info("Estimated time remaining: " + counter.getTimeLeftMessage(companiesMapped));
		
		// Report officers with null date of birth
		logger.info("Officers with null date of birth in officerList resource:   " + stats[0] 
				+ " ("+String.format("%.2f%%", (100.0 * (double)stats[0]) / nOfficers[0])+")");
		logger.info(" - of these, number with null DoB in appointments resource: " + stats[1] 
				+ " ("+String.format("%.2f%%", (100.0 * (double)stats[1]) / nOfficers[0])+")");
		
		// Report companies with missing officers link
		logger.info("Companies with officers links not found in database, in this run: " + companiesWithOfficerLinksNotFound.size() 
                + " ("+String.format("%.2f%%", (100.0 * (double)companiesWithOfficerLinksNotFound.size()) / companiesMapped)+")");
	}

}