package exec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.jgrapht.Graph;

import dm.Company;
import dm.CompanyLite;
import dm.CompanyOfficer;
import dm.Edge;
import dm.Vertex;
import enums.api.OfficerRole;
import exec.MapCompanyOfficers.Scenario;
import util.GraphUtil;
import util.IOUtil;

/**
 * This class provides an application that performs post-processing and analysis on the graphs
 * produced by the mapping applications.
 * 
 * Post processing steps to develop:
 * 1) Compute some relevant statistics about the graph structure
 * 
 * @author nrowell
 */
public final class CreateDirectorsList {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(CreateDirectorsList.class.getName());
	
	/**
	 * Private default constructor prevents instantiation.
	 */
	private CreateDirectorsList() {}
	
	/**
	 * The main application entry point.
	 * 
	 * @param args
	 * 	The command line arguments (ignored).
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		// Pick the scenario to execute
//		Scenario scenario = Scenario.PUBLIC_LSE;
		Scenario scenario = Scenario.PUBLIC_NON_LSE;
//		Scenario scenario = Scenario.PRIVATE;
		
		logger.info("Scenario: " + scenario.name());
		
		// Input directory containing the graph
		File inputDir = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/API/" + scenario.name());
		
		// Top level directory for outputs
		File outputDirTopLevel = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/Results/");
		
		// Location for outputs for the chosen Scenario
		File outputDir = new File(outputDirTopLevel, scenario.toString());
		outputDir.mkdir();
		
		// Deserialize the graph
		Graph<Vertex, Edge> graph = IOUtil.deserializeGraph(new File(inputDir, scenario.toString() + ".ser"), Edge.class);
		
		// Basic sanity checks and graph info
		GraphUtil.sanityChecks(graph);
		
		// Purge non-directors
		GraphUtil.purgeRoles(graph, new HashSet<OfficerRole> (Arrays.asList(OfficerRole.director)));
		
		// Merge duplicated officers
		GraphUtil.analyseOfficerMultiplicity(graph, outputDir);
		
		// Write a file listing each director for each company
		File directorsFile = new File(outputDir, "directors.txt");
		
		BufferedWriter out = new BufferedWriter(new FileWriter(directorsFile));
		
		// Write header
		out.write("CompanyNumber,CompanyName,DirectorName,Independent?,AppointmentsLink\n");
		
		// Iterate over companies; write list of directors
		for(Vertex v : graph.vertexSet()) {
			if(v instanceof CompanyLite) {
				
				Company company = IOUtil.deserialize(new File(((CompanyLite)v).file_link), Company.class);
				
				// Get company directors
				Set<CompanyOfficer> directors = new HashSet<>();
				
				for(Edge edge : graph.edgesOf(v)) {
					
					Vertex s = graph.getEdgeSource(edge);
					Vertex t = graph.getEdgeTarget(edge);
					
					if(s instanceof CompanyOfficer) {
						directors.add((CompanyOfficer) s);
					}
					else {
						directors.add((CompanyOfficer) t);
					}
				}
				
				// Write to file
				for(CompanyOfficer director : directors) {
					
					out.write("\""+company.company_number+"\"");
					out.write(",");
					out.write("\""+company.company_name+"\"");
					out.write(",");
					out.write("\""+director.name+"\"");
					out.write(",");
					out.write("Y/N");
					out.write(",");
					out.write(director.links.officer.appointments);
					out.newLine();
				}
				out.newLine();
			}
		}
		out.close();
	}
	
}
