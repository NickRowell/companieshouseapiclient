package enums.snapshot;

import java.util.Arrays;

public enum AccountType {

	_null("", "ACCOUNTS TYPE NOT AVAILABLE"),
	noAccountsFiled("NO ACCOUNTS FILED"),
	full("Full"),
	small("Small"),
	medium("Medium"),
	group("Group"),
	dormant("Dormant"),
	initial("Initial"),
	totalExemptionFull("Total Exemption Full"),
	totalExemptionSmall("Total Exemption Small"),
	partialExemption("Partial Exemption"),
	auditExemptionSubsidiary("Audit Exemption Subsidiary"),
	filingExemptionSubsidiary("Filing Exemption Subsidiary"),
	auditedAbridged("AUDITED ABRIDGED"),
	unauditedAbridged("UNAUDITED ABRIDGED"),
	
	// Values present in the Companies House API but not the database snapshot:
//	microEntity("Micro Entity"),
//	interim("Interim"),
	;

	AccountType(String ... descriptions) {
		this.descriptions = descriptions;
	}

	private String[] descriptions;
	
	public String toString() {
		return Arrays.toString(descriptions);
	}
	
	public boolean describes(String description) {
		for(String test : descriptions) {
			if(test.equalsIgnoreCase(description)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Parses a {@link AccountType} from a string.
	 * @param accountTypeString
	 * 	The status of the company in string representation.
	 * @return
	 * 	The {@link AccountType}, or null if this couldn't be determined.
	 */
	public static AccountType parse(String accountTypeString) {
		
		for(AccountType candidate : AccountType.values()) {
			if(candidate.describes(accountTypeString)) {
				return candidate;
			}
		}
		return null;
	}
}
