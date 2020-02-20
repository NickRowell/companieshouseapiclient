package enums.api;

import com.google.gson.annotations.SerializedName;

public enum AccountType {

	@SerializedName("null")
	_null("Null"),
	@SerializedName("full")
	full("Full"),
	@SerializedName("small")
	small("Small"),
	@SerializedName("medium")
	medium("Medium"),
	@SerializedName("group")
	group("Group"),
	@SerializedName("dormant")
	dormant("Dormant"),
	@SerializedName("interim")
	interim("Interim"),
	@SerializedName("initial")
	initial("Initial"),
	@SerializedName("total-exemption-full")
	totalExemptionFull("Total Exemption Full"),
	@SerializedName("total-exemption-small")
	totalExemptionSmall("Total Exemption Small"),
	@SerializedName("partial-exemption")
	partialExemption("Partial Exemption"),
	@SerializedName("audit-exemption-subsidiary")
	auditExemptionSubsidiary("Audit Exemption Subsidiary"),
	@SerializedName("filing-exemption-subsidiary")
	filingExemptionSubsidiary("Filing Exemption Subsidiary"),
	@SerializedName("micro-entity")
	microEntity("Micro Entity"),;

	AccountType(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
