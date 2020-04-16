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
 * A set of URLs related to a particular resource and providing links to other related resources.
 *
 * @author nrowell
 * @version $Id$
 */
public class Links implements Serializable {
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 3033779799967794027L;
	/**
	 * The nested URL of the officer appointments resource.
	 */
	public OfficerLink officer;
	/**
	 * The URL of the company's charge resource.
	 */
	public String charges;
	/**
	 * The URL of the company resource.
	 */
	public String company;
	/**
	 * The URL of the company's filing history list resource.
	 */
	public String filing_history;
	/**
	 * The URL of the company's insolvency list resource.
	 */
	public String insolvency;
	/**
	 * The URL of the company's officer list resource.
	 */
	public String officers;
	/**
	 * The URL of the company's persons with significant control list resource.
	 */
	public String persons_with_significant_control;
	/**
	 * The URL of the company's persons with significant control statements list resource
	 */
	public String persons_with_significant_control_statements;
	/**
	 * The URL of the registers resource for this company.
	 */
	public String registers;
	/**
	 * The URL of the resource.
	 */
	public String self;
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("officer.appointments = ").append(officer.appointments).append("\n");
		sb.append("charges = ").append(charges).append("\n");
		sb.append("company = ").append(company).append("\n");
		sb.append("filing_history = ").append(filing_history).append("\n");
		sb.append("insolvency = ").append(insolvency).append("\n");
		sb.append("officers = ").append(officers).append("\n");
		sb.append("persons_with_significant_control = ").append(persons_with_significant_control).append("\n");
		sb.append("persons_with_significant_control_statements = ").append(persons_with_significant_control_statements).append("\n");
		sb.append("registers = ").append(registers).append("\n");
		sb.append("self = ").append(self).append("\n");
		return sb.toString();
	}
}
