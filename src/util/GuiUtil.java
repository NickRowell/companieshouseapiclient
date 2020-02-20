package util;

/**
 * Utilities for GUI building.
 *
 * @author nrowell
 * @version $Id$
 */
public class GuiUtil {
	
	/**
	 * String array for converting month number [0:11] to the corresponding month name.
	 */
	public static String[] months = {"January", "February", "March", "April", "May", "June",         
			   "July", "August", "September", "October", "November", "December"};
	
	/**
	 * Provides the ordinal indicator (e.g. 'st' for '1st') for days in the month.
	 */
	public static String[] ordinalIndicator = {
			"st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th",
			"th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
			"st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th",
			"st"};
	
	
}
