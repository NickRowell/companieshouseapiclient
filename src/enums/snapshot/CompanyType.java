package enums.snapshot;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

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

	
//	@SerializedName("converted-or-closed")
//	convertedOrClosed("Converted / closed"),
	
//	@SerializedName("private-unlimited-nsc")
//	privateUnlimitedNsc("Private unlimited company without share capital"),
	
//	@SerializedName("protected-cell-company")
//	protectedCellCompany("Protected cell company"),
	
//	@SerializedName("assurance-company")
//	assuranceCompany("Assurance company"),
	
//	@SerializedName("oversea-company")
//	overseaCompany("Overseas company"),
	
//	@SerializedName("eeig")
//	eeig("European economic interest grouping (EEIG)"),
	
//	@SerializedName("icvc-securities")
//	icvcSecurities("Investment company with variable capital"),
	
	
//	@SerializedName("northern-ireland")
//	northernIreland("Northern Ireland company"),
	
//	@SerializedName("northern-ireland-other")
//	northernIrelandOther("Credit union (Northern Ireland)"),
	

//	@SerializedName("unregistered-company")
//	unregisteredCompany("Unregistered company"),
	

//	@SerializedName("uk-establishment")
//	ukEstablishment("UK establishment company"),
	
//	@SerializedName("scottish-partnership")
//	scottishPartnership("Scottish qualifying partnership"),
	
	;

	CompanyType(String ... descriptions) {
		this.descriptions = descriptions;
	}

	private String[] descriptions;
	
	public String toString() {
		return Arrays.toString(descriptions);
	}
	
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
