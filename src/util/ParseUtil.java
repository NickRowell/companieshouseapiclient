package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.Logger;

import dm.CompanyDbSnapshot;
import enums.snapshot.AccountType;
import enums.snapshot.CompanyStatus;
import enums.snapshot.CompanyType;

/**
 * Utilities to parse Companies House records from text.
 *
 * @author nrowell
 * @version $Id$
 */
public final class ParseUtil {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(ParseUtil.class.getName());
	
	/**
	 * Private default constructor to prevent accidental instantiation.
	 */
	private ParseUtil() {
	}
	
	/**
	 * Tokenize a string on the given token, while ignoring instances of the delimiter that are contained
	 * between double quotes.
	 * 
	 * TODO currently this method assumes every field is fully contained in quotes. Relax this.
	 * 
	 * @param txt
	 * 	The {@link String} to tokenize.
	 * @param delim
	 * 	The delimiter token.
	 * @return
	 * 	A {@link List} of {@link String}s containing each token.
	 */
	public static List<String> tokenizeWithQuotes(String txt, char delim) {
		
		char[] chars = txt.toCharArray();
		List<String> fieldsList = new LinkedList<>();
		
		// Indicates if we are currently 'inside' a field
		boolean readingField = false;
		
		// Buffer to store the characters of field currently being read
		List<Character> currentField = new LinkedList<>();
		
		for(int i=0; i<chars.length; i++) {
			
			// Special handling of double quotes
			if(chars[i] == '"') {
				if(!readingField) {
					// Start of a new field; no special checks
					readingField = true;
				}
				else {
					// Reading a field; double quote either means the end of this field, or the
					// presence of a pair of double quotes contained within the field. Need to 
					// resolve this by checking whether the next character is also a double quote.
					
					boolean endOfField = false;
					
					if(i == chars.length-1) {
						// No more characters; this is the closing double quote of the final field.
						// There's no closing comma, so terminate the field here.
						endOfField = true;
					}
					else {
						// Double quote could either close off the current field, or be inside a field.
						// Quotes inside a field always come in consecutive pairs, so check for that.
						if(chars[i+1] == '"') {
							// Pair of double quotes inside a field; this is an escaped double quote, so
							// add one double quote to the field and increment the counter to skip the next
							// double quote.
							currentField.add(chars[i]);
							i++;
						}
						else {
							// The double quote closes the current field. The contents of the field
							// will be read from the buffer when the comma is encountered.
							endOfField = true;
						}
					}
					
					if(endOfField) {
						char[] fieldChars = new char[currentField.size()];
						int c=0;
						for(Character ch : currentField) {
							fieldChars[c++] = ch;
						}
						String field = new String(fieldChars);
						fieldsList.add(field);
						currentField.clear();
						readingField = false;
					}
				}
			}
			
			// Special handling of commas
			else if(chars[i] == ',') {
				if(!readingField) {
					// Comma between fields; do nothing
				}
				else {
					// Add comma to the current field
					currentField.add(chars[i]);
				}
			}
			
			// Everything else
			else {
				if(readingField) {
					currentField.add(chars[i]);
				}
				else {
					// Character is a whitespace or something appearing between fields; just flush it
				}
			}
		}
		return fieldsList;
	}
	
	/**
	 * Tokenize a string on the given token, while ignoring instances of the delimiter that are contained
	 * between double quotes.
	 * 
	 * @param txt
	 * 	The {@link String} to tokenize.
	 * @param delim
	 * 	The delimiter token.
	 * @return
	 * 	A {@link List} of {@link String}s containing each token.
	 */
	public static List<String> tokenizeWithQuotes2(String txt, char delim) {
		
		char[] chars = txt.toCharArray();
		List<String> fieldsList = new LinkedList<>();
		
		// Indicates if we are currently 'inside' a quote
		boolean quoted = false;
		
		// Buffer to store the characters of field currently being read
		List<Character> currentField = new LinkedList<>();
		
		for(int i=0; i<chars.length; i++) {
			
			// Special handling of double quotes
			if(chars[i] == '"') {
				if(!quoted) {
					// Start of quoted section
					quoted = true;
				}
				else {
					if(i == chars.length-1 || chars[i+1] != '"') {
						// End of quoted section
						quoted = false;
					}
					else {
						// Double quote contained in a field
						currentField.add(chars[i]);
						i++;
					}
				}
			}
			
			// Special handling of commas
			else if(chars[i] == ',') {
				if(!quoted) {
					// End of current field

					char[] fieldChars = new char[currentField.size()];
					int c=0;
					for(Character ch : currentField) {
						fieldChars[c++] = ch;
					}
					String field = new String(fieldChars);
					// Trim any white space
					field = field.trim();
					fieldsList.add(field);
					currentField.clear();
				}
				else {
					// Add comma to the current field
					currentField.add(chars[i]);
				}
			}
			
			// Everything else
			else {
				currentField.add(chars[i]);
			}
		}
		return fieldsList;
	}
	
	/**
	 * This method takes a {@link String} containing words separated by whitespace and returns an identical {@link String}
	 * where the words are separated by exactly one space.
	 * 
	 * @param input
	 * 	The input {@link String}
	 * @return
	 * 	The output {@link String}
	 */
	public static String homogeniseWhiteSpace(String input) {
		
		// Trim any leading/trailing whitespace
		input = input.trim();
		
		// Split the string on any amount of white space
		String[] words = input.split("\\s+");
		
		if(words.length == 0) {
			return "";
		}
		
		// Put the words back together separated by single spaces
		StringBuilder output = new StringBuilder();
		output.append(words[0]);
		for(int i=1; i<words.length; i++) {
			String word = words[i];
			output.append(" ");
			output.append(word);
		}
			
		return output.toString();
	}
	
	/**
	 * This method takes a {@link String} and converts any variant of 'PLC' to 'PLC'.
	 * 
	 * @param input
	 * 	The input {@link String}
	 * @return
	 * 	The output {@link String}
	 */
	public static String homogenisePlc(String input) {
		
		// P.L.C. -> PLC
		input = input.replace("P.L.C.", "PLC");
		
		// P. L. C. -> PLC
		input = input.replace("P. L. C.", "PLC");
		
		// P L C -> PLC
		input = input.replace("P L C", "PLC");
		
		// PLC. -> PLC
		input = input.replace("PLC.", "PLC");
		
		// PUBLIC LIMITED COMPANY -> PLC
		input = input.replace("PUBLIC LIMITED COMPANY", "PLC");
		
		return input;
	}

	/**
	 * This method takes a {@link String} and converts any occurences of '&' to 'AND'.
	 * 
	 * @param input
	 * 	The input {@link String}
	 * @return
	 * 	The output {@link String}
	 */
	public static String homogeniseAnd(String input) {
		
		input = input.replace("&", "AND");
		
		return input;
	}
	
	/**
	 * This method takes a {@link String} and removes any occurence of '(THE)' within the
	 * string and 'THE' at the start  of the string.
	 * 
	 * @param input
	 * 	The input {@link String}
	 * @return
	 * 	The output {@link String}
	 */
	public static String homogeniseThe(String input) {
		
		input = input.replace("(THE)", "");
		// Close any excess whitespace left by removing (THE)
		input = homogeniseWhiteSpace(input);
		
		if (input.startsWith("THE ")) {
		    input = input.replaceFirst("THE ", "");
		}
		
		return input;
	}
	
	/**
	 * Looks for something contained in brackets in the string and moves it to the front.
	 * @param input
	 * @return
	 */
	public static String homogeniseParentheses(String input) {
		
		if(input.contains("(")) {
			// Identify the brackets and enclosed text
			String bracketsAndText = input.substring(input.indexOf("("),input.indexOf(")")+1);
			
			// Remove the brackets and enclosed text from the middle of the string
			input = input.replace(bracketsAndText, "");
			
			// Strip the brackets from the text
			String text = bracketsAndText.substring(1, bracketsAndText.length() - 1);
			
			// Insert the text at the start
			input = text + " " + input;
			
			input = homogeniseWhiteSpace(input);
		}
	    
		return input;
	}
	
	/**
	 * Parses a {@link CompanyDbSnapshot} from a record in the Companies House database snapshot.
	 * 
	 * @param txt
	 * 	The String to parse.
	 * @return
	 * 	A {@link CompanyDbSnapshot} parsed from the text String.
	 */
	public static CompanyDbSnapshot parseCompany(String txt) {
		
		List<String> fieldsList = tokenizeWithQuotes(txt, ',');
		
		if(fieldsList.size() != 55) {
			System.out.println("Parsed "+fieldsList.size() + " fields from " + txt);
			return null;
		}
		
		// Read the fields out to an array for ease of access
		String[] fields = new String[55];
		fieldsList.toArray(fields);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		
		// Now read out each field to the fields of the Company object
		CompanyDbSnapshot company = new CompanyDbSnapshot();
		
		// Parse CompanyName
		company.company_name = fields[0];
		// Parse CompanyNumber
		company.company_number = fields[1];
		
		company.registered_office_address = new CompanyDbSnapshot.RegisteredOfficeAddress();
		
		// Parse RegAddress.CareOf
		company.registered_office_address.care_of = fields[2];
		// Parse RegAddress.POBox
		company.registered_office_address.po_box = fields[3];
		// Parse RegAddress.AddressLine1
		company.registered_office_address.address_line_1 = fields[4];
		// Parse RegAddress.AddressLine2
		company.registered_office_address.address_line_2 = fields[5];
		// Parse RegAddress.PostTown
		company.registered_office_address.locality = fields[6];
		// Parse RegAddress.County
		company.registered_office_address.region = fields[7];
		// Parse RegAddress.Country
		company.registered_office_address.country = fields[8];
		// Parse RegAddress.PostCode
		company.registered_office_address.postal_code = fields[9];
		
		// Parse CompanyCategory, e.g. "Private Limited Company"
		company.type = CompanyType.parse(fields[10]);
		if(company.type == null) {
			System.out.println("Company "+company.company_number+": couldn't determine company type from \""+fields[10]+"\"");
		}
		
		// Parse CompanyStatus, e.g. "Active"
		company.company_status = CompanyStatus.parse(fields[11]);
		if(company.company_status == null) {
			System.out.println("Company "+company.company_number+": couldn't determine company status from \""+fields[11]+"\"");
		}
		
		// Parse CountryOfOrigin, e.g. "United Kingdom", from fields[12]
		company.country_of_origin = fields[12];
		
		// Parse DissolutionDate
		try {
			company.dissolution_date = df.parse(fields[13]);
		} catch (ParseException e) {
			company.dissolution_date = null;
			if(!fields[13].equals("")) {
				System.out.println("Couldn't parse DissolutionDate from \""+fields[13]+"\"");
			}
		}
		
		// Parse IncorporationDate
		try {
			company.incorporation_date = df.parse(fields[14]);
		} catch (ParseException e) {
			company.incorporation_date = null;
			if(!fields[14].equals("")) {
				System.out.println("Couldn't parse IncorporationDate from \""+fields[14]+"\"");
			}
		}
		
		company.accounts = new CompanyDbSnapshot.Accounts();
		
		// Parse Accounts.AccountRefDay
		if(!fields[15].isEmpty()) {
			try {
				company.accounts.account_ref_day = Integer.parseInt(fields[15]);
			}
			catch(NumberFormatException e) {
				System.out.println("Couldn't parse Accounts.AccountRefDay from \""+fields[15]+"\"");
			}
		}
		else {
			company.accounts.account_ref_day = Integer.MIN_VALUE;
		}
		
		// Parse Accounts.AccountRefMonth
		if(!fields[16].isEmpty()) {
			try {
				company.accounts.account_ref_month = Integer.parseInt(fields[16]);
			}
			catch(NumberFormatException e) {
				System.out.println("Couldn't parse Accounts.AccountRefMonth from \""+fields[16]+"\"");
			}
		}
		else {
			company.accounts.account_ref_month = Integer.MIN_VALUE;
		}
		
		// Parse Accounts.NextDueDate
		try {
			company.accounts.next_due_date = df.parse(fields[17]);
		} catch (ParseException e) {
			company.accounts.next_due_date = null;
			if(!fields[17].equals("")) {
				System.out.println("Couldn't parse Accounts.NextDueDate from \""+fields[17]+"\"");
			}
		}
		// Parse Accounts.LastMadeUpDate
		try {
			company.accounts.last_made_up_date = df.parse(fields[18]);
		} catch (ParseException e) {
			company.accounts.last_made_up_date = null;
			if(!fields[18].equals("")) {
				System.out.println("Couldn't parse Accounts.LastMadeUpDate from \""+fields[18]+"\"");
			}
		}
		
		// Parse Accounts.AccountCategory, e.g. "DORMANT", from fields[19]
		company.account_category = AccountType.parse(fields[19]);
		if(company.account_category == null) {
			System.out.println("Company "+company.company_number+": couldn't determine AccountType from \""+fields[19]+"\"");
		}
		
		company.annual_return = new CompanyDbSnapshot.Returns();
		
		// Parse Returns.NextDueDate
		try {
			company.annual_return.next_due_date = df.parse(fields[20]);
		} catch (ParseException e) {
			company.annual_return.next_due_date = null;
			if(!fields[20].equals("")) {
				System.out.println("Couldn't parse Returns.NextDueDate from "+fields[20]);
			}
		}
		
		// Parse Returns.LastMadeUpDate
		try {
			company.annual_return.last_made_up_date = df.parse(fields[21]);
		} catch (ParseException e) {
			company.annual_return.last_made_up_date = null;
			if(!fields[21].equals("")) {
				System.out.println("Couldn't parse Returns.LastMadeUpDate from "+fields[21]);
			}
		}
		
		company.mortgages = new CompanyDbSnapshot.Mortgages();
		
		// Parse Mortgages.NumMortCharges from fields[22]
		if(!fields[22].isEmpty()) {
			try {
				company.mortgages.num_mort_charges = Integer.parseInt(fields[22]);
			}
			catch(NumberFormatException e) {
				System.out.println("Couldn't parse Mortgages.NumMortCharges from \""+fields[22]+"\"");
			}
		}
		else {
			company.mortgages.num_mort_charges = Integer.MIN_VALUE;
		}
		
		// Parse Mortgages.NumMortOutstanding from fields[23]
		if(!fields[23].isEmpty()) {
			try {
				company.mortgages.num_mort_outstanding = Integer.parseInt(fields[23]);
			}
			catch(NumberFormatException e) {
				System.out.println("Couldn't parse Mortgages.NumMortOutstanding from \""+fields[23]+"\"");
			}
		}
		else {
			company.mortgages.num_mort_outstanding = Integer.MIN_VALUE;
		}
		
		// Parse Mortgages.NumMortPartSatisfied from fields[24]
		if(!fields[24].isEmpty()) {
			try {
				company.mortgages.num_mort_part_satisfied = Integer.parseInt(fields[24]);
			}
			catch(NumberFormatException e) {
				System.out.println("Couldn't parse Mortgages.NumMortPartSatisfied from \""+fields[24]+"\"");
			}
		}
		else {
			company.mortgages.num_mort_part_satisfied = Integer.MIN_VALUE;
		}
		
		// Parse Mortgages.NumMortSatisfied from fields[25]
		if(!fields[25].isEmpty()) {
			try {
				company.mortgages.num_mort_satisfied = Integer.parseInt(fields[25]);
			}
			catch(NumberFormatException e) {
				System.out.println("Couldn't parse Mortgages.NumMortSatisfied from \""+fields[25]+"\"");
			}
		}
		else {
			company.mortgages.num_mort_satisfied = Integer.MIN_VALUE;
		}
		
		company.sic_codes = new int[4];
		
		// Parse SICCode.SicText_1
		if(!fields[26].isEmpty()) {
			if(fields[26].substring(0, 4).equalsIgnoreCase("None")) {
				company.sic_codes[0] = Integer.MIN_VALUE;
			}
			else {
				company.sic_codes[0] = Integer.parseInt(fields[26].substring(0, 5).trim());
			}
		}
		else {
			company.sic_codes[0] = Integer.MIN_VALUE;
		}
		// Parse SICCode.SicText_2
		if(!fields[27].isEmpty()) {
			if(fields[27].substring(0, 4).equalsIgnoreCase("None")) {
				company.sic_codes[1] = Integer.MIN_VALUE;
			}
			else {
				company.sic_codes[1] = Integer.parseInt(fields[27].substring(0, 5).trim());
			}
		}
		else {
			company.sic_codes[1] = Integer.MIN_VALUE;
		}
		// Parse SICCode.SicText_3
		if(!fields[28].isEmpty()) {
			if(fields[28].substring(0, 4).equalsIgnoreCase("None")) {
				company.sic_codes[2] = Integer.MIN_VALUE;
			}
			else {
				company.sic_codes[2] = Integer.parseInt(fields[28].substring(0, 5).trim());
			}
		}
		else {
			company.sic_codes[2] = Integer.MIN_VALUE;
		}
		// Parse SICCode.SicText_4
		if(!fields[29].isEmpty()) {
			if(fields[29].substring(0, 4).equalsIgnoreCase("None")) {
				company.sic_codes[3] = Integer.MIN_VALUE;
			}
			else {
				company.sic_codes[3] = Integer.parseInt(fields[29].substring(0, 5).trim());
			}
		}
		else {
			company.sic_codes[3] = Integer.MIN_VALUE;
		}
		
		company.limited_partnerships = new CompanyDbSnapshot.LimitedPartnerships();
		
		// Parse LimitedPartnerships.NumGenPartners from fields[30]
		if(!fields[30].isEmpty()) {
			try {
				company.limited_partnerships.num_gen_partners = Integer.parseInt(fields[30]);
			}
			catch(NumberFormatException e) {
				System.out.println("Couldn't parse LimitedPartnerships.NumGenPartners from \""+fields[30]+"\"");
			}
		}
		else {
			company.limited_partnerships.num_gen_partners = Integer.MIN_VALUE;
		}
		
		// Parse LimitedPartnerships.NumLimPartners from fields[31]
		if(!fields[31].isEmpty()) {
			try {
				company.limited_partnerships.num_gen_partners = Integer.parseInt(fields[31]);
			}
			catch(NumberFormatException e) {
				System.out.println("Couldn't parse LimitedPartnerships.NumLimPartners from \""+fields[31]+"\"");
			}
		}
		else {
			company.limited_partnerships.num_gen_partners = Integer.MIN_VALUE;
		}
		
		// Parse URI from fields[32]
		company.links = new CompanyDbSnapshot.Links();
		company.links.self = fields[32];
		
		// Parse PreviousName_[1-10].CONDATE from fields[33, 35, ... , 51]
		// Parse PreviousName_[1-10].CompanyName from fields[34, 36, ... , 52]
		company.previous_company_names = new CompanyDbSnapshot.PreviousCompanyName[10];
		
		for(int i=0; i<10; i++) {
			company.previous_company_names[i] = new CompanyDbSnapshot.PreviousCompanyName();
			try {
				company.previous_company_names[i].ceased_on = df.parse(fields[33 + i*2 + 0]);
			}
			catch(ParseException e) {
				company.previous_company_names[i].ceased_on = null;
				if(!fields[33 + i*2 + 0].equals("")) {
					System.out.println("Couldn't parse PreviousName_"+(i+1)+".CONDATE from \""+fields[33 + i*2 + 0]+"\"");
				}
			}
			company.previous_company_names[i].name = fields[33 + i*2 + 1];
		}
		
		company.confirmation_statement = new CompanyDbSnapshot.ConfirmationStatement();
		
		// Parse ConfStmtNextDueDate from fields[53]
		try {
			company.confirmation_statement.next_due = df.parse(fields[53]);
		}
		catch(ParseException e) {
			company.confirmation_statement.next_due = null;
			if(!fields[53].equals("")) {
				System.out.println("Couldn't parse ConfStmtNextDueDate from "+fields[53]);
			}
		}
		
		// Parse ConfStmtLastMadeUpDate from fields[54]
		try {
			company.confirmation_statement.last_made_up_to = df.parse(fields[54]);
		}
		catch(ParseException e) {
			company.confirmation_statement.last_made_up_to = null;
			if(!fields[54].equals("")) {
				System.out.println("Couldn't parse ConfStmtLastMadeUpDate from "+fields[54]);
			}
		}
		
		return company;
	}
	
	/**
	 * Reads the given file and parses company numbers from the first column in each line. Logs warnings
	 * if any company number appears more than once.
	 * 
	 * @param input
	 * 	The {@link File} to load.
	 * @return
	 * 	A {@link List} of {@link String}s containing company numbers loaded from the first
	 * column of each line in the input file.
	 */
	public static List<String> parseCompanyNumbers(File input) {
		
		List<String> companyNumbers = new LinkedList<>();
		
		try(BufferedReader in = new BufferedReader(new FileReader(input))) {
			String line;
			while((line = in.readLine()) != null) {
				// Company number is in the first token
				Scanner scan = new Scanner(line);
				companyNumbers.add(scan.next());
				scan.close();
			}
		}
		catch(IOException e) {
			logger.severe("Exception while reading the file " + input.getName() + ": " + e.getLocalizedMessage());
		}
		
		// Check for duplicate company numbers
		Map<String, int[]> map = new HashMap<>();
		for(String companyNumber : companyNumbers) {
			if(!map.containsKey(companyNumber)) {
				map.put(companyNumber, new int[] {0});
			}
			map.get(companyNumber)[0]++;
		}
		
		for(Entry<String, int[]> entry : map.entrySet()) {
			if(entry.getValue()[0] > 1) {
				logger.warning("Company number " + entry.getKey() + " appears " + entry.getValue()[0] + " times in file " + input.getName());
			}
		}
		
		return companyNumbers;
	}
}
