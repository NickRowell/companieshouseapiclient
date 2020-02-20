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
 * This class provides a data model type to represent an address.
 *
 * Not all fields are always populated.
 *
 * @author nrowell
 * @version $Id$
 */
public final class Address implements Serializable {
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = -3527389728663311969L;
	/**
	 * 	The first line of the address, e.g. "Cms Cameron Mckenna Llp Cannon Place"
	 */
	public String address_line_1;
	/**
	 * The second line of the address, e.g. "78 Cannon Street"
	 */
	public String address_line_2;
	/**
	 * The care of name.
	 */
	public String care_of;
	/**
	 * The country. Possible values are:
	 *  - Wales
	 *  - England
	 *  - Scotland
	 *  - Great Britain
	 *  - Not specified
	 *  - United Kingdom
	 *  - Northern Ireland
	 */
	public String country;
	/**
	 * The locality e.g. "London"
	 */
	public String locality;
	/**
	 * The post-office box number.
	 */
	public String po_box;
	/**
	 * The postal code e.g CF14 3UZ.
	 */
	public String postal_code;
	/**
	 * The property name or number.
	 */
	public String premises;
	/**
	 * The region e.g Surrey.
	 */
	public String region;
}
