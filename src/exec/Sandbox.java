package exec;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;

import dm.Company;
import util.QueryUtil;

/**
 * Application name	companies_house_sandbox
 * Application type	API key
 * API key	unzngwpKvT8FOSSJJkyuNP7SsJo6v8f4w4FciVy2
 * Restricted IPs	
 * 195.194.121.66
 * JavaScript domains	None. JavaScript access disabled.
 * Registered on	2017-08-29T17:41:40
 *
 * TODO: build GUI application that queries a given company number and uses reflection to display all the available fields
 * TODO: load serialized companys from file
 * TODO: make Company and all inner classes serializable; download a bunch of Company data and save to local file; start building a snapshot of the database.
 * TODO: figure out how to query all the companies, i.e. get the highest company number at the present time
 * TODO: enumerate the values of the JSON reponse code.
 *
 * Questions to resolve:
 *  - How many companies are there?
 *  - How long would it take to slurp all the data?
 *  - How much disk space would that take up?
 *  - Can I use the database snapshot instead? How up to date is it? Does it contain all the same information?
 *  - What additional information is available through the API?
 *  - What do the company numbers with no company mean? Are they companies that closed down or something?
 *  - What kind of analysis is worthwhile?
 *
 * @author nrowell
 * @version $Id$
 */
public class Sandbox {
	
	private static int[] existingCompanies = new int[]{6, 86, 118, 121, 133, 140, 182};
	
	public static void main(String[] args) throws IOException {
		
		// Parse the serialized companies data
		File input = new File("data/Company.ser");
		
		List<Company> companies = new LinkedList<>();
		
		try (FileInputStream fileIn = new FileInputStream(input); ObjectInputStream in = new ObjectInputStream(fileIn);) {
			int count = 0;
			while(true) {
				Company company = (Company)in.readObject();
				companies.add(company);
				System.out.println("Count  = " + (++count));
			}
		} catch(EOFException e) {
			System.out.println("Reached end of file; got "+companies.size()+" Companies");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}
