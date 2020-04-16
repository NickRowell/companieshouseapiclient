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
import java.util.Date;

import enums.api.OfficerRole;

/**
 * Details of a company officer.
 * 
 * @author nrowell
 * @version $Id$
 */
public class CompanyOfficer implements Serializable, Vertex {
	
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 7278261155140209617L;
	
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
	 * The default constructor.
	 */
	public CompanyOfficer() {
		
	}
	
	/**
	 * Constructor taking only the appointments part of the officer links field. This uniquely identifies
	 * the {@link CompanyOfficer}, and is the field used in the {@link #equals(Object)} and {@link #hashCode()}
	 * methods. The other fields are not populated.
	 * 
	 * @param appointments
	 * 	A string containing the officer appointments link.
	 */
	public CompanyOfficer(String appointments) {
		links = new Links();
		links.officer = new OfficerLink();
		links.officer.appointments = appointments;
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
    	
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompanyOfficer other = (CompanyOfficer) obj;
        
        // If appointments links are equal then the two officers are the same people
        if(links.officer.appointments.equals(other.links.officer.appointments)) {
        	return true;
        }
        
        return false;
    }
	
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return links.officer.appointments.hashCode();
    }
    
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return name;
		
//		StringBuilder sb = new StringBuilder();
//		sb.append("name = ").append(name).append("\n");
//		sb.append("occupation = ").append(occupation).append("\n");
//		sb.append("nationality = ").append(nationality).append("\n");
//		sb.append("country_of_residence = ").append(country_of_residence).append("\n");
//		sb.append("appointed_on = ").append(appointed_on.toString()).append("\n");
//		sb.append("resigned_on = ").append(resigned_on).append("\n");
//		sb.append("date_of_birth = ").append((date_of_birth == null ? "null" : date_of_birth.toString())).append("\n");
//		sb.append("appointments = ").append(links.officer.appointments).append("\n");
//		sb.append("role = ").append(this.officer_role).append("\n");
//		return sb.toString();
	}
}
