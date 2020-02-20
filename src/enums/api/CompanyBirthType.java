package enums.api;

import com.google.gson.annotations.SerializedName;

public enum CompanyBirthType {

	@SerializedName("private-unlimited")
	privateUnlimited("Incorporated on"),
	@SerializedName("ltd")
	ltd("Incorporated on"),
	@SerializedName("plc")
	plc("Incorporated on"),
	@SerializedName("old-public-company")
	oldPublicCompany("Incorporated on"),
	@SerializedName("private-limited-guarant-nsc-limited-exemption")
	privateLimitedGuarantNscLimitedExemption("Incorporated on"),
	@SerializedName("limited-partnership")
	limitedPartnership("Registered on"),
	@SerializedName("private-limited-guarant-nsc")
	privateLimitedGuarantNsc("Incorporated on"),
	@SerializedName("converted-or-closed")
	convertedOrClosed("Incorporated on"),
	@SerializedName("private-unlimited-nsc")
	privateUnlimitedNsc("Incorporated on"),
	@SerializedName("private-limited-shares-section-30-exemption")
	privateLimitedSharesSection30Exemption("Incorporated on"),
	@SerializedName("assurance-company")
	assuranceCompany("Registered on"),
	@SerializedName("oversea-company")
	overseaCompany("First UK establishment opened on"),
	@SerializedName("eeig")
	eeig("Registered on"),
	@SerializedName("icvc-securities")
	icvcSecurities("Incorporated on"),
	@SerializedName("icvc-warrant")
	icvcWarrant("Incorporated on"),
	@SerializedName("icvc-umbrella")
	icvcUmbrella("Incorporated on"),
	@SerializedName("industrial-and-provident-society")
	industrialAndProvidentSociety("Incorporated on"),
	@SerializedName("northern-ireland")
	northernIreland("Incorporated on"),
	@SerializedName("northern-ireland-other")
	northernIrelandOther("Incorporated on"),
	@SerializedName("llp")
	llp("Incorporated on"),
	@SerializedName("royal-charter")
	royalCharter("Incorporated on"),
	@SerializedName("investment-company-with-variable-capital")
	investmentCompanyWithVariableCapital("Incorporated on"),
	@SerializedName("unregistered-company")
	unregisteredCompany("Formed on"),
	@SerializedName("other")
	other("Incorporated on"),
	@SerializedName("european-public-limited-liability-company-se")
	europeanPublicLimitedLiabilityCompanySe("Formed on"),
	@SerializedName("uk-establishment")
	ukEstablishment("Opened on"),
	@SerializedName("scottish-partnership")
	scottishPartnership("Registered on"),;

	CompanyBirthType(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
