package enums.api;

import com.google.gson.annotations.SerializedName;

public enum CompanySummary {

	@SerializedName("other")
	other("Other"),
	@SerializedName("private-unlimited")
	privateUnlimited("Private unlimited company"),
	@SerializedName("ltd")
	ltd("Private limited Company"),
	@SerializedName("plc")
	plc("Public limited Company"),
	@SerializedName("protected-cell-company")
	protectedCellCompany("Protected Cell Company"),
	@SerializedName("old-public-company")
	oldPublicCompany("Old public company"),
	@SerializedName("private-limited-guarant-nsc-limited-exemption")
	privateLimitedGuarantNscLimitedExemption("Private Limited Company by guarantee without share capital use of 'Limited' exemption"),
	@SerializedName("limited-partnership")
	limitedPartnership("Limited partnership"),
	@SerializedName("private-limited-guarant-nsc")
	privateLimitedGuarantNsc("Private company limited by guarantee without share capital"),
	@SerializedName("converted-or-closed")
	convertedOrClosed("Converted/closed company"),
	@SerializedName("private-unlimited-nsc")
	privateUnlimitedNsc("Private unlimited company without share capital"),
	@SerializedName("private-limited-shares-section-30-exemption")
	privateLimitedSharesSection30Exemption("Private limited company use of 'Limited' exemption"),
	@SerializedName("assurance-company")
	assuranceCompany("Assurance company"),
	@SerializedName("oversea-company")
	overseaCompany("Overseas company"),
	@SerializedName("eeig")
	eeig("European economic interest grouping (EEIG)"),
	@SerializedName("icvc-securities")
	icvcSecurities("Investment company with variable capital"),
	@SerializedName("icvc-warrant")
	icvcWarrant("Investment company with variable capital"),
	@SerializedName("icvc-umbrella")
	icvcUmbrella("Investment company with variable capital"),
	@SerializedName("registered-society-non-jurisdictional")
	registeredSocietyNonJurisdictional("Registered society"),
	@SerializedName("industrial-and-provident-society")
	industrialAndProvidentSociety("Industrial and provident Society"),
	@SerializedName("northern-ireland")
	northernIreland("Northern Ireland company"),
	@SerializedName("northern-ireland-other")
	northernIrelandOther("Credit union (Northern Ireland)"),
	@SerializedName("llp")
	llp("Limited liability partnership"),
	@SerializedName("royal-charter")
	royalCharter("Royal Charter company"),
	@SerializedName("investment-company-with-variable-capital")
	investmentCompanyWithVariableCapital("Investment company with variable capital"),
	@SerializedName("unregistered-company")
	unregisteredCompany("Unregistered company"),
	@SerializedName("european-public-limited-liability-company-se")
	europeanPublicLimitedLiabilityCompanySe("European public limited liability company (SE)"),
	@SerializedName("scottish-partnership")
	scottishPartnership("Scottish qualifying partnership"),;

	CompanySummary(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
