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

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import dm.CompanyOfficer;

public class OfficerMap extends TreeMap<CompanyOfficer, Set<CompanyOfficer>> {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -3530373197402313660L;
	
	/**
	 * The default constructor.
	 */
	public OfficerMap() {
		super(CompanyOfficer.companyOfficerComparatorApptsAndName);
	}
	
	public boolean add(CompanyOfficer officer) {
		
		boolean retVal = false;
		
		// Find out if this officer is already in the map; check appointments link AND name etc.
		if(!containsKey(officer)) {
			// Internal set contains instances of CompanyOfficer with different appointments links but which
			// have been determined to correspond to the same person based on analysis of name and possibly
			// other fields.
			put(officer, new TreeSet<>(CompanyOfficer.companyOfficerComparatorApptsOnly));
			// New entry
			retVal = true;
		}
		
		// Look up the officer based on appointments link AND name etc.
		get(officer).add(officer);
		
		return retVal;
	}
}
