package exec;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

import dm.Appointment;
import dm.Appointments;
import dm.Company;
import dm.CompanyOfficer;
import dm.CompanyOfficers;
import util.OfficerMap;
import util.QueryUtil;

/**
 * This application uses the Companies House API to map the network of companies and officers.
 *
 * TODO: make sure we're handling multiplicity of officers correctly
 * TODO: verify we're not covering the same ground twice
 * TODO: incorporate visualisation using some graph theory library
 * TODO: implement recursive automated mapping of company/officer network
 * TODO: add fields of the CompanyOfficer etc that are missing
 * TODO: update enum types
 * TODO: move some subclasses out to dm package and reuse
 *
 * @author nrowell
 * @version $Id$
 */
public class MapCompanyOfficers {
	
	/**
	 * Main application entry point.
	 * 
	 * @param args
	 * 	The command line args (ignored).
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// Starting point (company number)
		String startingCompanyNumber = "11432768"; // Oakwood Bespoke
//		String startingCompanyNumber = "00233462"; // John Lewis
		
		// Set of all companies examined
		Set<Company> companiesExamined = new HashSet<>();
		
		// Special data structure used to store unique officers already examined and monitor the multiplicity of records
		// that correspond to the same person.
		OfficerMap companyOfficersExamined = new OfficerMap();
		
		// Queue of all officers waiting to be examined
		Queue<CompanyOfficer> officersToExamine = new LinkedBlockingQueue<>();
		
		// Get the officers of the first company to initialise the search
		Company startingCompany = QueryUtil.getCompany(startingCompanyNumber);
		companiesExamined.add(startingCompany);
		officersToExamine.addAll(getOfficers(startingCompany));

		// Now start the search
		while(!officersToExamine.isEmpty()) {
			
			// Logging
			System.out.println("Companies examined: " + companiesExamined.size());
			System.out.println("Officers to be examined: " + officersToExamine.size());
			System.out.println("Officers examined:  " + companyOfficersExamined.size());
			
			// Pick the officer at the head of the queue to examine next
			CompanyOfficer officerToExamine = officersToExamine.poll();
			
			// Add the officer to the set of examined officers
			companyOfficersExamined.add(officerToExamine);
			
			// Get the companies associated with this officer
			Set<Company> companies = getCompanies(officerToExamine);
			
			// Remove any already examined
			companies.removeAll(companiesExamined);
			
			// Examine the remaining companies
			for(Company company : companies) {
				
				// Get all officers. The set compares entries by appointments link AND name
				Set<CompanyOfficer> officers = getOfficers(company.company_number);
				
				// Remove any already examined
				// TODO: should we compare by appointments AND names here?
				officers.removeAll(companyOfficersExamined.keySet());
				
				// Add the rest to the set of officers to examine
				officersToExamine.addAll(officers);
			}
			
			// Add new companies examined
			companiesExamined.addAll(companies);
			
			
			// XXX Monitor multiplicity of officer records
			for(Entry<CompanyOfficer, Set<CompanyOfficer>> entry : companyOfficersExamined.entrySet()) {
				
				CompanyOfficer prototype = entry.getKey();
				Set<CompanyOfficer> copies = entry.getValue();
				
				if(copies.size() > 1) {
					// Multiple records
					
					System.out.println("Found " + copies.size() + " multiples. Prototype:");
					System.out.println(prototype.toString());
					System.out.println("Copies:");
					for(CompanyOfficer copy : copies) {
						System.out.println(copy.toString());
					}
					
				}
			}
		}
	}
	
	/**
	 * Get the {@link Set} of all {@link Company} that a given {@link CompanyOfficer} is
	 * appointed to.
	 * 
	 * @param officer
	 * 	The {@link CompanyOfficer}.
	 * @return
	 * 	The {@link Set} of all {@link Company} that the given {@link CompanyOfficer} is
	 * appointed to.
	 * @throws IOException
	 * 	If there's a problem communicating with the Companies House database
	 * @throws InterruptedException 
	 */
	private static Set<Company> getCompanies(CompanyOfficer officer) throws IOException, InterruptedException {
		
		Appointments appointments = QueryUtil.getAppointments(officer.links.officer.appointments);
		
		Set<Company> companies = new HashSet<>();
		
		for(Appointment app : appointments.items) {
			companies.add(QueryUtil.getCompany(app.appointed_to.company_number));
		}
		
		return companies;
	}
	
	/**
	 * Get the {@link Set} of all {@link CompanyOfficer} for a given company number.
	 * 
	 * @param company
	 * 	The {@link Company}.
	 * @return
	 * 	The {@link Set} of all {@link CompanyOfficer} for the given {@link Company}.
	 * @throws IOException 
	 * 	If there's a problem communicating with the Companies House database
	 * @throws InterruptedException 
	 */
	private static Set<CompanyOfficer> getOfficers(String companyNumber) throws IOException, InterruptedException {
		
		Company company = QueryUtil.getCompany(companyNumber);
		
		return getOfficers(company);
	}

	/**
	 * Get the {@link Set} of all {@link CompanyOfficer} for a given {@link Company}.
	 * 
	 * @param company
	 * 	The {@link Company}.
	 * @return
	 * 	The {@link Set} of all {@link CompanyOfficer} for the given {@link Company}.
	 * @throws IOException 
	 * 	If there's a problem communicating with the Companies House database
	 * @throws InterruptedException 
	 */
	private static Set<CompanyOfficer> getOfficers(Company company) throws IOException, InterruptedException {
		
		// Get the array of officers for this company
		CompanyOfficers officers = QueryUtil.getCompanyOfficers(company.links.officers);
		
		Set<CompanyOfficer> officersSet = new TreeSet<>(CompanyOfficer.companyOfficerComparatorApptsAndName);
		officersSet.addAll(Arrays.asList(officers.items));
		
		return officersSet;
	}
}