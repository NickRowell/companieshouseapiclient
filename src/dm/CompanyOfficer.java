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

package dm;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import enums.api.OfficerRole;
import util.CompaniesHouseUtil;
import util.CompaniesHouseUtil.NAME_SIMILARITY;

/**
 * Details of an officer.
 * 
 * Note that the {@link #hashCode()} method should not be used. Detecting identical {@link CompanyOfficer} records
 * requires matching several fields that can't be simply distilled into a hash code. This class is
 * designed to be stored in a {@link java.util.TreeSet}, where the {@link Comparable#compareTo()} method will be
 * used to identify unique members.
 * 
 * @author nrowell
 * @version $Id$
 */
public class CompanyOfficer implements Serializable {
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 7278261155140209617L;
	
	// Two comparators that are used for the following two purposes:
	
	// 1) Compare officers based on appointments links only. This is used to monitor the multiplicity of
	//    officers, and can be used when building the network if the name matching is to be disabled.
	// 2) Compare officers based on appointments links AND name. This is used when building the network to
	//    handle cases where the same person is recorded multiple times with different appointments links.
	
	public static final Comparator<CompanyOfficer> companyOfficerComparatorApptsOnly = new Comparator<CompanyOfficer>() {
	
		@Override
		public int compare(CompanyOfficer o1, CompanyOfficer o2) {
			
			String appt1 = o1.links.officer.appointments;
			String appt2 = o2.links.officer.appointments;
			
			return appt1.compareTo(appt2);
		}
	};
	
	public static final Comparator<CompanyOfficer> companyOfficerComparatorApptsAndName = new Comparator<CompanyOfficer>() {

		@Override
		public int compare(CompanyOfficer o1, CompanyOfficer o2) {

			String appt1 = o1.links.officer.appointments;
			String appt2 = o2.links.officer.appointments;
			
			// If appointments fields are equal then the officers are identical
			if(appt1.equals(appt2)) {
				return 0;
			}
			
			// Check name
			String name1 = o1.name;
			String name2 = o2.name;

			// If appointments fields are different then we need to check other fields to be sure
			NAME_SIMILARITY sim = CompaniesHouseUtil.resolveNames(name1, name2);
	        
			// XXX
//			System.out.println(name1 + " / " + name2 + " => " + sim.toString());
			
	        if(sim == NAME_SIMILARITY.SAME || sim == NAME_SIMILARITY.AMBIGUOUS_SIMILAR) {
	        	// Same names - officers are equal
	        	return 0;
	        }
	        else {
	        	// Different names - assign order based on String.compareTo()
	        	return name1.compareTo(name2);
	        }
		}
	};
	
	/**
	 * The officer's name.
	 */
	public String name;
	/**
	 * The officer's occupation.
	 */
	public String occupation;
	/**
	 * The officer's nationality.
	 */
	public String nationality;
	/**
	 * The officer's country of residence.
	 */
	public String country_of_residence;
	/**
	 * Date appointed.
	 */
	public Date appointed_on;
	/**
	 * Date resigned.
	 */
	public Date resigned_on;
	/**
	 * Date of birth.
	 */
	public DateYMD date_of_birth;
	/**
	 * The officer's role.
	 */
	public OfficerRole officer_role;
	/**
	 * Links to relevant URL resources.
	 */
	public Links links;
	/**
	 * The officer's address.
	 */
	public Address address;
	
    /**
     * {@inheritDoc}
     */
//    @Override
//    public boolean equals(Object obj) {
//    	
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final CompanyOfficer other = (CompanyOfficer) obj;
//        
//        // If appointments links are equal then the two officers are the same people
//        if(links.officer.appointments.equals(other.links.officer.appointments)) {
//        	return true;
//        }
//        
//        // Different appointments links - could still be the same person if the name is the same
//        NAME_SIMILARITY sim = CompaniesHouseUtil.resolveNames(this.name, other.name);
//        
//        if(sim == NAME_SIMILARITY.SAME || sim == NAME_SIMILARITY.AMBIGUOUS_SIMILAR) {
//        	return true;
//        }
//        else {
//        	return false;
//        }
//    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
    	// DON'T use hash sets to map officers; there's no suitable has function.
        throw new IllegalStateException( "This method must not be called" );
    }
    
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name = ").append(name).append("\n");
		sb.append("occupation = ").append(occupation).append("\n");
		sb.append("nationality = ").append(nationality).append("\n");
		sb.append("country_of_residence = ").append(country_of_residence).append("\n");
		sb.append("appointed_on = ").append(appointed_on.toString()).append("\n");
		sb.append("resigned_on = ").append(resigned_on).append("\n");
		sb.append("date_of_birth = ").append((date_of_birth == null ? "null" : date_of_birth.toString())).append("\n");
		sb.append("appointments = ").append(links.officer.appointments).append("\n");
		sb.append("role = ").append(this.officer_role).append("\n");
		return sb.toString();
	}

}
