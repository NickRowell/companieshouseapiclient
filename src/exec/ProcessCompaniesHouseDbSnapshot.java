package exec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import dm.CompanyDbSnapshot;
import util.ParseUtil;

/**
 * This class provides an application that reads the Companies House Database Snapshot,
 * parses the records to {@link CompanyDbSnapshot} data types and processes the results to measure
 * various statistical quantities.
 *
 * @author nrowell
 * @version $Id$
 */
public class ProcessCompaniesHouseDbSnapshot {
	
	/**
	 * Path to the file containing the database snapshot.
	 */
	private static final File dbSnaphotPath = new File("/home/nrowell/Projects/CompaniesHouseStatsAnalysis/data/BasicCompanyDataAsOneFile-2017-06-01.csv");
	
	public static void main(String[] args) throws IOException {
		
		List<CompanyDbSnapshot> companies = new LinkedList<>();
		
		BufferedReader in = new BufferedReader(new FileReader(dbSnaphotPath));
		
		BufferedWriter out = new BufferedWriter(new FileWriter(new File("/home/nrowell/Temp/companies.txt")));
		Calendar cal = Calendar.getInstance();
		DateFormat f = new SimpleDateFormat("dd MMM yyyy");
		
		// Flush the header line
		in.readLine();
		
		String record;
		while((record = in.readLine()) != null) {
			
			// Parse the Company
			CompanyDbSnapshot company = ParseUtil.parseCompany(record);
//			companies.add(company);
			
			if(company.incorporation_date == null) {
				continue;
			}
			
			cal.setTime(company.incorporation_date);
			
			// Check the incorporation date
			if(cal.get(Calendar.MONTH) == Calendar.OCTOBER && cal.get(Calendar.YEAR) == 2009) {
				out.write(company.company_number + "\t" + f.format(company.incorporation_date) + "\t" + company.company_name + "\n");
				out.flush();
			}
		}
		in.close();
		out.close();
		
		System.out.println("Parsed "+companies.size() + " Company records");
	}
}
