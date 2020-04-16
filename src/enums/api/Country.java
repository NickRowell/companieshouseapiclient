package enums.api;

import com.google.gson.annotations.SerializedName;

/**
 * TODO: this enum type might not be necessary.
 * 
 * @author nrowell
 *
 */
public enum Country {

	@SerializedName("Wales")
	wales("Wales"),
	@SerializedName("England")
	england("England"),
	@SerializedName("Scotland")
	scotland("Scotland"),
	@SerializedName("Great Britain")
	greatBritain("Great Britain"),
	@SerializedName("Not specified")
	notSpecified("Not specified"),
	@SerializedName("United Kingdom")
	unitedKingdom("United Kingdom"),
	@SerializedName("Northern Ireland")
	northernIreland("Northern Ireland"),;

	Country(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
