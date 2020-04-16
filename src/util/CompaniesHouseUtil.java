/*
 * Gaia CU5 DU10
 *
 * (c) 2005-2020 Gaia Data Processing and Analysis Consortium
 *
 *
 * CU5 photometric calibration software is free software; you can redistribute
 * it and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * CU5 photometric calibration software is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this CU5 software; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 *-----------------------------------------------------------------------------
 */

package util;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import dm.CompanyOfficer;
import enums.api.OfficerRole;

/**
 * Class contains general utilities for the Companies House applications.
 *
 * @author nrowell
 * @version $Id$
 */
public class CompaniesHouseUtil {
	
	/**
	 * Comparator used to sort map entries consisting of officer role and counter.
	 */
	public static Comparator<Map.Entry<OfficerRole, int[]>> officerRoleEntryComp = new Comparator<Map.Entry<OfficerRole, int[]>>() {
		@Override
		public int compare(Entry<OfficerRole, int[]> o1, Entry<OfficerRole, int[]> o2) {
			return Integer.compare(o2.getValue()[0], o1.getValue()[0]);
		}
	};
	
	/**
	 * This method determines if two names are the same. The names have the format:
	 * 
	 * ZUCH, Martin David
	 * OLIVER, Nicholas Martin
	 * PRIMA DIRECTOR LIMITED
	 * THOMAS, Andrew David
	 * HOOK, George William Hunter
	 * FRANCE, John Martin, Dr
	 * DICKINSON DEES
	 * CARE, Timothy James
	 * 
	 * Name similarity works on the following principles:
	 * 
	 * 1) Case is irrelevant, so Joe Bloggs = joe bloggs = jOe BLogGs
	 * 2) Different surname and/or forename = different person
	 * 3) Different middle names = different person, missing middle name(s) = ambiguous, not necessarily different
	 * 4) Title, if present, is ignored.
	 * 
	 * @param name1
	 * 	The first name to check
	 * @param name2
	 * 	The second name to check
	 * @param ignoreMissingMiddleNames
	 * 	To combat inconsistent recording of middle names the method can be instructed to only identify different
	 * names if the middle names are present and different. However this results in the method not being transitive, i.e.
	 * 
	 * "WRIGHT, Christopher James" = "WRIGHT, Christopher"
	 * "WRIGHT, Christopher John" = "WRIGHT, Christopher"
	 * "WRIGHT, Christopher James" != "WRIGHT, Christopher John"
	 *
	 * @return
	 * 	True if the names are the same, false otherwise.
	 * @throws IllegalArgumentException
	 * 	If one or both of the names are null or empty
	 */
	public static boolean sameNames(String name1, String name2, boolean ignoreMissingMiddleNames) throws IllegalArgumentException {
		
		// Sanity checks
		if(name1 == null) {
			throw new RuntimeException("Name 1 is null");
		}
		if(name2 == null) {
			throw new RuntimeException("Name 2 is null");
		}
		if(name1.isEmpty()) {
			throw new RuntimeException("Name 1 is empty");
		}
		if(name2.isEmpty()) {
			throw new RuntimeException("Name 2 is empty");
		}
		
		// Split on commas
    	String[] names1 = name1.split("[,]", 0);
    	// Remove any leading/trailing white space
    	for(int i=0; i<names1.length; i++) {
    		names1[i] = names1[i].trim();
    	}
    	
    	String[] names2 = name2.split("[,]", 0);
    	// Remove any leading/trailing white space
    	for(int i=0; i<names2.length; i++) {
    		names2[i] = names2[i].trim();
    	}
    	
    	// Names with only one field are likely to be corporate officers
    	if(names1.length == 1 && names2.length == 1) {
    		return names1[0].equalsIgnoreCase(names2[0]);
    	}
    	
    	// One is a corporate officer, one is a regular officer
    	if(names1.length == 1 && names2.length > 1) {
    		return false;
    	}
    	if(names2.length == 1 && names1.length > 1) {
    		return false;
    	}
    	
    	// At this point, both names have at least two elements.
    	// Compare first two elements, ignoring third element if present.
    	
    	String surname1 = names1[0];
    	String surname2 = names2[0];
    	
    	if(!surname1.equalsIgnoreCase(surname2)) {
    		// Different surnames - different people
    		return false;
    	}
    	
    	// Surnames are identical - check first name(s)
    	
    	// Check for middle names
    	String[] forenames1 = names1[1].split(" ", 0);
    	// Remove any leading/trailing whitespace
    	for(int i=0; i<forenames1.length; i++) {
    		forenames1[i] = forenames1[i].trim();
    	}
    	
    	// Check for middle names
    	String[] forenames2 = names2[1].split(" ", 0);
    	// Remove any leading/trailing whitespace
    	for(int i=0; i<forenames2.length; i++) {
    		forenames2[i] = forenames2[i].trim();
    	}
    	
    	// Compare first names
    	if(!forenames1[0].equalsIgnoreCase(forenames2[0])) {
    		// Different first names - different people
    		return false;
    	}
    	
    	// First name and surname are equal - check middle names
    	
    	int numMiddleNamesToCheck = 0;
    	
    	if(ignoreMissingMiddleNames) {
    		// Only middle names that are present must match.
    		numMiddleNamesToCheck = Math.min(forenames1.length, forenames2.length);
    	}
    	else {
    		// Check for different number of middle names
    		if(forenames1.length != forenames2.length) {
    			return false;
    		}
    		
    		// Middle names must match exactly.
    		numMiddleNamesToCheck = forenames1.length;
    	}
    	
    	for(int i = 1; i<numMiddleNamesToCheck; i++) {
    		if(!forenames1[i].equalsIgnoreCase(forenames2[i])) {
    			// Different middle names
    			return false;
    		}
    	}
    	
    	// Same middle names
    	return true;
	}
	
	/**
	 * Enumerates the possible outcomes of a test of the equality of two {@link CompanyOfficer}s.
	 */
	public static enum OFFICER_EQUALITY_TEST_OUTCOME {
		SAME, DIFFERENT, AMBIGUOUS;
	}
	
	/**
	 * Method used to determine if two {@link CompanyOfficer}s correspond to the same person, different people, or if
	 * the situation is ambiguous.
	 * 
	 * The comparison logic is as follows:
	 * 
	 *  - Same appointments link? -> Same officers.
	 *  - Different appointments link? -> Compare names.
	 *     - Different names? -> Different officers.
	 *     - Same names? -> Compare date of birth.
	 *        - Same DoB (month & year) -> Same officers.
	 *        - Different DoB -> Different officers.
	 *        - Missing DoB information -> Ambiguous.
	 * 
	 * @param o1
	 * 	The first {@link CompanyOfficer}.
	 * @param o2
	 * 	The second {@link CompanyOfficer}.
	 * @return
	 * An {@link OFFICER_EQUALITY_TEST_OUTCOME}.
	 */
	public static OFFICER_EQUALITY_TEST_OUTCOME compareOfficers(CompanyOfficer o1, CompanyOfficer o2) {

		String appt1 = o1.links.officer.appointments;
		String appt2 = o2.links.officer.appointments;
		
		// If appointments fields are equal then the officers are identical
		if(appt1.equals(appt2)) {
			return OFFICER_EQUALITY_TEST_OUTCOME.SAME;
		}
		
		// Check name
		String name1 = o1.name;
		String name2 = o2.name;

		// Compare names
        if(CompaniesHouseUtil.sameNames(name1, name2, false)) {
        	
        	if(o1.date_of_birth == null || o2.date_of_birth == null) {
        		// XXX Ambiguous -> same name, but missing DoB info that would allow to detect same officers.
        		// Would corporate officers end up here...? Only if badly recorded in the first place with numerous
        		// instances with different appointments links.
        		// TODO: Compare postcode?
        		
        		System.out.println("Different appointments: " + appt1 + "\t" + appt2);
        		System.out.println("Same name: " + name1 + " \\ " + name2);
        		System.out.println("Insufficient DoB info: " + o1.date_of_birth + "\t" + o2.date_of_birth);
        		System.out.println("Postcodes: " + o1.address.postal_code + "\t" + o2.address.postal_code);

        		return OFFICER_EQUALITY_TEST_OUTCOME.AMBIGUOUS;
        	}
        	
        	// Compare dates of birth (month & year)
        	if(o1.date_of_birth.month.equals(o2.date_of_birth.month) && o1.date_of_birth.year.equals(o2.date_of_birth.year)) {
        		// Same DoB (in addition to same name). Assume these are the same officer
        		return OFFICER_EQUALITY_TEST_OUTCOME.SAME;
        	}
        	else {
        		// Different dates of birth
        		return OFFICER_EQUALITY_TEST_OUTCOME.DIFFERENT;
        	}
        }
        else {
        	// Different names
        	return OFFICER_EQUALITY_TEST_OUTCOME.DIFFERENT;
        }
	}
	
}
