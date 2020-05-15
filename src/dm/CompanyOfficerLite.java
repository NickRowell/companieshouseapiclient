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

/**
 * Stores very limited data on a {@link CompanyOfficer} that is only required to uniquely identify it when building
 * a graph. This provides significant memory optimzation. The full data for the {@link CompanyOfficer} is stored to
 * a file on disk for retrieval if needed.
 * 
 * @author nrowell
 * @version $Id$
 */
public class CompanyOfficerLite implements Serializable, Vertex {
	
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = -5821014163503901524L;

	/**
	 * Link to file containing a serialised {@link CompanyOfficer} containing the complete data.
	 */
	public String file_link;
	/**
	 * The officer's name.
	 */
	public String name;
	/**
	 * Date of birth.
	 */
	public DateYMD date_of_birth;
	/**
	 * Links to relevant URL resources.
	 */
	public Links links;
	
	/**
	 * The default constructor.
	 */
	public CompanyOfficerLite() {
		
	}
	
	/**
	 * Constructor used to derive a {@link CompanyOfficerLite} from a {@link CompanyOfficer}.
	 * 
	 * @param fullOfficer
	 * 	The {@link CompanyOfficer} containing full data; the relevant fields will be picked out to initialise 
	 * this {@link CompanyOfficerLite}.
	 */
	public CompanyOfficerLite(CompanyOfficer fullOfficer) {
		name = fullOfficer.name;
		date_of_birth = fullOfficer.date_of_birth;
		links = new Links();
		links.officer = new OfficerLink();
		links.officer.appointments = fullOfficer.links.officer.appointments;
	}
	
	/**
	 * Constructor taking only the appointments part of the officer links field. This uniquely identifies
	 * the {@link CompanyOfficerLite}, and is the field used in the {@link #equals(Object)} and {@link #hashCode()}
	 * methods. The other fields are not populated.
	 * 
	 * @param appointments
	 * 	The appointments link for the officer.
	 */
	public CompanyOfficerLite(String appointments) {
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
        final CompanyOfficerLite other = (CompanyOfficerLite) obj;
        
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
