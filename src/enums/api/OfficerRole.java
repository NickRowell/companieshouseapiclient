package enums.api;

import com.google.gson.annotations.SerializedName;

public enum OfficerRole {

	@SerializedName("cic-manager")
	cicManager("CIC Manager"),
	@SerializedName("corporate-director")
	corporateDirector("Director"),
	@SerializedName("corporate-llp-designated-member")
	corporateLlpDesignatedMember("LLP Designated Member"),
	@SerializedName("corporate-llp-member")
	corporateLlpMember("LLP Member"),
	@SerializedName("corporate-manager-of-an-eeig")
	corporateManagerOfAnEeig("Manager of an EEIG"),
	@SerializedName("corporate-member-of-a-management-organ")
	corporateMemberOfAManagementOrgan("Member of a Management Organ"),
	@SerializedName("corporate-member-of-a-supervisory-organ")
	corporateMemberOfASupervisoryOrgan("Member of a Supervisory Organ"),
	@SerializedName("corporate-member-of-an-administrative-organ")
	corporateMemberOfAnAdministrativeOrgan("Member of an Administrative Organ"),
	@SerializedName("corporate-nominee-director")
	corporateNomineeDirector("Nominee Director"),
	@SerializedName("corporate-nominee-secretary")
	corporateNomineeSecretary("Nominee Secretary"),
	@SerializedName("corporate-secretary")
	corporateSecretary("Secretary"),
	@SerializedName("director")
	director("Director"),
	@SerializedName("general-partner-in-a-limited-partnership")
	generalPartnerInALimitedPartnership("General Partner in a Limited Partnership"),
	@SerializedName("judicial-factor")
	judicialFactor("Judicial Factor"),
	@SerializedName("limited-partner-in-a-limited-partnership")
	limitedPartnerInALimitedPartnership("Limited Partner in a Limited Partnership"),
	@SerializedName("llp-designated-member")
	llpDesignatedMember("LLP Designated Member"),
	@SerializedName("llp-member")
	llpMember("LLP Member"),
	@SerializedName("manager-of-an-eeig")
	managerOfAnEeig("Manager of an EEIG"),
	@SerializedName("member-of-a-management-organ")
	memberOfAManagementOrgan("Member of a Management Organ"),
	@SerializedName("member-of-a-supervisory-organ")
	memberOfASupervisoryOrgan("Member of a Supervisory Organ"),
	@SerializedName("member-of-an-administrative-organ")
	memberOfAnAdministrativeOrgan("Member of an Administrative Organ"),
	@SerializedName("nominee-director")
	nomineeDirector("Nominee Director"),
	@SerializedName("nominee-secretary")
	nomineeSecretary("Nominee Secretary"),
	@SerializedName("person-authorised-to-accept")
	personAuthorisedToAccept("Person Authorised to Accept"),
	@SerializedName("person-authorised-to-represent")
	personAuthorisedToRepresent("Person Authorised to Represent"),
	@SerializedName("person-authorised-to-represent-and-accept")
	personAuthorisedToRepresentAndAccept("Person Authorised to Represent and Accept"),
	@SerializedName("receiver-and-manager")
	receiverAndManager("Receiver and Manager"),
	@SerializedName("secretary")
	secretary("Secretary"),;

	OfficerRole(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
