package exec;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DefaultUndirectedGraph;

import dm.Appointment;
import dm.Appointments;
import dm.Company;
import dm.CompanyOfficer;
import dm.CompanyOfficers;
import dm.Vertex;
import util.GraphDisplay;
import util.QueryUtil;

/**
 * This application uses the Companies House API to map the network of companies and officers.
 * 
 * It performs a recursive search from a fixed starting pointm adding new unmapped companies to the
 * set to be mapped as it encounters them. This is the method that was implemented initially before
 * the full list of company numbers became available and made this method obsolete.
 *
 * TODO: make sure we're handling multiplicity of officers correctly
 * TODO: verify we're not covering the same ground twice
 * TODO: implement recursive automated mapping of company/officer network
 * TODO: add fields of the CompanyOfficer etc that are missing
 * TODO: update enum types
 * TODO: move some subclasses out to dm package and reuse
 *
 * @author nrowell
 * @version $Id$
 */
public class MapCompanyOfficersRecursive {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(MapCompanyOfficersRecursive.class.getName());
	
	/**
	 * Main application entry point.
	 * 
	 * @param args
	 * 	The command line args (ignored).
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// Company numbers in a queue
		Queue<Company> companiesToExamine = new LinkedList<>();

		int[] httpUrlResponseCode = new int[1];
		// Use a fiducial starting point for testing
//		String startingCompanyNumber = "11432768"; // Oakwood Bespoke
//		String startingCompanyNumber = "00233462"; // John Lewis
		// This one terminates quickly and is isolated:
		String startingCompanyNumber = "SC573784"; // MELVILLE WHITSON & MUIR LTD
		Company startingCompany = QueryUtil.getCompany(startingCompanyNumber, httpUrlResponseCode);
		
		companiesToExamine.add(startingCompany);
		
		// TODO: load partially completed graph from a file...?
		// TODO: save the graph to a file.
		
		// Set of all companies already examined
		Set<Company> companiesExamined = new HashSet<>();
		
		// Set of all company officers examined.
		Set<CompanyOfficer> companyOfficersExamined = new HashSet<>();
		
		// Build JGraphT object
		// TODO try undirected graph
//		Graph<Vertex, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
		// For visualisation to work in real time this must be a ListenableGraph
		ListenableGraph<Vertex, DefaultEdge> graph = new DefaultListenableGraph<>(new DefaultUndirectedGraph<>(DefaultEdge.class));
		
		// Set up visualisation
		GraphDisplay<Vertex, DefaultEdge> graphDisplay = new GraphDisplay<>(graph);
		
		JFrame frame = new JFrame();
        frame.getContentPane().add(graphDisplay);
        frame.setTitle("JGraphT Adapter to JGraphX Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
		
		// Loop over company numbers to examine
		while(!companiesToExamine.isEmpty()) {

			// Pick the company number at the head of the queue to examine next
			Company company = companiesToExamine.poll();
			companiesExamined.add(company);
			
			// Get all officers of this company
			Set<CompanyOfficer> officers = getOfficers(company.company_number);
			
			// Logging
			System.out.println("Companies examined: " + companiesExamined.size());
			System.out.println("Companies to be examined: " + companiesToExamine.size());
			System.out.println("Officers examined:  " + companyOfficersExamined.size() + " " + Arrays.toString(companyOfficersExamined.toArray(new CompanyOfficer[0])));
			
			// Examine each officer of this company. We may have encountered some of these before but
			// they need to be processed again here in order to link them to the current company.
			graphDisplay.beginUpdate();
			for(CompanyOfficer officer : officers) {
				
				// Get the companies associated with this officer
				Set<Company> companies = getCompanies(officer);
				
				// Remove any already examined (including the current one), to avoid covering the same ground more than once.
				companies.removeAll(companiesExamined);
				
				// Remove any already on the list to examine (not really necessary if the set works correctly)
				companies.removeAll(companiesToExamine);
				
				// Add the rest to the set of companies to examine
				companiesToExamine.addAll(companies);
				
				// Add the officer to the set of examined officers
				companyOfficersExamined.add(officer);
				
				// Add the new link(s) to the graph
				graph.addVertex(company);
				graph.addVertex(officer);
				graph.addEdge(company, officer);
				
				System.out.println("Linking: " + company.toString() + " -> " + officer.toString() + ": " + officer.links.officer.appointments);
			}
			
			// Update layout of graph visualisation
			graphDisplay.endUpdate();
			
		}
	}
	
	/**
	 * Get the {@link Set} of all {@link Company} that a given {@link CompanyOfficer} is
	 * appointed to.
	 * 
	 * @param officer
	 * 	The {@link CompanyOfficer}.
	 * @return
	 * 	The {@link Set} of all {@link Company} that the given {@link CompanyOfficer} is
	 * appointed to.
	 * @throws IOException
	 * 	If there's a problem communicating with the Companies House database
	 * @throws InterruptedException 
	 */
	private static Set<Company> getCompanies(CompanyOfficer officer) throws IOException, InterruptedException {

		int[] httpUrlResponseCode = new int[1];
		Appointments appointments = QueryUtil.getAppointments(officer.links.officer.appointments, httpUrlResponseCode);
		
		Set<Company> companies = new HashSet<>();
		
		for(Appointment app : appointments.items) {
			companies.add(QueryUtil.getCompany(app.appointed_to.company_number, httpUrlResponseCode));
		}
		
		return companies;
	}
	
	/**
	 * Get the {@link Set} of all {@link CompanyOfficer} for a given company number.
	 * 
	 * @param company
	 * 	The {@link Company}.
	 * @return
	 * 	The {@link Set} of all {@link CompanyOfficer} for the given {@link Company}.
	 * @throws IOException 
	 * 	If there's a problem communicating with the Companies House database
	 * @throws InterruptedException 
	 */
	private static Set<CompanyOfficer> getOfficers(String companyNumber) throws IOException, InterruptedException {

		int[] httpUrlResponseCode = new int[1];
		Company company = QueryUtil.getCompany(companyNumber, httpUrlResponseCode);
		
		return getOfficers(company);
	}

	/**
	 * Get the {@link Set} of all {@link CompanyOfficer} for a given {@link Company}.
	 * 
	 * @param company
	 * 	The {@link Company}.
	 * @return
	 * 	The {@link Set} of all {@link CompanyOfficer} for the given {@link Company}.
	 * @throws IOException 
	 * 	If there's a problem communicating with the Companies House database
	 * @throws InterruptedException 
	 */
	private static Set<CompanyOfficer> getOfficers(Company company) throws IOException, InterruptedException {

		int[] httpUrlResponseCode = new int[1];
		// Get the array of officers for this company
		CompanyOfficers officers = QueryUtil.getCompanyOfficers(company.links.officers, httpUrlResponseCode);
		
		Set<CompanyOfficer> officersSet = new HashSet<>();
		officersSet.addAll(Arrays.asList(officers.items));
		
		return officersSet;
	}
}