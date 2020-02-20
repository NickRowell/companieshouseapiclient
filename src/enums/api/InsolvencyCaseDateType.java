package enums.api;

import com.google.gson.annotations.SerializedName;

public enum InsolvencyCaseDateType {

	@SerializedName("wound-up-on")
	woundUpOn("Commencement of winding up"),
	@SerializedName("petitioned-on")
	petitionedOn("Petition date"),
	@SerializedName("concluded-winding-up-on")
	concludedWindingUpOn("Conclusion of winding up"),
	@SerializedName("due-to-be-dissolved-on")
	dueToBeDissolvedOn("Due to be dissolved on"),
	@SerializedName("dissolved-on")
	dissolvedOn("Dissolved on"),
	@SerializedName("administration-started-on")
	administrationStartedOn("Administration started"),
	@SerializedName("administration-ended-on")
	administrationEndedOn("Administration ended"),
	@SerializedName("administration-discharged-on")
	administrationDischargedOn("Administration discharged"),
	@SerializedName("instrumented-on")
	instrumentedOn("Instrument date"),
	@SerializedName("moratorium-started-on")
	moratoriumStartedOn("Moratorium start date"),
	@SerializedName("ordered-to-wind-up-on")
	orderedToWindUpOn("Ordered to wind up"),
	@SerializedName("voluntary-arrangement-started-on")
	voluntaryArrangementStartedOn("Date of meeting to approve CVA"),
	@SerializedName("voluntary-arrangement-ended-on")
	voluntaryArrangementEndedOn("Date of completion or termination of CVA"),
	@SerializedName("declaration-solvent-on")
	declarationSolventOn("Declaration of solvency sworn on"),;

	InsolvencyCaseDateType(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
