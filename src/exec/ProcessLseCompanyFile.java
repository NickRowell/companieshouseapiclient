package exec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dm.CompanySearch.CompanySearchCompany;
import util.ParseUtil;
import util.QueryUtil;

/**
 * Application to process the 'Company List' file from:
 * https://www.londonstockexchange.com/statistics/companies-and-issuers/companies-and-issuers.htm
 *
 *
 * @author nrowell
 * @version $Id$
 */
public class ProcessLseCompanyFile {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(ProcessLseCompanyFile.class.getName());
	
	/**
	 * Main application entry point.
	 * 
	 * @param args
	 * 	The command line args (ignored).
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// Path to file containing the company names
		File input = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/LondonStockExchange/2020.05.04/companies-defined-by-mifir-identifiers-list-on-lse.csv");
		
		// Number of header and tail lines in input file
		int nHead = 6;
		int nTail = 2;
		
		// Path to output file containing company numbers for unambiguous matches in the API
		File outputUnamb = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/LondonStockExchange/ukMainMarketCompanyNumbers.txt");
		
		// Path to output file containing ambiguous companies that couldn't be automatically matched.
		// These need to be manually examined and entered into the main file.
		File outputAmb = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/LondonStockExchange/ukMainMarketCompanyNumbersAmbiguous.txt");
		
		// Step 1) Parse the input file, identify UK Main Market companies and store their names
		
		BufferedReader in = new BufferedReader(new FileReader(input));
		
		// Parse entire file into a list of string
		List<String> lines = new ArrayList<>();
		String line;
		while((line = in.readLine()) != null) {
			lines.add(line);
		}
		in.close();
		
		List<String> lseCompanyNames = new ArrayList<>();
		
		// Read contents
		for(int i=nHead; i<lines.size() - nTail; i++) {
			
			List<String> columns = ParseUtil.tokenizeWithQuotes2(lines.get(i), ',');
			
			String countryOfIncorporation = columns.get(5).trim();
			String market = columns.get(7).trim();
			
			if(countryOfIncorporation.equalsIgnoreCase("United Kingdom") && market.equalsIgnoreCase("MAIN MARKET")) {
				lseCompanyNames.add(columns.get(2));
			}
		}
		
		logger.info("Loaded " + lseCompanyNames.size() + " company names to search.");
		
		// Step 2) Use the Companies House API to search for company numbers; write these to file
		
		BufferedWriter outUnamb = new BufferedWriter(new FileWriter(outputUnamb));
		BufferedWriter outAmb = new BufferedWriter(new FileWriter(outputAmb));
		
		int unambiguous = 0;
		int ambiguous = 0;
		int missing = 0;
		int searched = 0;
		
		// Examination of some problematic cases:
			
		// BROWN (N) GROUP PLC
//		for(int i=136; i<137; i++) {
			
		// TRAINLINE PLC
//		for(int i=724; i<725; i++) {	
			
		// WORLDLINK GROUP PLC
//		for(int i=779; i<780; i++) {	
		
		// XPLORER PLC
//		for(int i=785; i<786; i++) {	

		// PHOTO-ME INTERNATIONAL PLC
//		for(int i=563; i<564; i++) {
			
		// BIOTECH GROWTH TRUST (THE) PLC
//		for(int i=102; i<103; i++) {
		
		// Process all the companies
		for(int i=0; i<lseCompanyNames.size(); i++) {
		
			String lseCompanyName = lseCompanyNames.get(i);
			
			logger.info("Searched " + (++searched));

			int[] httpUrlResponseCode = new int[1];
			List<CompanySearchCompany> matches = QueryUtil.getActiveCompanyByName(lseCompanyName, httpUrlResponseCode);
			
			if(matches.size() == 1) {
				// Unambiguous match
				CompanySearchCompany company = matches.get(0);
				String apiCompanyName = company.title;
				outUnamb.write(company.company_number + "\t\"" + apiCompanyName + "\"\t-->\t\"" + lseCompanyName + "\"\t" + company.company_status + "\n");
				outUnamb.flush();
				unambiguous++;
			}
			else if (matches.size() > 1) {
				
				System.out.println(i+ " - Ambiguous: " + lseCompanyName);
				
				// Ambiguous matches - manual search
				outAmb.write("\""+lseCompanyName+"\"\nPossible matches: "+matches.size()+"\n");
				for(CompanySearchCompany company : matches) {
					String apiCompanyName = company.title;
					outAmb.write(company.company_number + "\t\"" + apiCompanyName + "\"\t-->\t\"" + lseCompanyName + "\"\t" + company.company_status + "\n");
				}
				outAmb.write("\n\n");
				outAmb.flush();
				ambiguous++;
			}
			else {
				// No matches - manual search
				outAmb.write("\""+lseCompanyName+"\"\nNo matches\n\n\n");
				outAmb.flush();
				missing++;
			}
		}
		
		outUnamb.close();
		outAmb.close();
		
		logger.info("Unambiguous matches: " + unambiguous + " / " + lseCompanyNames.size());
		logger.info("Ambiguous matches:   " + ambiguous + " / " + lseCompanyNames.size());
		logger.info("No matches:          " + missing + " / " + lseCompanyNames.size());
	}
}