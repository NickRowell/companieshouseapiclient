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

import enums.api.CompanyStatus;
import enums.api.OfficerRole;

/**
 *   {"occupation":"Retired"},
 *    
 *   {"occupation":"Director",
 *    "resigned_on":"2019-01-21",},
 *    
 *   {"occupation":"Operational Director",
 *    "resigned_on":"2007-06-07",},
 *    
 *   {"resigned_on":"2001-09-20",
 *    "occupation":"Manager"}
 *
 *
 * @author nrowell
 * @version $Id$
 */
public class Appointment implements Serializable {

	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 523983116149298801L;
	/**
	 * The details of the company appointed to.
	 */
	public AppointedTo appointed_to;
	/**
	 * Date appointed.
	 */
	public Date appointed_on;
	/**
	 * Date resigned.
	 */
	public Date resigned_on;
	/**
	 * Links to relevant URL resources (only company is populated).
	 */
	public Links links;
	/**
	 * The name elements.
	 */
	public NameElements name_elements;
	/**
	 * The country of residence.
	 */
	public String country_of_residence;
	/**
	 * The officer role.
	 */
	public OfficerRole officer_role;
	/**
	 * The appointment address, i.e. of the company.
	 */
	public Address address;
	/**
	 * The appointee's name.
	 */
	public String name;
	/**
	 * The appointee's nationality.
	 */
	public String nationality;
	/**
	 * The appointee's occupation.
	 */
	public String occupation;
	
	/**
	 * Contains fields of the appointed_to node
	 */
	public static class AppointedTo implements Serializable {
		/**
		 * The serialVersionUID.
		 */
		private static final long serialVersionUID = -7737753684380351759L;
		/**
		 * The name of the company, e.g. "MARINE AND GENERAL MUTUAL LIFE ASSURANCE SOCIETY"
		 */
		public String company_name;
		/**
		 * The number of the company, e.g. "00000006"
		 */
		public String company_number;
		/**
		 * The status of the company. 
		 */
		public CompanyStatus company_status;
	}

	/**
	 * Contains fields of the name_elements node.
	 * 
	 */
	public static class NameElements implements Serializable {

		/**
		 * The serialVersionUID.
		 */
		private static final long serialVersionUID = -4520306612892262868L;

		/**
		 * Other forenames.
		 */
		public String other_forenames;
		/**
		 * Title.
		 */
		public String title;
		/**
		 * Forename.
		 */
		public String forename;
		/**
		 * Surname.
		 */
		public String surname;
	}
}
