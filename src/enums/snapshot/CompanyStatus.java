package enums.snapshot;

import java.util.Arrays;

public enum CompanyStatus {

	active("Active"),
	activeProposalToStrikeOff("Active - Proposal to Strike off"),
	liquidation("Liquidation"),
	administration("In Administration"),
	administrationOrder("ADMINISTRATION ORDER"),
	administrativeReceiver("ADMINISTRATIVE RECEIVER"),
	receiverManagerAdminReceiver("RECEIVER MANAGER / ADMINISTRATIVE RECEIVER"),
	administrationAdminReceiver("In Administration/Administrative Receiver"),
	administrationReceiverManager("In Administration/Receiver Manager"),
	voluntaryArrangement("Voluntary Arrangement"),
	voluntaryArrangementAdminReceiver("VOLUNTARY ARRANGEMENT / ADMINISTRATIVE RECEIVER"),
	voluntaryArrangementReceiverManager("VOLUNTARY ARRANGEMENT / RECEIVER MANAGER"),
	receivership("RECEIVERSHIP"),
	receivershipRmAtLeastOneCharge("Live but Receiver Manager on at least one charge"),
	;

	CompanyStatus(String ... descriptions) {
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
	 * Parses a {@link CompanyStatus} from a string.
	 * @param statusString
	 * 	The status of the company in string representation.
	 * @return
	 * 	The {@link CompanyStatus}, or null if this couldn't be determined.
	 */
	public static CompanyStatus parse(String statusString) {
		
		for(CompanyStatus candidate : CompanyStatus.values()) {
			if(candidate.describes(statusString)) {
				return candidate;
			}
		}
		
		return null;
	}
	
}
