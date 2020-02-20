package enums.api;

import com.google.gson.annotations.SerializedName;

public enum FilingStatus {

	@SerializedName("in-progress")
	inProgress("In Progress"),
	@SerializedName("queued")
	queued("Queued"),
	@SerializedName("processing")
	processing("Processing"),
	@SerializedName("accepted")
	accepted("Accepted"),
	@SerializedName("rejected")
	rejected("Rejected"),;

	FilingStatus(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
