package enums.api;

import com.google.gson.annotations.SerializedName;

public enum CompanyStatus {

	@SerializedName("active")
	active("Active"),
	@SerializedName("dissolved")
	dissolved("Dissolved"),
	@SerializedName("liquidation")
	liquidation("Liquidation"),
	@SerializedName("receivership")
	receivership("Receiver Action"),
	@SerializedName("converted-closed")
	convertedClosed("Converted / Closed"),
	@SerializedName("voluntary-arrangement")
	voluntaryArrangement("Voluntary Arrangement"),
	@SerializedName("insolvency-proceedings")
	insolvencyProceedings("Insolvency Proceedings"),
	@SerializedName("administration")
	administration("In Administration"),
	@SerializedName("open")
	open("Open"),
	@SerializedName("closed")
	closed("Closed"),;

	CompanyStatus(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
