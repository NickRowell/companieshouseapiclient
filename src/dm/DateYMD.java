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
 * This class provides a data model type to represent a date.
 *
 * Not all fields are always populated.
 *
 * @author nrowell
 * @version $Id$
 */
public class DateYMD implements Serializable {
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 731918515809522791L;
	
	/**
	 * The year
	 */
	public Integer year;
	/**
	 * The month
	 */
	public Integer month;
	/**
	 * The day
	 */
	public Integer day;
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		
		return String.format("%02d / %02d / %04d", day, month, year);
	}
}
