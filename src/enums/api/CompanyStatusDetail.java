package enums.api;

import com.google.gson.annotations.SerializedName;

public enum CompanyStatusDetail {

	@SerializedName("active")
	active(""),
	@SerializedName("dissolved")
	dissolved(""),
	@SerializedName("converted-closed")
	convertedClosed(""),
	@SerializedName("transferred-from-uk")
	transferredFromUk("Transfer from UK"),
	@SerializedName("active-proposal-to-strike-off")
	activeProposalToStrikeOff("Active proposal to strike off"),
	@SerializedName("petition-to-restore-dissolved")
	petitionToRestoreDissolved("Petition to restore dissolved"),
	@SerializedName("transformed-to-se")
	transformedToSe("Transformed to SE"),
	@SerializedName("converted-to-plc")
	convertedToPlc("Converted to PLC"),;

	CompanyStatusDetail(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
