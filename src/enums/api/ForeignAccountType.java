package enums.api;

import com.google.gson.annotations.SerializedName;

public enum ForeignAccountType {

	@SerializedName("accounting-requirements-of-originating-country-apply")
	accountingRequirementsOfOriginatingCountryApply("Accounting requirements of originating country apply"),
	@SerializedName("accounting-requirements-of-originating-country-do-not-apply")
	accountingRequirementsOfOriginatingCountryDoNotApply("Accounting requirements of originating country do not apply"),;

	ForeignAccountType(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
