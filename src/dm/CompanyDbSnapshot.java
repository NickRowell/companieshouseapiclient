package dm;

import java.io.Serializable;
import java.util.Date;

import enums.snapshot.AccountType;
import enums.snapshot.CompanyStatus;
import enums.snapshot.CompanyType;

/**
 * This class provides a data model type to store the fields of a Company parsed from the
 * Companies House database snapshot.
 *
 * @author nrowell
 * @version $Id$
 */
public class CompanyDbSnapshot implements Serializable {
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = -1117410266918257839L;
	/**
	 * The name of the company, e.g. "MARINE AND GENERAL MUTUAL LIFE ASSURANCE SOCIETY"
	 */
	public String company_name;
	/**
	 * The number of the company, e.g. "00000006"
	 */
	public String company_number;
	/**
	 * The address of the company's registered office.
	 */
	public RegisteredOfficeAddress registered_office_address;
	/**
	 * The type of the company.
	 */
	public CompanyType type;
	/**
	 * The status of the company. 
	 */
	public CompanyStatus company_status;
	/**
	 * The country of origin, e.g. "United Kingdom"
	 */
	public String country_of_origin;
	/**
	 * The date which the company was converted/closed or dissolved. Please refer to company status to determine which.
	 */
	public Date dissolution_date;
	/**
	 * The date when the company was created.
	 */
	public Date incorporation_date;
	/**
	 * Company accounts information.
	 */
	public Accounts accounts;
	/**
	 * Account category information.
	 */
	public AccountType account_category;
	/**
	 * Annual return information. This member is only returned if a confirmation statement has not be filed.
	 */
	public Returns annual_return;
	/**
	 * The mortgages information.
	 */
	public Mortgages mortgages;
	/**
	 * SIC codes for this company.
	 */
	public int[] sic_codes;
	/**
	 * The limited partners information.
	 */
	public LimitedPartnerships limited_partnerships;
	/**
	 * A set of URLs related to the resource, including self.
	 */
	public Links links;
	/**
	 * The previous names of this company.
	 */
	public PreviousCompanyName[] previous_company_names;
	/**
	 * Confirmation statement information.
	 */
	public ConfirmationStatement confirmation_statement;
	
	/**
	 * The address of the company's registered office.
	 * 
	 * @author nrowell
	 * @version $Id$
	 */
	public static class RegisteredOfficeAddress implements Serializable {
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
	/**
	 * Company accounts information.
	 * 
	 * @author nrowell
	 * @version $Id$
	 */
	public static class Accounts implements Serializable {
		/**
		 * The serialVersionUID.
		 */
		private static final long serialVersionUID = -9060090413670026155L;
		/**
		 * The Accounting Reference Date (ARD) month, e.g. "12"
		 */
		public Integer account_ref_month;
		/**
		 * The Accounting Reference Date (ARD) day, e.g. "31"
		 */
		public Integer account_ref_day;
		/**
		 * The next due date
		 */
		public Date next_due_date;
		/**
		 * The last made up date
		 */
		public Date last_made_up_date;
	}
	
	/**
	 * Annual return information. This member is only returned if a confirmation statement has not be filed.
	 * @author nrowell
	 * @version $Id$
	 */
	public static class Returns implements Serializable {
		/**
		 * The serialVersionUID.
		 */
		private static final long serialVersionUID = 6991447969024583690L;
		/**
		 * 	The date the last annual return was made up to.
		 */
		public Date last_made_up_date;
		/**
		 * The date the next annual return is due. This member will only be returned if a confirmation statement
		 * has not been filed and the date is before 28th July 2016, otherwise refer to confirmation_statement.next_due
		 */
		public Date next_due_date;
	}
	
	/**
	 * Mortgages information
	 * @author nrowell
	 * @version $Id$
	 */
	public static class Mortgages implements Serializable {
		/**
		 * The serialVersionUID.
		 */
		private static final long serialVersionUID = 974433203571879199L;
		/**
		 * The number of mortgage charges.
		 */
		public int num_mort_charges;
		/**
		 * The number of mortgages outstanding.
		 */
		public int num_mort_outstanding;
		/**
		 * The number of mortgages part satisfied.
		 */
		public int num_mort_part_satisfied;
		/**
		 * The number of mortgages satisfied.
		 */
		public int num_mort_satisfied;
	}
	
	/**
	 * Limited partnerships information.
	 * @author nrowell
	 * @version $Id$
	 */
	public static class LimitedPartnerships implements Serializable {
		/**
		 * The serialVersionUID.
		 */
		private static final long serialVersionUID = 4619793575854445552L;
		/**
		 * Number of general partners.
		 */
		public int num_gen_partners;
		/**
		 * Number of limited partners.
		 */
		public int num_lim_partners;
	}

	/**
	 * A set of URLs related to the resource, including self.
	 * 
	 * @author nrowell
	 * @version $Id$
	 */
	public static class Links implements Serializable {
		/**
		 * The serialVersionUID.
		 */
		private static final long serialVersionUID = 3033779799967794027L;
		/**
		 * The URL of the resource.
		 */
		public String self;
	}

	/**
	 * Details of a previous company name.
	 * 
	 * @author nrowell
	 * @version $Id$
	 */
	public static class PreviousCompanyName implements Serializable {
		/**
		 * The serialVersionUID.
		 */
		private static final long serialVersionUID = 7278261155140209617L;
		/**
		 * The date on which the company name ceased.
		 */
		public Date ceased_on;
		/**
		 * The previous company name.
		 */
		public String name;
	}

	/**
	 * Confirmation statement information.
	 * 
	 * @author nrowell
	 * @version $Id$
	 */
	public static class ConfirmationStatement implements Serializable {
		/**
		 * The serialVersionUID.
		 */
		private static final long serialVersionUID = 7751978978897335119L;
		/**
		 * The date to which the company last made a confirmation statement.
		 */
		public Date last_made_up_to;
		/**
		 * The date by which the next confimation statement must be received.
		 */
		public Date next_due;
	}
}
