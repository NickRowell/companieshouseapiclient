package dm;

import java.io.Serializable;
import java.util.Date;

import enums.api.CompanyStatus;
import enums.api.CompanyType;

/**
 * 
 * This class provides a data model type to store the fields of the 'CompanySearch' resource returned by
 * the Companies House API.
 * 
 * @author nrowell
 */
public class CompanySearch implements Serializable {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 3808940666700757576L;

	/**
	 * The ETag of the resource, e.g. "6ff8a51bb8763c3b49bff68e9e621f8007ad8a80"
	 */
	public String etag;
	/**
	 * Housekeeping field returned by query.
	 */
	public int items_per_page;
	/**
	 * Data type returned by query.
	 */
	public String kind;
	/**
	 * Housekeeping field returned by query.
	 */
	public int start_index;
	/**
	 * Housekeeping field returned by query.
	 */
	public int total_results;
	/**
	 * The array of {@link CompanySearchCompany} returned by the search.
	 */
	public CompanySearchCompany[] items;
	
	/**
	 * Class encapsulates the data returned for each company in a companysearch resource.
	 * @author nrowell
	 *
	 */
	public static class CompanySearchCompany {
		/**
		 * The address.
		 */
		public Address address;
		/**
		 * The 'address snippet'.
		 */
		public String address_snippet;
		/**
		 * The company number
		 */
		public String company_number;
		/**
		 * The {@link CompanyStatus}.
		 */
		public CompanyStatus company_status;
		/**
		 * The {@link CompanyType}.
		 */
		public CompanyType company_type;
		/**
		 * The date which the company was converted/closed or dissolved. Please refer to company status to determine which.
		 */
		public Date date_of_cessation;
		/**
		 * The date when the company was created.
		 */
		public Date date_of_creation;
		
		
		public String description;
		public String[] description_identifier;
		public String external_registration_number;
		public String kind;

		public Links links;
		
		public Matches matches;
		public String snippet;
		public String title;
		
		
        public static class Matches implements Serializable {
        	/**
			 * The serial version UID.
			 */
			private static final long serialVersionUID = -7526839064470141436L;
			
			public int[] address_snippet;
        	public int[] snippet;
        	public int[] title;
        }
	}
}
