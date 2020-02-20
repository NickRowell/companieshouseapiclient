package enums.api;

import com.google.gson.annotations.SerializedName;

public enum TermsOfAccountPublication {

	@SerializedName("accounts-publication-date-supplied-by-company")
	accountsPublicationDateSuppliedByCompany("Accounts publication date supplied by company"),
	@SerializedName("accounting-publication-date-does-not-need-to-be-supplied-by-company")
	accountingPublicationDateDoesNotNeedToBeSuppliedByCompany("Accounts publication date does not need to be supplied by company"),
	@SerializedName("accounting-reference-date-allocated-by-companies-house")
	accountingReferenceDateAllocatedByCompaniesHouse("Accounting reference date allocated by Companies House"),;

	TermsOfAccountPublication(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
