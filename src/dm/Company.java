package dm;

import java.io.Serializable;
import java.util.Date;

import enums.api.AccountType;
import enums.api.CompanyStatus;
import enums.api.CompanyStatusDetail;
import enums.api.CompanyType;
import enums.api.ForeignAccountType;
import enums.api.Jurisdiction;
import enums.api.PartialDataAvailable;
import enums.api.TermsOfAccountPublication;

/**
 * This class provides a data model type to store the fields of a Company retrieved from the
 * Companies House API.
 *
 * @author nrowell
 * @version $Id$
 */
public class Company implements Serializable, Vertex {
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = -1117410266918257839L;
	/**
	 * Company accounts information.
	 */
	public Accounts accounts;
	/**
	 * Annual return information. This member is only returned if a confirmation statement has not be filed.
	 */
	public AnnualReturn annual_return;
	/**
	 * UK branch of a foreign company.
	 */
	public BranchCompanyDetails branch_company_details;
	/**
	 * Flag indicating whether this company can file.
	 */
	public Boolean can_file;
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
	/**
	 * Extra details about the status of the company. 
	 */
	public CompanyStatusDetail company_status_detail;
	/**
	 * Confirmation statement information.
	 */
	public ConfirmationStatement confirmation_statement;
	/**
	 * The date which the company was converted/closed or dissolved. Please refer to company status to determine which.
	 */
	public Date date_of_cessation;
	/**
	 * The date when the company was created.
	 */
	public Date date_of_creation;
	/**
	 * The ETag of the resource, e.g. "6ff8a51bb8763c3b49bff68e9e621f8007ad8a80"
	 */
	public String etag;
	/**
	 * Foreign company details.
	 */
	public ForeignCompanyDetails foreign_company_details;
	/**
	 * The flag indicating if the company has been liquidated in the past.
	 */
	public Boolean has_been_liquidated;
	/**
	 * The flag indicating if the company has any charges.
	 */
	public Boolean has_charges;
	/**
	 * The flag indicating if the company has insolvency history.
	 */
	public Boolean has_insolvency_history;
	/**
	 * The flag indicating if the company is a Community Interest Company.
	 */
	public Boolean is_community_interest_company;
	/**
	 * The jurisdiction specifies the political body responsible for the company.
	 */
	public Jurisdiction jurisdiction;
	/**
	 * The date of last full members list update.
	 */
	public Date last_full_members_list_date;
	/**
	 * A set of URLs related to the resource, including self.
	 */
	public Links links;
	/**
	 *	Returned if Companies House is not the primary source of data for this company.
	 */
	public PartialDataAvailable partial_data_available;
	/**
	 * The previous names of this company.
	 */
	public PreviousCompanyName[] previous_company_names;
	/**
	 * The address of the company's registered office.
	 */
	public Address registered_office_address;
	/**
	 * Flag indicating registered office address as been replaced.
	 */
	public Boolean registered_office_is_in_dispute;
	/**
	 * SIC codes for this company.
	 */
	public int[] sic_codes;
	/**
	 * The type of the company.
	 */
	public CompanyType type;
	/**
	 * Flag indicating whether post can be delivered to the registered office.
	 */
	public Boolean undeliverable_registered_office_address;
	
	/**
	 * TODO: move this out to dm package...?
	 * 
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
		 * The Accounting Reference Date (ARD) of the company.
		 */
		public AccountingReferenceDate accounting_reference_date;
		/**
		 * The last company accounts filed.
		 */
		public LastAccounts last_accounts;
		/**
		 * The next company accounts filed.
		 */
		public NextAccounts next_accounts;
		/**
		 * To be deprecated. Please use next_accounts.due_on.
		 */
		@Deprecated
		public Date next_due;
		/**
		 * To be deprecated. Please use next_accounts.period_end_on.
		 */
		@Deprecated
		public Date next_made_up_to;
		/**
		 * To be deprecated. Please use next_accounts.overdue
		 */
		@Deprecated
		public String overdue;
		
		/**
		 * TODO: move this out to dm package...?
		 */
		public static class AccountingReferenceDate implements Serializable {
			/**
			 * The serialVersionUID.
			 */
			private static final long serialVersionUID = 731918515809522791L;
			/**
			 * The Accounting Reference Date (ARD) month, e.g. "12"
			 */
			public Integer month;
			/**
			 * The Accounting Reference Date (ARD) day, e.g. "31"
			 */
			public Integer day;
		}
		/**
		 * TODO: move this out to dm package...?
		 */
		public static class NextAccounts implements Serializable {
			/**
			 * The serialVersionUID.
			 */
			private static final long serialVersionUID = -2761735160137721587L;
			/**
			 * The date the next company accounts are due, e.g. "2016-09-30"
			 */
			public Date due_on;
			/**
			 * Flag indicating if the company accounts are overdue.
			 */
			public Boolean overdue;
			/**
			 * The last day of the next accounting period to be filed, e.g. "2015-12-31"
			 */
			public Date period_end_on;
			/**
			 * The first day of the next accounting period to be filed, e.g. "2015-01-01"
			 */
			public Date period_start_on;
		}
		/**
		 * TODO: move this out to dm package...?
		 */
		public static class LastAccounts implements Serializable {
			/**
			 * The serialVersionUID.
			 */
			private static final long serialVersionUID = -3155029132362691849L;
			/**
			 * To be deprecated. Please use period_end_on.
			 */
			@Deprecated
			public Date made_up_to;
			/**
			 * The last day of the most recently filed accounting period, e.g. "2014-12-31"
			 */
			public Date period_end_on;
			/**
			 * The first day of the most recently filed accounting period, e.g. "2014-01-01"
			 */
			public Date period_start_on;
			/**
			 * The type of the last company accounts filed
			 */
			public AccountType type;
		}
	}

	/**
	 * TODO: move this out to dm package...?
	 * 
	 * Annual return information. This member is only returned if a confirmation statement has not be filed.
	 * @author nrowell
	 * @version $Id$
	 */
	public static class AnnualReturn implements Serializable {
		/**
		 * The serialVersionUID.
		 */
		private static final long serialVersionUID = 6991447969024583690L;
		/**
		 * 	The date the last annual return was made up to.
		 */
		public Date last_made_up_to;
		/**
		 * The date the next annual return is due. This member will only be returned if a confirmation statement
		 * has not been filed and the date is before 28th July 2016, otherwise refer to confirmation_statement.next_due
		 */
		public Date next_due;
		/**
		 * The date the next annual return should be made up to. This member will only be returned if a confirmation statement
		 * has not been filed and the date is before 30th July 2016, otherwise refer to confirmation_statement.next_made_up_to
		 */
		public Date next_made_up_to;
		/**
		 * Flag indicating if the annual return is overdue.
		 */
		public Boolean overdue;
	}
	
	/**
	 * TODO: move this out to dm package...?
	 * 
	 * UK branch of a foreign company.
	 *
	 * @author nrowell
	 * @version $Id$
	 */
	public class BranchCompanyDetails implements Serializable {
		/**
		 * The serialVersionUID.
		 */
		private static final long serialVersionUID = -76686940686747323L;
		/**
		 * Type of business undertaken by the UK establishment.
		 */
		public String business_activity;
		/**
		 * Parent company name.
		 */
		public String parent_company_name;
		/**
		 * Parent company number.
		 */
		public String parent_company_number;
	}
	
	/**
	 * TODO: move this out to dm package...?
	 * 
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
		/**
		 * The date to which the company must next make a confirmation statement.
		 */
		public Date next_made_up_to;
		/**
		 * Flag indicating if the confirmation statement is overdue.
		 */
		public Boolean overdue;
	}

	/**
	 * TODO: move this out to dm package...?
	 * 
	 * Foreign company details.
	 *
	 * @author nrowell
	 * @version $Id$
	 */
	public static class ForeignCompanyDetails implements Serializable {
		/**
		 * The serialVersionUID.
		 */
		private static final long serialVersionUID = 6548540423009387023L;
		/**
		 * Accounts requirement.
		 */
		public AccountingRequirement accounting_requirement;
		/**
		 * Foreign company account information.
		 */
		public Accounts accounts;
		/**
		 * Type of business undertaken by the company.
		 */
		public String business_activity;
		/**
		 * 	Legal form of the company in the country of incorporation.
		 */
		public String company_type;
		/**
		 * Law governing the company in country of incorporation.
		 */
		public String governed_by;
		/**
		 * Is it a financial or credit institution.
		 */
		public Boolean is_a_credit_finance_institution;
		/**
		 * Company origin informations.
		 */
		public OriginatingRegistry originating_registry;
		/**
		 * Registration number in company of incorporation.
		 */
		public String registration_number;

		/**
		 * TODO: move this out to dm package...?
		 */
		public static class AccountingRequirement implements Serializable {
			/**
			 * The serialVersionUID.
			 */
			private static final long serialVersionUID = -5955149846047668009L;
			/**
			 * Type of accounting requirement that applies.
			 */
			public ForeignAccountType foreign_account_type;
			/**
			 * Describes how the publication date is derived.
			 */
			public TermsOfAccountPublication terms_of_account_publication;
		}
		/**
		 * TODO: move this out to dm package...?
		 */
		public static class Accounts implements Serializable {
			/**
			 * The serialVersionUID.
			 */
			private static final long serialVersionUID = 3178835592348026591L;
			/**
			 * Date account period starts under parent law.
			 */
			public AccountPeriodFrom account_period_from;
			/**
			 * Date account period ends under parent law.
			 */
			public AccountPeriodTo account_period_to;
			/**
			 * Time allowed from period end for disclosure of accounts under parent law.
			 */
			public MustFileWithin must_file_within;

			/**
			 * TODO: move this out to dm package...?
			 */
			public static class AccountPeriodFrom implements Serializable {
				/**
				 * The serialVersionUID.
				 */
				private static final long serialVersionUID = -7988980767156699748L;
				/**
				 * Day on which accounting period starts under parent law.
				 */
				public Integer day;
				/**
				 * Month in which accounting period starts under parent law.
				 */
				public Integer month;
			}
			/**
			 * TODO: move this out to dm package...?
			 */
			public static class AccountPeriodTo implements Serializable {
				/**
				 * The serialVersionUID.
				 */
				private static final long serialVersionUID = -6099248469332781817L;
				/**
				 * Day on which accounting period ends under parent law.
				 */
				public Integer day;
				/**
				 * Month in which accounting period ends under parent law.
				 */
				public Integer month;
			}
			/**
			 * TODO: move this out to dm package...?
			 */
			public static class MustFileWithin implements Serializable {
				/**
				 * The serialVersionUID.
				 */
				private static final long serialVersionUID = 7738599929773832110L;
				/**
				 * Number of months within which to file.
				 */
				public Integer months;
			}
		}
		/**
		 * TODO: move this out to dm package...?
		 */
		public static class OriginatingRegistry implements Serializable {
			/**
			 * The serialVersionUID.
			 */
			private static final long serialVersionUID = 7632271445577474385L;
			/**
			 * Country in which company was incorporated.
			 */
			public String country;
			/**
			 * Identity of register in country of incorporation.
			 */
			public String name;
		}
	}

	/**
	 * TODO: move this out to dm package...?
	 * 
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
		 * The date from which the company name was effective.
		 */
		public Date effective_from;
		/**
		 * The previous company name.
		 */
		public String name;
	}
	
	/**
	 * The default constructor.
	 */
	public Company() {
		
	}
	
	/**
	 * Constructor taking only the company number. This uniquely identifies the {@link Company}, and is the
	 * field used in the {@link #equals(Object)} and {@link #hashCode()} methods. The other fields are not
	 * populated.
	 * 
	 * @param company_number
	 * 	The company number.
	 */
	public Company(String company_number) {
		this.company_number = company_number;
	}
	
	@Override
	public String toString() {
		return company_number;
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
        final Company other = (Company) obj;
        
        // Match on company number
        return company_number.equals(other.company_number);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return company_number.hashCode();
    }
}
