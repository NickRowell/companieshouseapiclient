package enums.api;

import com.google.gson.annotations.SerializedName;

public enum RegisterLocations {

	@SerializedName("registered-office")
	registeredOffice("Registered office address"),
	@SerializedName("single-alternative-inspection-location")
	singleAlternativeInspectionLocation("Registered inspection location"),
	@SerializedName("public-register")
	publicRegister("Public register"),
	@SerializedName("unspecified-location")
	unspecifiedLocation("Not on the public register"),;

	RegisterLocations(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
