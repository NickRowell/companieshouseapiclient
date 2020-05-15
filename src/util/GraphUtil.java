package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.Graph;
import org.jgrapht.graph.Multigraph;

import dm.Company;
import dm.CompanyLite;
import dm.CompanyOfficerLite;
import dm.CompanyOfficers;
import dm.Edge;
import dm.Vertex;
import enums.api.OfficerRole;
import util.CompaniesHouseUtil.OFFICER_EQUALITY_TEST_OUTCOME;

/**
 * Utilities for processing and handling graph data structures.
 * 
 * @author nrowell
 */
public class GraphUtil {

	/**
	 * The {@link Logger}.
	 */
	private static final Logger logger = Logger.getLogger(GraphUtil.class.getName());
	
	/**
	 * Performs some sanity checks to ensure integrity of graph, and reports some basic statistics.
	 * 
	 * @param graph
	 * 	The {@link Graph} to analyse.
	 */
	public static void sanityChecks(Graph<Vertex, Edge> graph) {
		
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
			if(v instanceof CompanyOfficerLite) {
				nOfficers++;
				CompanyOfficerLite officer = (CompanyOfficerLite)v;
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
			if(v instanceof CompanyOfficerLite) {
				return;
			}
			
			CompanyLite company = (CompanyLite)v;
			
			// Get all edges associated with this company
			Set<Edge> edges = graph.edgesOf(v);
			
			// Count the number of connections to each officer
			Map<CompanyOfficerLite, List<OfficerRole>> rolesPerOfficer = new HashMap<>();
			
			for(Edge edge : edges) {
				Vertex s = graph.getEdgeSource(edge);
				Vertex t = graph.getEdgeTarget(edge);
				
				CompanyOfficerLite officer = null;
				if(s instanceof CompanyOfficerLite) {
					officer = (CompanyOfficerLite) s;
				}
				else {
					officer = (CompanyOfficerLite) t;
				}
				
				if(!rolesPerOfficer.containsKey(officer)) {
					rolesPerOfficer.put(officer, new LinkedList<>());
				}
				rolesPerOfficer.get(officer).add(edge.role);
			}
			
			for(Entry<CompanyOfficerLite, List<OfficerRole>> entry : rolesPerOfficer.entrySet()) {
				CompanyOfficerLite officer = entry.getKey();
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
	 * Method to purge from the {@link Graph} any {@link Edge}s that are not of the desired type(s),
	 * 
	 * @param graph
	 * 	The {@link Graph} to be purged.
	 * @param rolesToKeep
	 * 	A {@link List} of {@link OfficerRole}s enumerating the roles to be kept, i.e. all {@link Edge}s
	 * corresponding to {@link OfficerRole}s NOT in this list will be purged.
	 */
	public static void purgeRoles(Graph<Vertex, Edge> graph, Set<OfficerRole> rolesToKeep) {
		
		Set<Edge> edgesToPurge = new HashSet<>();
		
		for(Edge edge : graph.edgeSet()) {
			if(!rolesToKeep.contains(edge.role)) {
				edgesToPurge.add(edge);
			}
		}
		
		logger.info("Removing " + edgesToPurge.size() + " Edges that don't correspond to: " + 
				Arrays.toString(rolesToKeep.toArray(new OfficerRole[rolesToKeep.size()])));
		
		graph.removeAllEdges(edgesToPurge);
		
		// For consistency with the map building, we should remove any CompanyOfficerLites that no longer
		// have any roles
		
		Set<Vertex> verticesToPurge = new HashSet<>();
		
		for(Vertex vertex : graph.vertexSet()) {
			if(vertex instanceof CompanyOfficerLite) {
				if(graph.edgesOf(vertex).isEmpty()) {
					verticesToPurge.add(vertex);
				}
			}
		}
		
		logger.info("Removing " + verticesToPurge.size() + " CompanyOfficerLites no longer connected.");
		
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
	public static Map<CompanyOfficerLite, Set<CompanyOfficerLite>> analyseOfficerMultiplicity(Graph<Vertex, Edge> graph, File outputDir) {

		// Extract all CompanyOfficerLites to a queue
		Queue<CompanyOfficerLite> companyOfficers = new LinkedList<>();
		for(Vertex v : graph.vertexSet()) {
			if(v instanceof CompanyOfficerLite) {
				companyOfficers.add((CompanyOfficerLite)v);
			}
		}
		
		int numOfficers = companyOfficers.size();
		
		// Data structure to store multiplicity information
		Map<CompanyOfficerLite, Set<CompanyOfficerLite>> multiplicityInfo = new HashMap<>();
		
		// Fringe case detection: store details of pairs of officers with ambiguous comparison
		List<Pair<CompanyOfficerLite, CompanyOfficerLite>> ambiguousOfficers = new LinkedList<>();
		
		// Pair-wise comparison of all officers to detect equal ones and ambiguous ones
		
		// Log helpful progress messages
		long nTotalPairwiseComparisons = ((long)numOfficers * ((long)numOfficers-1)) / 2;
		
		Counter counter = new Counter(nTotalPairwiseComparisons);
        int comparisons = 0;
        counter.start(System.nanoTime());
		
		while(!companyOfficers.isEmpty()) {
			
			// Retrieve and remove officer at head of queue
			CompanyOfficerLite prototype = companyOfficers.poll();
			
			// Check the remaining officers for any equal or ambiguous ones
			
			// Stores (by appointments link) all officers found to be equal to this one
			Set<CompanyOfficerLite> duplicates = new HashSet<>();
			
			for(CompanyOfficerLite officer : companyOfficers) {
				
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
		for(Set<CompanyOfficerLite> duplicates : multiplicityInfo.values()) {
			numDuplicatedRecords += duplicates.size() + 1;
		}
		
		// Report details of merged officers
		Map<Integer, int[]> duplications = new TreeMap<>();
		for(Set<CompanyOfficerLite> duplicates : multiplicityInfo.values()) {
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
		
		for(Entry<CompanyOfficerLite, Set<CompanyOfficerLite>> entry : multiplicityInfo.entrySet()) {
			
			// The prototype CompanyOfficer that will replace all the duplicates
			CompanyOfficerLite prototype = entry.getKey();
			
			Set<CompanyOfficerLite> duplicates = entry.getValue();
			
			for(CompanyOfficerLite duplicate : duplicates) {
				
				// Set of all companies that this duplicate officer is connected to. This is used
				// to remove all the edges associated with this duplicate officer in a manner
				// that can handle cases where there's more than one edge connecting to the same
				// company.
				Set<CompanyLite> companiesConnectedToDuplicate = new HashSet<>();
				
				// Iterate over edges connected to this duplicate officer & transfer the edge
				// to the prototype officer.
				NEXT_EDGE: for(Edge edgeToTransfer : graph.edgesOf(duplicate)) {
					
					Vertex s = graph.getEdgeSource(edgeToTransfer);
					Vertex t = graph.getEdgeTarget(edgeToTransfer);
					
					CompanyLite company = null;
					if(s instanceof CompanyOfficerLite) {
						company = (CompanyLite) t;
					}
					else {
						company = (CompanyLite) s;
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
				for(CompanyLite company : companiesConnectedToDuplicate) {
					graph.removeAllEdges(company, duplicate);
				}
				
			}
			
			// Remove the duplicated officers
			graph.removeAllVertices(duplicates);
		}
		
		System.out.println("Number of duplicated identical filings found: " + identicalFilings.size());
		
		// Store details of fringe cases to files
		try(BufferedWriter out = new BufferedWriter(new FileWriter(new File(outputDir, "ambiguousOfficers.txt")))) {
			for(Pair<CompanyOfficerLite, CompanyOfficerLite> pair : ambiguousOfficers) {
				CompanyOfficerLite officer1 = pair.getLeft();
				CompanyOfficerLite officer2 = pair.getRight();
				out.write(officer1.links.officer.appointments + "\t" + officer2.links.officer.appointments + "\n");
			}
		} catch (IOException e) {
			logger.severe("Exception writing ambiguousOfficer.txt file: " + e.getMessage());
			e.printStackTrace();
		}
		
		try(BufferedWriter out = new BufferedWriter(new FileWriter(new File(outputDir, "identicalFilings.txt")))) {
			for(IdenticalFiling identicalFiling : identicalFilings) {
				CompanyLite company = identicalFiling.company;
				CompanyOfficerLite officer1 = identicalFiling.officer1;
				CompanyOfficerLite officer2 = identicalFiling.officer2;
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
	public static void writeInterchangeFormat(Graph<Vertex, Edge> graph, Map<CompanyOfficerLite, Set<CompanyOfficerLite>> duplicatedOfficers, File graphFile) throws IOException {
		
		BufferedWriter out = new BufferedWriter(new FileWriter(graphFile));
		
		// Loop over every vertex; write company numbers then officer appointments
		Set<CompanyLite> companies = new HashSet<>();
		Set<CompanyOfficerLite> officers = new HashSet<>();
		
		for(Vertex v : graph.vertexSet()) {
			if(v instanceof CompanyOfficerLite) {
				officers.add((CompanyOfficerLite)v);
			}
			else {
				companies.add((CompanyLite)v);
			}
		}
		
		// Write header info
		out.write("# Companies: " + companies.size() + "\n");
		out.write("# Officers: " + officers.size() + "\n");
		out.write("# Edges: " + graph.edgeSet().size() + "\n");
		
		// Write Company vertices
		for(CompanyLite company : companies) {
			out.write(company.company_number + "\n");
		}

		// Write CompanyOfficer vertices
		for(CompanyOfficerLite officer : officers) {
			
			// Check if this officer is the survivor of a merger
			if(duplicatedOfficers.containsKey(officer)) {
				// This officer has been merged with other(s). Write out the details
				out.write(officer.links.officer.appointments);
				for(CompanyOfficerLite duplicate : duplicatedOfficers.get(officer)) {
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
			
			CompanyOfficerLite officer = null;
			CompanyLite company = null;
			if(s instanceof CompanyOfficerLite) {
				officer = (CompanyOfficerLite) s;
				company = (CompanyLite) t;
			}
			else {
				officer = (CompanyOfficerLite) t;
				company = (CompanyLite) s;
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
	public static Graph<Vertex, Edge> readInterchangeFormat(File graphFile) throws IOException {
		
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
			CompanyLite company = new CompanyLite(companyNumber);
			graph.addVertex(company);
		}
		
		// Read all CompanyOfficer vertices & enter into graph
		for(int i=0; i<numOfficers; i++) {
			line = in.readLine();
			scan = new Scanner(line);
			String appointments = scan.next();
			// Ignore any duplicate officers
			scan.close();
			CompanyOfficerLite officer = new CompanyOfficerLite(appointments);
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
			CompanyLite company = new CompanyLite(companyNumber);
			CompanyOfficerLite officer = new CompanyOfficerLite(appointments);
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
		CompanyLite company;
		/**
		 * The first {@link CompanyOfficerLite}.
		 */
		CompanyOfficerLite officer1;
		/**
		 * The second {@link CompanyOfficerLite}.
		 */
		CompanyOfficerLite officer2;
		/**
		 * The {@link OfficerRole}.
		 */
		OfficerRole role;
		
		/**
		 * Main constructor.
		 * 
		 * @param company
		 *  The {@link CompanyLite}.
		 * @param officer1
		 *  The first {@link CompanyOfficerLite}.
		 * @param officer2
		 *  The second {@link CompanyOfficerLite}.
		 * @param role
		 *  The {@link OfficerRole}.
		 */
		IdenticalFiling(CompanyLite company, CompanyOfficerLite officer1, CompanyOfficerLite officer2, OfficerRole role) {
			this.company = company;
			this.officer1 = officer1;
			this.officer2 = officer2;
			this.role = role;
		}
	}
}
