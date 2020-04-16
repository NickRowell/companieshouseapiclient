package exec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

import dm.CompanyDbSnapshot;
import enums.snapshot.CompanyStatus;
import enums.snapshot.CompanyType;
import util.ParseUtil;

/**
 * This class provides an application that reads the Companies House Database Snapshot, identifies suitable
 * companies according to certain predicates, and writes the details of the selected companies to file for
 * follow up analysis.
 * 
 * TODO: include/exclude Scottish registered companies? Any others? NI prefix?
 * 
 * @author nrowell
 * @version $Id$
 */
public class ProcessCompaniesHouseDbSnapshot {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(ProcessCompaniesHouseDbSnapshot.class.getName());
	
	/**
	 * Path to the file containing the database snapshot.
	 */
	private static final File dbSnaphotPath = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/FreeDataProduct/BasicCompanyDataAsOneFile-2020-03-01.csv");
	
	/**
	 * Path to the directory to store the outputs.
	 */
	private static final File outputDir = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/FreeDataProduct");
	
	/**
	 * Predicate used to identify suitable public companies.
	 */
	static CompanyPredicate pubPred = new CompanyPredicate(
			Arrays.asList(CompanyType.plc), 
			Arrays.asList(CompanyStatus.active));

	/**
	 * Predicate used to identify suitable private companies.
	 */
	static CompanyPredicate privPred = new CompanyPredicate(
			Arrays.asList(CompanyType.ltd), 
			Arrays.asList(CompanyStatus.active));
	
	/**
	 * Main application entry point.
	 * 
	 * @param args
	 * 	The command line arguments (ignored).
	 * @throws IOException
	 * 	If there's an error reading or writing the data.
	 */
	public static void main(String[] args) throws IOException {
		
		// Open writers for the output files
		BufferedWriter outPub = new BufferedWriter(new FileWriter(new File(outputDir, "pubCompanies.txt")));
		BufferedWriter outPriv = new BufferedWriter(new FileWriter(new File(outputDir, "privCompanies.txt")));
		
		// Count numbers of companies
		int nPub = 0;
		int nPriv = 0;
		
		// Accounting of different company types
		Map<CompanyType, int[]> counts = new EnumMap<>(CompanyType.class);
		
		BufferedReader in = new BufferedReader(new FileReader(dbSnaphotPath));
		
		// Flush the header line
		in.readLine();
		
		String record;
		while((record = in.readLine()) != null) {
			
			// Parse the Company
			CompanyDbSnapshot company = ParseUtil.parseCompany(record);
			
			// Identify public companies
			if(pubPred.test(company)) {
				outPub.write(String.format("%s\n", company.company_number));
				outPub.flush();
				nPub++;
			}
			
			// Identify private companies
			if(privPred.test(company)) {
				outPriv.write(String.format("%s\n", company.company_number));
				outPriv.flush();
				nPriv++;
			}
			
			// Accounting of all active companies of different types
			if(company.company_status == CompanyStatus.active) {
				if(!counts.containsKey(company.type)) {
					counts.put(company.type, new int[] {0});
				}
				counts.get(company.type)[0]++;
			}
		}
		in.close();
		outPub.close();
		outPriv.close();
		
		logger.info("Selected public companies: " + nPub);
		logger.info("Selected private companies: " + nPriv);
		
		int[] sum = {0};
		counts.forEach((k, v) -> sum[0] += v[0]);
		logger.info("Total active companies: " + sum[0]);
		
		logger.info("Accounting of different company types:");
		counts.forEach((k, v) -> logger.info(String.format("%s:\t%d", k, v[0])));
	}
	
	/**
	 * The {@link CompanyPredicate} is used to identify {@link CompanyDbSnapshot} records that
	 * satisfy various selection criteria.
	 */
	static class CompanyPredicate implements Predicate<CompanyDbSnapshot> {
		
		/**
		 * A {@link List} of {@link CompanyType} that contains the accepted company types.
		 */
		List<CompanyType> acceptedCompanyTypes;
		
		/**
		 * A {@link List} of {@link CompanyStatus} that contains the accepted company statuses.
		 */
		List<CompanyStatus> acceptedCompanyStatuses;
		
		/**
		 * Main constructor.
		 * 
		 * @param acceptedCompanyTypes
		 * 	A {@link List} of {@link CompanyType} that contains the accepted company types.
		 * @param acceptedCompanyStatuses
		 * 	A {@link List} of {@link CompanyStatus} that contains the accepted company statuses.
		 */
		public CompanyPredicate(List<CompanyType> acceptedCompanyTypes, List<CompanyStatus> acceptedCompanyStatuses) {
			this.acceptedCompanyTypes = new LinkedList<>(acceptedCompanyTypes);
			this.acceptedCompanyStatuses = new LinkedList<>(acceptedCompanyStatuses);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean test(CompanyDbSnapshot company) {
			
			// Check company type
			if(!acceptedCompanyTypes.contains(company.type)) {
				return false;
			}

			// Check company status
			if(!acceptedCompanyStatuses.contains(company.company_status)) {
				return false;
			}
			
			// Accepted company
			return true;
		}
	}
}
