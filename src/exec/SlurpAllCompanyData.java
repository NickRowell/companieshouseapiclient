package exec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import dm.Company;
import util.QueryUtil;

public class SlurpAllCompanyData {
	
	/**
	 * Text file containing the number of the last company slurped from the database.
	 */
	public static final File companyNumberRecord = new File("misc/companyRecord.txt");
	
	/**
	 * File in which to save downloaded {@link Company} records.
	 */
	public static final File localCopyFile = new File("data/Company.ser");
	
	/**
	 * Main application entry point.
	 * @param args
	 * 	The command line args (ignored).
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// Get the company number to start at by reading the file
		BufferedReader in = new BufferedReader(new FileReader(companyNumberRecord));
		int startingCompanyNumber = 0;
		String line;
		while((line=in.readLine()) != null) {
			startingCompanyNumber = Integer.parseInt(line);
		}
		in.close();
		// XXX
		startingCompanyNumber = 1000000;
		
		
		// Open writers on the output file and logfile, appending data
		ObjectOutputStream data = new ObjectOutputStream(new FileOutputStream(localCopyFile, true));
		
		// Counter for number of companies read
		int counter = 0;
		
		for(int c=startingCompanyNumber; c < 10000000; ) {

			int[] httpUrlResponseCode = new int[1];
			Company company = QueryUtil.getCompany(String.format("%08d", c), httpUrlResponseCode);
			
			if(company != null) {
			
				// Got a company; save it to file
				System.out.println(String.format("%d\t%s", c, company.company_name));
				data.writeObject(company);
				data.flush();
				c++;
				counter++;
				// XXX: Write only one object to file
				if(counter >= 100) {
					break;
				}
				BufferedWriter count = new BufferedWriter(new FileWriter(companyNumberRecord));
				count.write(String.format("%d", c));
				count.close();
			}
		}
		data.close();
	}

	/**
	 * TODO: use a Shutdown Hook to shutdown the application gracefully and ensure files are not
	 * left in invalid state.
	 *
	 *
	 * @author nrowell
	 * @version $Id$
	 */
	class ShutdownHook extends Thread {
	    public void run() {
	        System.out.println("Shutting down");
	    }
	}
	
}