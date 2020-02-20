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

/**
 * Class contains general utilities for the Companies House applications.
 *
 * @author nrowell
 * @version $Id$
 */
public class CompaniesHouseUtil {
	
	/**
	 * Enum type to represent the outcome of name similarity test.
	 *
	 * SAME -> Names are functionally identical
	 * DIFFERENT -> Names are different
	 * AMBIGUOUS_SIMILAR -> Names are similar, i.e. maybe different middle names, different title or missing title in one
	 * CANT_CHECK -> Can't determine similarity, e.g. one or both names are null
	 */
	public static enum NAME_SIMILARITY {SAME, DIFFERENT, AMBIGUOUS_SIMILAR, CANT_CHECK};
	
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
	 * @return
	 */
	public static NAME_SIMILARITY resolveNames(String name1, String name2) {
		
		// Sanity checks
		if(name1 == null || name2 == null) {
			// Can't determine whether the names are the same
			return NAME_SIMILARITY.CANT_CHECK;
		}
		
		if(name1.isEmpty() || name2.isEmpty()) {
			// Can't determine whether the names are the same
			return NAME_SIMILARITY.CANT_CHECK;
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
    		if(names1[0].equalsIgnoreCase(names2[0])) {
    			return NAME_SIMILARITY.SAME;
    		}
    		else {
    			return NAME_SIMILARITY.DIFFERENT;
    		}
    	}
    	
    	if(names1.length == 1 && names2.length > 1) {
    		return NAME_SIMILARITY.DIFFERENT;
    	}
    	if(names2.length == 1 && names1.length > 1) {
    		return NAME_SIMILARITY.DIFFERENT;
    	}
    	
    	// At this point, both names have at least two elements.
    	// Compare first two elements, ignoring third element if present.
    	
    	if(!names1[0].equalsIgnoreCase(names2[0])) {
    		// Different surnames - different people
    		
    		// TODO what about name changes on marriage...?
    		return NAME_SIMILARITY.DIFFERENT;
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
    	
    	if(forenames1[0].equalsIgnoreCase(forenames2[0])) {
    		// Same surname and first name, possibly different middle names
    		// TODO: further resolve middle names?
    		return NAME_SIMILARITY.SAME;
    	}
    	
    	// Same surname, different middle names
    	return NAME_SIMILARITY.DIFFERENT;
	}
	
}
