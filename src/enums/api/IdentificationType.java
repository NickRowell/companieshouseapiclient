package enums.api;

import com.google.gson.annotations.SerializedName;

public enum IdentificationType {

	@SerializedName("non-eea")
	nonEea("Non European Economic Area"),
	@SerializedName("eea")
	eea("European Economic Area"),;

	IdentificationType(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
