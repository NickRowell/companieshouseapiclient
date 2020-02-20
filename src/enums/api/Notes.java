package enums.api;

import com.google.gson.annotations.SerializedName;

public enum Notes {

	@SerializedName("scottish-insolvency-info")
	scottishInsolvencyInfo("The law requires some Scottish company and LLP insolvency documents be filed with the Accountant in Bankruptcy (AIB). Find further details at [[AIB's register of insolvent companies]](http://roi.aib.gov.uk/ROI/)"),;

	Notes(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
