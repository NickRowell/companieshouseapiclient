package gui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dm.Company.Accounts;
import util.GuiUtil;


/**
 * A {@code JPanel} to display the {@link Accounts} information.
 *
 *
 * @author nrowell
 * @version $Id$
 */
public class AccountsPanel extends JPanel {

	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 4832672306471046934L;
	
	/**
	 * A {@link JLabel} to display the {@link Accounts.AccountingReferenceDate} field.
	 */
	JLabel accountingReferenceDateField;
	
	/**
	 * A {@link JPanel} to display the {@link Accounts.LastAccounts} field.
	 */
	LastAccountsPanel lastAccountsPanel;

	/**
	 * A {@link JPanel} to display the {@link Accounts.NextAccounts} field.
	 */
	NextAccountsPanel nextAccountsPanel;
	
	/**
	 * The main constructor for the {@link AccountsPanel}
	 */
	public AccountsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
	            .createTitledBorder("Accounts"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		accountingReferenceDateField = new JLabel("---");
		lastAccountsPanel = new LastAccountsPanel();
		nextAccountsPanel = new NextAccountsPanel();
		
		JPanel subPanel = new JPanel(new GridLayout(0, 2));
		subPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		subPanel.add(new JLabel("Accounting Reference Date: "));
		subPanel.add(accountingReferenceDateField);
		
		add(subPanel);
		add(lastAccountsPanel);
		add(nextAccountsPanel);
		
	}
	
	/**
	 * Load the {@link Accounts} information and present in the GUI.
	 * 
	 * TODO: display all fields
	 * 
	 * @param accounts
	 * 	The {@link Accounts} information to present.
	 */
	public void load(Accounts accounts) {
		if(accounts==null) {
			// Nullify all fields
			accountingReferenceDateField.setText("");
			lastAccountsPanel.load(null);
			nextAccountsPanel.load(null);
			return;
		}
		lastAccountsPanel.load(accounts.last_accounts);
		nextAccountsPanel.load(accounts.next_accounts);
		
		if(accounts.accounting_reference_date==null) {
			accountingReferenceDateField.setText("");
		}
		else {
			Integer month = accounts.accounting_reference_date.month;
			String monthString = "INVALID MONTH NUMBER ("+month+")";
			if(month != null) {
				int m = month.intValue();
				if(m > 0 && m < 13) {
					monthString = GuiUtil.months[m-1];
				}
			}
			Integer day = accounts.accounting_reference_date.day;
			String dayString = "INVALID DAY NUMBER ("+day+")";
			if(day != null) {
				int d = day.intValue();
				if(d > 0 && d < 32) {
					dayString = String.format("%s%s", d, GuiUtil.ordinalIndicator[d-1]);
				}
			}
			accountingReferenceDateField.setText(String.format("%s %s", monthString, dayString));
		}
	}
}
