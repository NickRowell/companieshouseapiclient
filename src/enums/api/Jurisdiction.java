package enums.api;

import com.google.gson.annotations.SerializedName;

public enum Jurisdiction {

	@SerializedName("england-wales")
	englandWales("England/Wales"),
	@SerializedName("wales")
	wales("Wales"),
	@SerializedName("scotland")
	scotland("Scotland"),
	@SerializedName("northern-ireland")
	northernIreland("Northern Ireland"),
	@SerializedName("european-union")
	europeanUnion("European Union"),
	@SerializedName("united-kingdom")
	unitedKingdom("United Kingdom"),
	@SerializedName("england")
	england("England"),
	@SerializedName("noneu")
	noneu("Foreign (Non E.U.)"),;

	Jurisdiction(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
