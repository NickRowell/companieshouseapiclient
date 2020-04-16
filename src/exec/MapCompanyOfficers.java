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

import org.jgrapht.Graph;
import org.jgrapht.graph.Multigraph;

import dm.Appointments;
import dm.Company;
import dm.CompanyOfficer;
import dm.CompanyOfficers;
import dm.Edge;
import dm.Vertex;
import enums.api.CompanyStatus;
import enums.api.OfficerRole;
import util.Counter;
import util.IOUtil;
import util.ParseUtil;
import util.QueryUtil;

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
		
		// Path to file containing the company numbers to be mapped
		File input = null;
		
		switch(scenario) {
		case PRIVATE:
			input = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/FreeDataProduct/privCompanies.txt");
			break;
		case PUBLIC_LSE:
		case PUBLIC_NON_LSE:
			input = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/FreeDataProduct/pubCompanies.txt");
		}
		
		// Read company numbers, convert to order-preserving Set
		Set<String> companyNumbersSet = new LinkedHashSet<String>(ParseUtil.parseCompanyNumbers(input));
		
		logger.info("Loaded " + companyNumbersSet.size() + " company numbers to search.");
		
		// Optionally load details of companies trading on London Stock Exchange
		if(scenario == Scenario.PUBLIC_LSE || scenario == Scenario.PUBLIC_NON_LSE) {
			
			// Path to file containing company numbers for UK companies that trade shares on the London Stock Exchange Main Market
			File lseFile = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/LondonStockExchange/ukMainMarketCompanyNumbers.txt");
			
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
		
		// Path to the file containing the map. Any existing map will be loaded and the region already mapped will be
		// eliminated from the current search. This allows the map to be built over several runs, to combat problems
		// with the connection.
		File mapFile = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/API/" + scenario.name() + ".ser");
		
		// Dump the graph to file every X companies added to the graph
		int graphSaveInterval = 100;
		
		Graph<Vertex, Edge> graph = null;
		
		if(mapFile.exists()) {
			
			logger.info("Loading partially completed graph from " + mapFile.getAbsolutePath());
			
			// Load partially completed graph from file
			graph = IOUtil.deserializeGraph(mapFile, Edge.class);
			
			// Purge company numbers already mapped
			int[] count = {0};
			graph.vertexSet().forEach((v) -> {if(v instanceof Company) {companyNumbersSet.remove(((Company)v).company_number); count[0]++;}});
			logger.info("Removed " + count[0] + " company numbers already mapped.");
			logger.info("Retained " + companyNumbersSet.size() + " unmapped company numbers to search.");
		}
		else {
			// Create empty graph
			graph = new Multigraph<>(Edge.class);
			logger.info("Created new empty graph");
		}
		
		// Set the date 
		
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
			
			// Add the Company to the graph as a new Vertex
			graph.addVertex(company);
			
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
				
				// The officer may already be in the graph if encountered when processing a previous company...
				graph.addVertex(officer);
				
				// ...however the connection is always new to the graph
				graph.addEdge(company, officer, new Edge(role, company, officer));
			}
			
			if(companiesMapped % graphSaveInterval == 0) {
				
				logger.info("Writing graph to file...");
				
				IOUtil.serialize(mapFile, graph);
				
				final int[] nCompanies = {0};
				final int[] nOfficers = {0};
				graph.vertexSet().forEach((v) -> {if(v instanceof Company) nCompanies[0]++; else nOfficers[0]++;});
				
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

		logger.info("Writing graph to file...");
		
		IOUtil.serialize(mapFile, graph);
		
		final int[] nCompanies = {0};
		final int[] nOfficers = {0};
		graph.vertexSet().forEach((v) -> {if(v instanceof Company) nCompanies[0]++; else nOfficers[0]++;});
		
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