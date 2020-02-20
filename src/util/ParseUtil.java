package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
public class ParseUtil {
	
	/**
	 * Parses a {@link CompanyDbSnapshot} from a record in the Companies House database snapshot.
	 * 
	 * @param txt
	 * 	The String to parse.
	 * @return
	 * 	A {@link CompanyDbSnapshot} parsed from the text String.
	 */
	public static CompanyDbSnapshot parseCompany(String txt) {
		
		/**
		 * Comma-separated fields from one example record:
		 * 
		 * "!BIG IMPACT GRAPHICS LIMITED"
		 * "07382019"
		 * ""
		 * ""
		 * "335 ROSDEN HOUSE"
		 * "372 OLD STREET"
		 * "LONDON"
		 * ""
		 * ""
		 * "EC1V 9AV"
		 * "Private Limited Company"
		 * "Active"
		 * "United Kingdom"
		 * ""
		 * "21/09/2010"
		 * "30"
		 * "9"
		 * "30/06/2017"
		 * "30/09/2015"
		 * "DORMANT"
		 * "19/10/2016"
		 * "21/09/2015"
		 * "0"
		 * "0"
		 * "0"
		 * "0"
		 * "59112 - Video production activities"
		 * "59113 - Television programme production activities"
		 * "74100 - specialised design activities"
		 * "74202 - Other specialist photography"
		 * "0"
		 * "0"
		 * "http://business.data.gov.uk/id/company/07382019"
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * ""
		 * "05/10/2018"
		 * "21/09/2016"
		 */
		
		// Cannot just tokenize the string on the commas because sometimes there are commas contained within the
		// fields. Need to tokenise only on commas that are not contained within fields. Also, sometimes
		// there are pairs of double quotes within double quotes: we need to track the level of quoting in order to figure
		// out when we're not inside a quote.
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
	
}
