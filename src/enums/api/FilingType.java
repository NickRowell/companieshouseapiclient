package enums.api;

import com.google.gson.annotations.SerializedName;

public enum FilingType {

	@SerializedName("change-registered-office-address")
	changeRegisteredOfficeAddress("(AD01) Change of Registered Office Address"),;

	FilingType(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
