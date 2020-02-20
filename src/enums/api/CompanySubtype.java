package enums.api;

import com.google.gson.annotations.SerializedName;

public enum CompanySubtype {

	@SerializedName("community-interest-company")
	communityInterestCompany("Community Interest Company (CIC)"),
	@SerializedName("private-fund-limited-partnership")
	privateFundLimitedPartnership("Private Fund Limited Partnership (PFLP)"),;

	CompanySubtype(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
