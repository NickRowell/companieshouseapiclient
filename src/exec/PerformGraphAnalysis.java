package exec;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;

import dm.Company;
import dm.CompanyOfficer;
import dm.CompanyOfficerLite;
import dm.Edge;
import dm.Vertex;
import enums.api.OfficerRole;
import exec.MapCompanyOfficers.Scenario;
import util.CompaniesHouseUtil;
import util.GnuplotUtil;
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
		GraphUtil.sanityChecks(graph);
		
		// Analyses to perform:
		
		// 0) Plot the distribution of different officer roles
		// TODO: add a percentage threshold below which any role type is added to an 'other' category
		plotOfficerRoleHistogram(graph, new File(outputDir, "officerRoles.png"));
		
		// Now purge the roles we're not interested in
		GraphUtil.purgeRoles(graph, new HashSet<OfficerRole> (Arrays.asList(OfficerRole.director)));
		
		// Write graph interchange format pre-merger
		GraphUtil.writeInterchangeFormat(graph, new HashMap<>(), new File(outputDir, scenario + "_PRE_MERGER.txt"));
		
		// 1) Analysis of officer multiplicity & purging of duplicated officers. Important to do
		//    this before any statistical analysis that is affected by officer multiplicity.
		Map<CompanyOfficerLite, Set<CompanyOfficerLite>> duplicatedOfficers = GraphUtil.analyseOfficerMultiplicity(graph, outputDir);
		
		// Write graph interchange format post-merger
		GraphUtil.writeInterchangeFormat(graph, duplicatedOfficers, new File(outputDir, scenario + "_POST_MERGER.txt"));
		
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

}
