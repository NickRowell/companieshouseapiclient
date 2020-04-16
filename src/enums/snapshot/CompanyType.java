package enums.snapshot;

import java.util.Arrays;

/**
 * Enumerates the different company types recorded in the Companies House database snapshot.
 * 
 * @author nrowell
 */
public enum CompanyType {

	ltd("Private Limited Company"),
	privateUnlimited("Private Unlimited Company", "Private Unlimited"),
	plc("Public limited company"),
	oldPublicCompany("Old public company"),
	privateLimitedGuarantNsc("Private limited by guarantee without share capital", "PRI/LTD BY GUAR/NSC (Private, limited by guarantee, no share capital)"),
	cic("Community Interest Company"),
	llp("Limited liability partnership"),
	privateLimitedGuarantNscLimitedExemption("Private Limited Company by guarantee without share capital, use of 'Limited' exemption",
			"PRI/LBG/NSC (Private, Limited by guarantee, no share capital, use of 'Limited' exemption)"),
	limitedPartnership("Limited partnership"),
	other("Other company type"),
	royalCharter("Royal charter company"),
	industrialAndProvidentSociety("Industrial and provident society"),
	registeredSociety("Registered Society"),
	icvcUmbrella("Investment Company with Variable Capital(Umbrella)"),
	icvcWarrant("Investment company with variable capital"),
	icvcSecurities("Investment Company with Variable Capital (Securities)"),
	privateLimitedSharesSection30Exemption("Private Limited Company, use of 'Limited' exemption","PRIV LTD SECT. 30 (Private limited company, section 30 of the Companies Act)"),
	europeanPublicLimitedLiabilityCompanySe("European public limited liability company (SE)", "European Public Limited-Liability Company (SE)"),
	cio("Charitable Incorporated Organisation"),
	scio("Scottish Charitable Incorporated Organisation"),
	sp("Scottish Partnership"),
	pcp("Protected Cell Company"),
	feasfcc("Further Education and Sixth Form College Corps"),
	;

	/**
	 * Main constructor.
	 * 
	 * @param descriptions
	 * 	One or more {@link String}s containing the textual representation of the company type. Some types are duplicated
	 * with similar strings of text.
	 */
	CompanyType(String ... descriptions) {
		this.descriptions = descriptions;
	}

	/**
	 * Array of {@link String}s that contain the textual representation of the company type. These are used to map to the
	 * enum type.
	 */
	private String[] descriptions;
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return Arrays.toString(descriptions);
	}
	
	/**
	 * Determines if the enum corresponds to the description given.
	 * 
	 * @param description
	 * 	The textual description of the company type.
	 * @return
	 * 	Boolean flag indicating if the given {@link String} corresponds to the current enum (true) or not (false).
	 */
	public boolean describes(String description) {
		for(String test : descriptions) {
			if(test.equalsIgnoreCase(description)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Parses a {@link CompanyType} from a string.
	 * 
	 * @param typeString
	 * 	The type of the company in string representation.
	 * @return
	 * 	The {@link CompanyType}, or null if this couldn't be determined.
	 */
	public static CompanyType parse(String typeString) {
		
		for(CompanyType candidate : CompanyType.values()) {
			if(candidate.describes(typeString)) {
				return candidate;
			}
		}
		
		return null;
	}
}
