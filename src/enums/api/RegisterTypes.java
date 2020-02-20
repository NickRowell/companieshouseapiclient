package enums.api;

import com.google.gson.annotations.SerializedName;

public enum RegisterTypes {

	@SerializedName("directors")
	directors("Directors"),
	@SerializedName("secretaries")
	secretaries("Secretaries"),
	@SerializedName("persons-with-significant-control")
	personsWithSignificantControl("Person(s) with significant control (PSCs)"),
	@SerializedName("usual-residential-address")
	usualResidentialAddress("Usual residential addresses"),
	@SerializedName("members")
	members("Members"),
	@SerializedName("llp-members")
	llpMembers("Members"),
	@SerializedName("llp-usual-residential-address")
	llpUsualResidentialAddress("Usual residential addresses"),;

	RegisterTypes(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
