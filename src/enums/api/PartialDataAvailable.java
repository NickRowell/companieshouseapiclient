package enums.api;

import com.google.gson.annotations.SerializedName;

public enum PartialDataAvailable {

	@SerializedName("full-data-available-from-financial-conduct-authority")
	fullDataAvailableFromFinancialConductAuthority("Refer to the Financial Conduct Authority for further information about this company"),
	@SerializedName("full-data-available-from-department-of-the-economy")
	fullDataAvailableFromDepartmentOfTheEconomy("Refer to the Department of the Economy for further information about this company"),
	@SerializedName("full-data-available-from-the-company")
	fullDataAvailableFromTheCompany("Contact the company directly for further information"),
	@SerializedName("full-data-available-from-financial-conduct-authority-mutuals-public-register")
	fullDataAvailableFromFinancialConductAuthorityMutualsPublicRegister("Refer to the Financial Conduct Authority Mutuals Public Register for further information about this company"),;

	PartialDataAvailable(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
