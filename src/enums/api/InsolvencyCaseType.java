package enums.api;

import com.google.gson.annotations.SerializedName;

public enum InsolvencyCaseType {

	@SerializedName("compulsory-liquidation")
	compulsoryLiquidation("Compulsory liquidation"),
	@SerializedName("in-administration")
	inAdministration("In administration"),
	@SerializedName("creditors-voluntary-liquidation")
	creditorsVoluntaryLiquidation("Creditors voluntary liquidation"),
	@SerializedName("members-voluntary-liquidation")
	membersVoluntaryLiquidation("Members voluntary liquidation"),
	@SerializedName("foreign-insolvency")
	foreignInsolvency("Foreign insolvency"),
	@SerializedName("administrative-receiver")
	administrativeReceiver("Administrative receiver appointed"),
	@SerializedName("scottish-administrative-receiver")
	scottishAdministrativeReceiver("Receiver (Scotland) appointed"),
	@SerializedName("administration-order")
	administrationOrder("Administration order"),
	@SerializedName("corporate-voluntary-arrangement")
	corporateVoluntaryArrangement("Corporate voluntary arrangement (CVA)"),
	@SerializedName("receiver-manager")
	receiverManager("Receiver/Manager appointed"),
	@SerializedName("corporate-voluntary-arrangement-moratorium")
	corporateVoluntaryArrangementMoratorium("Corporate voluntary arrangement moratorium"),;

	InsolvencyCaseType(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
