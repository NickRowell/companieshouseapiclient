package enums.api;

import com.google.gson.annotations.SerializedName;

public enum CessationLabelForStatus {

	@SerializedName("dissolved")
	dissolved("Dissolved on"),
	@SerializedName("converted-closed")
	convertedClosed("Closed on"),;

	CessationLabelForStatus(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
