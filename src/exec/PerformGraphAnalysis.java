package exec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.Multigraph;

import dm.Company;
import dm.CompanyOfficer;
import dm.CompanyOfficers;
import dm.Edge;
import dm.Vertex;
import enums.api.OfficerRole;
import exec.MapCompanyOfficers.Scenario;
import util.CompaniesHouseUtil;
import util.CompaniesHouseUtil.OFFICER_EQUALITY_TEST_OUTCOME;
import util.Counter;
import util.GnuplotUtil;
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
public final class PerformGraphAnalysis {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(PerformGraphAnalysis.class.getName());
	
	/**
	 * Private default constructor prevents instantiation.
	 */
	private PerformGraphAnalysis() {}
	
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
		Scenario scenario = Scenario.PUBLIC_LSE;
//		Scenario scenario = Scenario.PUBLIC_NON_LSE;
//		Scenario scenario = Scenario.PRIVATE;
		
		// Input directory containing the graphs
		File inputDir = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/API/");
		
		// Top level directory for outputs
		File outputDirTopLevel = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/Results/");
		
		// Location for outputs for the chosen Scenario
		File outputDir = new File(outputDirTopLevel, scenario.toString() + "/");
		outputDir.mkdir();
		
		// Deserialize the graph
		Graph<Vertex, Edge> graph = IOUtil.deserializeGraph(new File(inputDir, scenario.toString() + ".ser"), Edge.class);
		
		// Basic sanity checks and graph info
		sanityChecks(graph);
		
		// Analyses to perform:
		
		// 0) Plot the distribution of different officer roles
		// TODO: add a percentage threshold below which any role type is added to an 'other' category
		plotOfficerRoleHistogram(graph, new File(outputDir, "officerRoles.png"));
		
		// Now purge the roles we're not interested in
		purgeRoles(graph, new HashSet<OfficerRole> (Arrays.asList(OfficerRole.director)));
		
		// Write graph interchange format pre-merger
		writeInterchangeFormat(graph, new HashMap<>(), new File(outputDir, scenario + "_PRE_MERGER.txt"));
		
		// 1) Analysis of officer multiplicity & purging of duplicated officers. Important to do
		//    this before any statistical analysis that is affected by officer multiplicity.
		Map<CompanyOfficer, Set<CompanyOfficer>> duplicatedOfficers = analyseOfficerMultiplicity(graph, outputDir);
		
		// Write graph interchange format post-merger
		writeInterchangeFormat(graph, duplicatedOfficers, new File(outputDir, scenario + "_POST_MERGER.txt"));
		
		// 2) Number of disconnected graphs (& their size distribution?)
		ConnectivityInspector<Vertex, Edge> connInsp = new ConnectivityInspector<>(graph);
		
		List<Set<Vertex>> connectedSets = connInsp.connectedSets();
		logger.info("Connected Sets = " + connectedSets.size());
		
		// 3) Distribution of number of directors per company
		// 4) Distribution of number of directorships per officer
		plotHistogram(graph, new File(outputDir, "numDirectorsPerCompany.png"), Company.class, "Number of directors", "Number of companies", "Frequency of number of directors", false);
		plotHistogram(graph, new File(outputDir, "numCompaniesPerDirector.png"), CompanyOfficer.class, "Number of directorships", "Number of directors", "Frequency of number of directorships", true);
		
		// 5) Statistics of the network density etc; cases of two or three-way cycles linking directors/companies

		
		// Set up visualisation
		
		// For visualisation to work in real time this must be a ListenableGraph
//		ListenableGraph<Vertex, DefaultEdge> graph = new DefaultListenableGraph<>(new DefaultUndirectedGraph<>(DefaultEdge.class));
//		
//		GraphDisplay<Vertex, DefaultEdge> graphDisplay = new GraphDisplay<>(graph);
//		
//		JFrame frame = new JFrame();
//        frame.getContentPane().add(graphDisplay);
//        frame.setTitle("JGraphT Adapter to JGraphX Demo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//
//		graphDisplay.beginUpdate();
//
//		// Add graph parts?
//		
//		graphDisplay.endUpdate();
	}
	
	/**
	 * Performs some sanity checks to ensure integrity of graph, and reports some basic statistics.
	 * 
	 * @param graph
	 * 	The {@link Graph} to analyse.
	 */
	private static void sanityChecks(Graph<Vertex, Edge> graph) {
		
		// 1) Edges should only ever connect a Company with a CompanyOfficer
		for(Edge edge : graph.edgeSet()) {
			Vertex v1 = graph.getEdgeSource(edge);
			Vertex v2 = graph.getEdgeTarget(edge);
			
			if(v1.getClass().isInstance(v2)) {
				throw new RuntimeException("Vertices are illegally connected!");
			}
			
		}
		
		logger.info("Passed sanity checks.");

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// Count officers with no date of birth
		int nCompanies = 0;
		int nOfficers = 0;
		int nullDob = 0;
		for(Vertex v : graph.vertexSet()) {
			if(v instanceof CompanyOfficer) {
				nOfficers++;
				CompanyOfficer officer = (CompanyOfficer)v;
				if(officer.date_of_birth == null) {
					nullDob++;
				}
			}
			else {
				nCompanies++;
			}
		}
		logger.info("Number of vertices = " + graph.vertexSet().size());
		logger.info("Number of edges = " + graph.edgeSet().size());
		logger.info("Number of companies / officers = " + nCompanies + " / " + nOfficers);
		logger.info("Number of officers with null DoB = " + nullDob + "\t("+String.format("%.2f", (100.0 * nullDob)/nOfficers)+"%)");
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// Check for officers with more than one role with the same company
		graph.vertexSet().forEach((v) -> {
			// Ignore officers - process only companies
			if(v instanceof CompanyOfficer) {
				return;
			}
			
			Company company = (Company)v;
			
			// Get all edges associated with this company
			Set<Edge> edges = graph.edgesOf(v);
			
			// Count the number of connections to each officer
			Map<CompanyOfficer, List<OfficerRole>> rolesPerOfficer = new HashMap<>();
			
			for(Edge edge : edges) {
				Vertex s = graph.getEdgeSource(edge);
				Vertex t = graph.getEdgeTarget(edge);
				
				CompanyOfficer officer = null;
				if(s instanceof CompanyOfficer) {
					officer = (CompanyOfficer) s;
				}
				else {
					officer = (CompanyOfficer) t;
				}
				
				if(!rolesPerOfficer.containsKey(officer)) {
					rolesPerOfficer.put(officer, new LinkedList<>());
				}
				rolesPerOfficer.get(officer).add(edge.role);
			}
			
			for(Entry<CompanyOfficer, List<OfficerRole>> entry : rolesPerOfficer.entrySet()) {
				CompanyOfficer officer = entry.getKey();
				List<OfficerRole> rolesList = entry.getValue();
				if(rolesList.isEmpty()) {
					throw new RuntimeException("Found no roles for " + officer.toString() + " -> " + company.toString());
				}
				
				if(rolesList.size() > 1) {
					System.out.println("Officer " + officer.toString() + " has " + rolesList.size() + " roles at " + company.toString() + ":");
					for(OfficerRole role : rolesList) {
						System.out.println(role);
					}
				}
			}
		});
		
	}
	
	/**
	 * Plots a histogram showing the distribution of number of connections for the given vertex type. This can be used to produce the
	 * two plot types:
	 *  - Distribution of the number of directors per company
	 *  - Distribution of the number of directorships per director
	 * 
	 * @param graph
	 * 	The {@link Graph}.
	 * @param plotFile
	 * 	The {@link File} to store the plot to.
	 * @param clazz
	 *	The class type of the vertex that is to be the subject of the histogram.
	 * @param xLabel
	 * 	The label for the X axis.
	 * @param yLabel
	 * 	The label for the Y axis.
	 * @param title
	 * 	The title for the plot.
	 * @param logy
	 * 	Boolean flag specifying whether the Y axis should be logarithmic or linear.
	 * @throws IOException
	 * 	If there's a problem writing the Gnuplot script to file or saving the image.
	 * @throws InterruptedException
	 * 	If there's a problem executing the Gnuplot process.
	 */
	private static void plotHistogram(Graph<Vertex, Edge> graph, File plotFile, Class<?> clazz, String xLabel, String yLabel, String title, boolean logy) throws IOException, InterruptedException {
		
		// Accumulates number of vertices of the desired type (value) that have X edges (key)
		SortedMap<Integer, int[]> map = new TreeMap<>();
		
		// Loop over vertices; for vertices matching the input type, count the number of edges.
		for(Vertex v : graph.vertexSet()) {
			if(clazz.isInstance(v)) {
				Integer num = new Integer(graph.edgesOf(v).size());
				if(!map.containsKey(num)) {
					map.put(num, new int[] {0});
				}
				map.get(num)[0]++;
			}
		}
		
		// Get the upper limit
		int maxX = (int)map.lastKey();

		// Plot a bar chart
		StringBuilder sb = new StringBuilder();
		sb.append("set terminal pngcairo font \"helvetica,14\" size 1280, 480\n");
		sb.append("set output '" + plotFile.getAbsolutePath() + "'\n");
		sb.append("set boxwidth 0.75\n");
		sb.append("set style fill solid\n");
		sb.append("set key off\n");
		sb.append("set xrange [-0.5:"+(maxX + 0.5)+"]\n");
		sb.append("set xtics 5 out\n");
		sb.append("set mxtics 5\n");
		sb.append("set ytics out\n");
		
		sb.append("set style line 20 lc rgb '#ddccdd' lt 1 lw 1.5\n");
		sb.append("set style line 21 lc rgb '#ddccdd' lt 1 lw 0.5\n");
		sb.append("set grid ytics mytics back ls 20, ls 21\n");
		
		if(logy) {
			sb.append("set logscale y\n");
			sb.append("set yrange [0:*]\n");
		}
		else {
			sb.append("set yrange [0:*]\n");
		}
		
		sb.append("set xlabel '"+xLabel+"'\n");
		sb.append("set ylabel '"+yLabel+"'\n");
		sb.append("set title '"+title+"'\n");
		sb.append("plot '-' using 1:2 with boxes\n");
		
		for(int x=0; x<=maxX; x++) {
			int y = 0;
			if(map.containsKey(x)) {
				y = map.get(x)[0];
			}
			sb.append(x + "\t" + y + "\n");
		}
		sb.append("e\n");
		
		GnuplotUtil.executeScript(sb.toString(), logger);
	}

	/**
	 * Plots a histogram of the distribution of {@link OfficerRole} in the graph.
	 * 
	 * @param graph
	 * 	The {@link Graph} to be plotted.
	 * @param plotFile
	 * 	The {@link File} to store the plot image.
	 * @throws IOException
	 * 	If there's a problem writing the plot script or image.
	 * @throws InterruptedException
	 * 	If there's a problem with the external Gnuplot process used to do the plotting.
	 */
	private static void plotOfficerRoleHistogram(Graph<Vertex, Edge> graph, File plotFile) throws IOException, InterruptedException {
		
		// Accumulate histogram of officer roles
		Map<OfficerRole, int[]> roles = new HashMap<>();
		
		graph.edgeSet().forEach((e) -> {
			Edge edge = (Edge)e;
			OfficerRole role = edge.role;
			if(!roles.containsKey(role)) {
				roles.put(role, new int[]{0});
			}
			roles.get(role)[0]++;
			});
		
		// Sort into descending order
		final Map<OfficerRole, int[]> sortedRoles = roles.entrySet().stream().sorted(CompaniesHouseUtil.officerRoleEntryComp)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		// Plot a bar chart
		StringBuilder sb = new StringBuilder();
		sb.append("set terminal pngcairo font \"helvetica,14\" size 1280, 480\n");
		sb.append("set output '" + plotFile.getAbsolutePath() + "'\n");
		sb.append("set boxwidth 0.75\n");
		sb.append("set style fill solid\n");
		sb.append("set key off\n");
		sb.append("set xtic rotate by -45 scale 0\n");
		
		sb.append("set style line 20 lc rgb '#ddccdd' lt 1 lw 1.5\n");
		sb.append("set style line 21 lc rgb '#ddccdd' lt 1 lw 0.5\n");
		sb.append("set grid ytics mytics back ls 20, ls 21\n");
		
		sb.append("set yrange [0:*]\n");
		
		sb.append("set xlabel 'Officer Role'\n");
		sb.append("set ylabel 'Number'\n");
		sb.append("set title 'Distribution of officer roles'\n");
		sb.append("plot '-' using 2:xtic(1) with boxes, '' using 0:($2+300):2 with labels\n");
		
		sortedRoles.entrySet().forEach((e) -> {sb.append("\"" + e.getKey().toString().replace(" ", "\\n") + "\"\t" + e.getValue()[0] + "\n");});
		
		sb.append("e\n");
		
		sortedRoles.entrySet().forEach((e) -> {sb.append("\"" + e.getKey().toString().replace(" ", "\\n") + "\"\t" + e.getValue()[0] + "\n");});
		
		sb.append("e\n");
		
		GnuplotUtil.executeScript(sb.toString(), logger);
	}

	/**
	 * Method to purge from the {@link Graph} any {@link Edge}s that are not of the desired type(s),
	 * 
	 * @param graph
	 * 	The {@link Graph} to be purged.
	 * @param rolesToKeep
	 * 	A {@link List} of {@link OfficerRole}s enumerating the roles to be kept, i.e. all {@link Edge}s
	 * corresponding to {@link OfficerRole}s NOT in this list will be purged.
	 */
	private static void purgeRoles(Graph<Vertex, Edge> graph, Set<OfficerRole> rolesToKeep) {
		
		Set<Edge> edgesToPurge = new HashSet<>();
		
		for(Edge edge : graph.edgeSet()) {
			if(!rolesToKeep.contains(edge.role)) {
				edgesToPurge.add(edge);
			}
		}
		
		logger.info("Removing " + edgesToPurge.size() + " Edges that don't correspond to: " + 
				Arrays.toString(rolesToKeep.toArray(new OfficerRole[rolesToKeep.size()])));
		
		graph.removeAllEdges(edgesToPurge);
		
		// For consistency with the map building, we should remove any CompanyOfficers that no longer
		// have any roles
		
		Set<Vertex> verticesToPurge = new HashSet<>();
		
		for(Vertex vertex : graph.vertexSet()) {
			if(vertex instanceof CompanyOfficer) {
				if(graph.edgesOf(vertex).isEmpty()) {
					verticesToPurge.add(vertex);
				}
			}
		}
		
		logger.info("Removing " + verticesToPurge.size() + " CompanyOfficers no longer connected.");
		
		graph.removeAllVertices(verticesToPurge);
	}
	
	/**
	 * Method to quantify the multiplicity and duplication of individual officers in the graph, and
	 * merge the duplicates to mitigate their effect on the analyses.
	 * 
	 * @param graph
	 * 	The {@link Graph} to examine.
	 * @param outputDir
	 * 	The directory to store outputs; details of certain fringe cases are written to files here.
	 * @return
	 * 	A {@link Map} recording details of duplicated officers and the merging that has been performed.
	 */
	private static Map<CompanyOfficer, Set<CompanyOfficer>> analyseOfficerMultiplicity(Graph<Vertex, Edge> graph, File outputDir) {

		// Extract all CompanyOfficers to a queue
		Queue<CompanyOfficer> companyOfficers = new LinkedList<>();
		for(Vertex v : graph.vertexSet()) {
			if(v instanceof CompanyOfficer) {
				companyOfficers.add((CompanyOfficer)v);
			}
		}
		
		int numOfficers = companyOfficers.size();
		
		// Data structure to store multiplicity information
		Map<CompanyOfficer, Set<CompanyOfficer>> multiplicityInfo = new HashMap<>();
		
		// Fringe case detection: store details of pairs of officers with ambiguous comparison
		List<Pair<CompanyOfficer, CompanyOfficer>> ambiguousOfficers = new LinkedList<>();
		
		// Pair-wise comparison of all officers to detect equal ones and ambiguous ones
		
		// Log helpful progress messages
		long nTotalPairwiseComparisons = ((long)numOfficers * ((long)numOfficers-1)) / 2;
		
		Counter counter = new Counter(nTotalPairwiseComparisons);
        int comparisons = 0;
        counter.start(System.nanoTime());
		
		while(!companyOfficers.isEmpty()) {
			
			// Retrieve and remove officer at head of queue
			CompanyOfficer prototype = companyOfficers.poll();
			
			// Check the remaining officers for any equal or ambiguous ones
			
			// Stores (by appointments link) all officers found to be equal to this one
			Set<CompanyOfficer> duplicates = new HashSet<>();
			
			for(CompanyOfficer officer : companyOfficers) {
				
				OFFICER_EQUALITY_TEST_OUTCOME outcome = CompaniesHouseUtil.compareOfficers(prototype, officer);
				
				comparisons++;
				
				// Log progress messages
	        	String[] message;
	        	if((message = counter.getOnePercentMessage(comparisons)) != null) {
	        		logger.info("Progress: " + message[0] + " " + message[1]);
	        	}
				
				switch(outcome) {
				case DIFFERENT:
					break;
				case SAME:
					duplicates.add(officer); break;
				case AMBIGUOUS:
					// Same name, but missing DoB info that could resolve the ambiguity.
					// We currently assume these are different officers.
					ambiguousOfficers.add(Pair.of(prototype, officer));
					break;
				}
			}
			
			if(!duplicates.isEmpty()) {
				// Record the multiplicity info
				multiplicityInfo.put(prototype, duplicates);
				// Remove the duplicates from the remaining officers to avoid double counting
				companyOfficers.removeAll(duplicates);
			}
		}
		
		int numDuplicatedRecords = 0;
		for(Set<CompanyOfficer> duplicates : multiplicityInfo.values()) {
			numDuplicatedRecords += duplicates.size() + 1;
		}
		
		// Report details of merged officers
		Map<Integer, int[]> duplications = new TreeMap<>();
		for(Set<CompanyOfficer> duplicates : multiplicityInfo.values()) {
			Integer multiplicity = new Integer(duplicates.size() + 1);
			if(!duplications.containsKey(multiplicity)) {
				duplications.put(multiplicity, new int[]{0});
			}
			duplications.get(multiplicity)[0]++;
		}
		
//		for(Entry<CompanyOfficer, Set<CompanyOfficer>> entry : multiplicityInfo.entrySet()) {
//			CompanyOfficer prototype = entry.getKey();
//			Set<CompanyOfficer> duplicates = entry.getValue();
//			System.out.println("Officer multiplicity: " + (duplicates.size() + 1));
//			System.out.println("Copies:");
//			System.out.println(prototype.name + "\t" + prototype.links.officer.appointments);
//			for(CompanyOfficer copy : duplicates) {
//				System.out.println(copy.name + "\t" + copy.links.officer.appointments);
//			}
//		}

		System.out.println("Number of officer records, including duplicates: " + numOfficers);
		System.out.println("Number of officers with duplicate records: " + multiplicityInfo.size());
		System.out.println("Multiplicity distribution:");
		for(Entry<Integer, int[]> entry : duplications.entrySet()) {
			System.out.println(entry.getKey() + "\t -> \t" + entry.getValue()[0]);
		}
		System.out.println("Number of duplicate officer records: " + numDuplicatedRecords 
				+ "\t" + String.format("(%.2f%%)", (100.0 * numDuplicatedRecords)/numOfficers));
		System.out.println("Number of ambiguous comparisons: " + ambiguousOfficers.size() 
		        + "\t" + String.format("(%.2f%%)", (100.0 * ambiguousOfficers.size())/numOfficers));

		// Now merge the duplicated officers
		
		// Fringe case detection: store details of cases where two CompanyOfficers that are to be merged
		// have the same role with the same company and appear to be the result of multiple identical filings
		List<IdenticalFiling> identicalFilings = new LinkedList<>();
		
		for(Entry<CompanyOfficer, Set<CompanyOfficer>> entry : multiplicityInfo.entrySet()) {
			
			// The prototype CompanyOfficer that will replace all the duplicates
			CompanyOfficer prototype = entry.getKey();
			
			Set<CompanyOfficer> duplicates = entry.getValue();
			
			for(CompanyOfficer duplicate : duplicates) {
				
				// Set of all companies that this duplicate officer is connected to. This is used
				// to remove all the edges associated with this duplicate officer in a manner
				// that can handle cases where there's more than one edge connecting to the same
				// company.
				Set<Company> companiesConnectedToDuplicate = new HashSet<>();
				
				// Iterate over edges connected to this duplicate officer & transfer the edge
				// to the prototype officer.
				NEXT_EDGE: for(Edge edgeToTransfer : graph.edgesOf(duplicate)) {
					
					Vertex s = graph.getEdgeSource(edgeToTransfer);
					Vertex t = graph.getEdgeTarget(edgeToTransfer);
					
					Company company = null;
					if(s instanceof CompanyOfficer) {
						company = (Company) t;
					}
					else {
						company = (Company) s;
					}
					
					companiesConnectedToDuplicate.add(company);
					
					// Check for multiple filings
					
					// Existing edges between company and prototype officer
					Set<Edge> existingEdges = graph.getAllEdges(company, prototype);
					
					if(!existingEdges.isEmpty()) {
						// The prototype officer is already connected with the company that the duplicate
						// is connected to. Check if the role is the same.
						for(Edge existingEdge : existingEdges) {
							if(existingEdge.role == edgeToTransfer.role) {
								// Found case of multiple identical filings
								identicalFilings.add(new IdenticalFiling(company, prototype, duplicate, existingEdge.role));
								// Don't need to transfer this edge
								continue NEXT_EDGE;
							}
						}
					}
					
					// Transfer the edge to the prototype officer
					graph.addEdge(company, prototype, new Edge(edgeToTransfer.role, company, prototype));
				}
				
				// Remove all edges connecting this duplicate officer with each company
				for(Company company : companiesConnectedToDuplicate) {
					graph.removeAllEdges(company, duplicate);
				}
				
			}
			
			// Remove the duplicated officers
			graph.removeAllVertices(duplicates);
		}
		
		System.out.println("Number of duplicated identical filings found: " + identicalFilings.size());
		
		// Store details of fringe cases to files
		try(BufferedWriter out = new BufferedWriter(new FileWriter(new File(outputDir, "ambiguousOfficers.txt")))) {
			for(Pair<CompanyOfficer, CompanyOfficer> pair : ambiguousOfficers) {
				CompanyOfficer officer1 = pair.getLeft();
				CompanyOfficer officer2 = pair.getRight();
				out.write(officer1.links.officer.appointments + "\t" + officer2.links.officer.appointments + "\n");
			}
		} catch (IOException e) {
			logger.severe("Exception writing ambiguousOfficer.txt file: " + e.getMessage());
			e.printStackTrace();
		}
		
		try(BufferedWriter out = new BufferedWriter(new FileWriter(new File(outputDir, "identicalFilings.txt")))) {
			for(IdenticalFiling identicalFiling : identicalFilings) {
				Company company = identicalFiling.company;
				CompanyOfficer officer1 = identicalFiling.officer1;
				CompanyOfficer officer2 = identicalFiling.officer2;
				OfficerRole role = identicalFiling.role;
				out.write(company.company_number + "\t" + officer1.links.officer.appointments + "\t"
						+ officer2.links.officer.appointments + "\t" + role + "\n");
			}
		} catch (IOException e) {
			logger.severe("Exception writing identicalFilings.txt file: " + e.getMessage());
			e.printStackTrace();
		}

		return multiplicityInfo;
	}
	
	/**
	 * Writes the graph to a suitable interchange format.
	 * 
	 * @param graph
	 * 	The {@link Graph} to write to the {@link File}.
	 * @param duplicatedOfficers
	 * 	A {@link Map} recording the details of any merger of duplicated officers that has been performed. The details
	 * of the merged officers is recorded in the stored graph.
	 * @param graphFile
	 * 	The {@link File} to write the graph to in the interchange format.
	 * @throws IOException
	 * 	If there's a problem writing the graph to the file.
	 */
	private static void writeInterchangeFormat(Graph<Vertex, Edge> graph, Map<CompanyOfficer, Set<CompanyOfficer>> duplicatedOfficers, File graphFile) throws IOException {
		
		BufferedWriter out = new BufferedWriter(new FileWriter(graphFile));
		
		// Loop over every vertex; write company numbers then officer appointments
		Set<Company> companies = new HashSet<>();
		Set<CompanyOfficer> officers = new HashSet<>();
		
		for(Vertex v : graph.vertexSet()) {
			if(v instanceof CompanyOfficer) {
				officers.add((CompanyOfficer)v);
			}
			else {
				companies.add((Company)v);
			}
		}
		
		// Write header info
		out.write("# Companies: " + companies.size() + "\n");
		out.write("# Officers: " + officers.size() + "\n");
		out.write("# Edges: " + graph.edgeSet().size() + "\n");
		
		// Write Company vertices
		for(Company company : companies) {
			out.write(company.company_number + "\n");
		}

		// Write CompanyOfficer vertices
		for(CompanyOfficer officer : officers) {
			
			// Check if this officer is the survivor of a merger
			if(duplicatedOfficers.containsKey(officer)) {
				// This officer has been merged with other(s). Write out the details
				out.write(officer.links.officer.appointments);
				for(CompanyOfficer duplicate : duplicatedOfficers.get(officer)) {
					out.write("\t" + duplicate.links.officer.appointments);
				}
				out.newLine();
			}
			else {
				// This officer is was not merged with any others
				out.write(officer.links.officer.appointments + "\n");
			}
		}
		
		// Write edges
		for(Edge edge : graph.edgeSet()) {
			Vertex s = graph.getEdgeSource(edge);
			Vertex t = graph.getEdgeTarget(edge);
			
			CompanyOfficer officer = null;
			Company company = null;
			if(s instanceof CompanyOfficer) {
				officer = (CompanyOfficer) s;
				company = (Company) t;
			}
			else {
				officer = (CompanyOfficer) t;
				company = (Company) s;
			}
			
			String companyNumber = company.company_number;
			String appointments = officer.links.officer.appointments;
			String role = edge.role.name();
			
			out.write(companyNumber + "\t" + appointments + "\t" + role + "\n");
		}
		out.close();

		// Check that the interchange format preserves the graph contents & structure
		if(!graph.equals(readInterchangeFormat(graphFile))) {
			throw new RuntimeException("Error in interchange format: serialised and deserialised pre-merger graphs not equal!");
		}
	}

	/**
	 * Reads the graph from a suitable interchange format.
	 * 
	 * @param graphFile
	 * 	The {@link File} containing the graph in the interchange format.
	 * @return
	 * 	The {@link Graph} read from the {@link File}.
	 * @throws IOException 
	 * 	If there's a problem reading the graph from the file.
	 */
	private static Graph<Vertex, Edge> readInterchangeFormat(File graphFile) throws IOException {
		
		Graph<Vertex, Edge> graph = new Multigraph<>(Edge.class);
		
		BufferedReader in = new BufferedReader(new FileReader(graphFile));
		
		String line;
		
		// Read header info
		int numCompanies = 0;
		int numOfficers = 0;
		int numEdges = 0;
		
		Scanner scan = new Scanner(in.readLine());
		// Purge '#' and 'Companies:'
		scan.next();
		scan.next();
		numCompanies = scan.nextInt();
		scan.close();
		
		scan = new Scanner(in.readLine());
		// Purge '#' and 'Officers:'
		scan.next();
		scan.next();
		numOfficers = scan.nextInt();
		scan.close();

		scan = new Scanner(in.readLine());
		// Purge '#' and 'Edges:'
		scan.next();
		scan.next();
		numEdges = scan.nextInt();
		scan.close();
		
		// Read all Company vertices & enter into graph
		for(int i=0; i<numCompanies; i++) {
			line = in.readLine();
			scan = new Scanner(line);
			String companyNumber = scan.next();
			scan.close();
			Company company = new Company(companyNumber);
			graph.addVertex(company);
		}
		
		// Read all CompanyOfficer vertices & enter into graph
		for(int i=0; i<numOfficers; i++) {
			line = in.readLine();
			scan = new Scanner(line);
			String appointments = scan.next();
			// Ignore any duplicate officers
			scan.close();
			CompanyOfficer officer = new CompanyOfficer(appointments);
			graph.addVertex(officer);
		}
		
		// Read all edges & enter into graph
		for(int i=0; i<numEdges; i++) {
			line = in.readLine();
			scan = new Scanner(line);
			String companyNumber = scan.next();
			String appointments = scan.next();
			String role = scan.next();
			scan.close();
			Company company = new Company(companyNumber);
			CompanyOfficer officer = new CompanyOfficer(appointments);
			OfficerRole officerRole = OfficerRole.valueOf(role);
			graph.addEdge(company, officer, new Edge(officerRole, company, officer));
		}
		in.close();
		
		return graph;
	}
	
	/**
	 * Represents cases of identical filings, where two separate {@link CompanyOfficers} have been determined
	 * to represent the same person, and they have the same role at the same company.
	 * @author nrowell
	 *
	 */
	private static class IdenticalFiling {
		/**
		 * The {@link Company}.
		 */
		Company company;
		/**
		 * The first {@link CompanyOfficer}.
		 */
		CompanyOfficer officer1;
		/**
		 * The second {@link CompanyOfficer}.
		 */
		CompanyOfficer officer2;
		/**
		 * The {@link OfficerRole}.
		 */
		OfficerRole role;
		
		/**
		 * Main constructor.
		 * 
		 * @param company
		 *  The {@link Company}.
		 * @param officer1
		 *  The first {@link CompanyOfficer}.
		 * @param officer2
		 *  The second {@link CompanyOfficer}.
		 * @param role
		 *  The {@link OfficerRole}.
		 */
		IdenticalFiling(Company company, CompanyOfficer officer1, CompanyOfficer officer2, OfficerRole role) {
			this.company = company;
			this.officer1 = officer1;
			this.officer2 = officer2;
			this.role = role;
		}
	}
}
