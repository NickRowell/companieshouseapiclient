package enums.api;

import com.google.gson.annotations.SerializedName;

public enum ProofStatus {

	@SerializedName("paper")
	paper("PAPER"),
	@SerializedName("electronic")
	electronic("ELECTRONIC"),
	@SerializedName("statutory")
	statutory("STATUTORY"),
	@SerializedName("in-dispute")
	inDispute("IN DISPUTE"),
	@SerializedName("pending")
	pending("PENDING"),;

	ProofStatus(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
