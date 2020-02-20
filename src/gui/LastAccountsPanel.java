package gui;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dm.Company.Accounts;
import dm.Company.Accounts.LastAccounts;

/**
 * A {@code JPanel} to display the {@link Accounts.LastAccounts} information.
 *
 *
 * @author nrowell
 * @version $Id$
 */
public class LastAccountsPanel extends JPanel {

	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = -7973080574209723818L;

	/**
	 * Format for rendering dates within this class.
	 */
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d YYYY");
	
	/**
	 * A {@link JLabel} to display the {@link Accounts.LastAccounts#period_end_on} field.
	 */
	JLabel periodEndOnField;

	/**
	 * A {@link JLabel} to display the {@link Accounts.LastAccounts#period_start_on} field.
	 */
	JLabel periodStartOnField;
	
	/**
	 * A {@link JLabel} to display the {@link Accounts.LastAccounts#type} field.
	 */
	JLabel accountTypeField;

	/**
	 * A {@link JLabel} to display the {@link Accounts.LastAccounts#made_up_to} field.
	 */
	JLabel madeUpToField;
	
	/**
	 * The main constructor for the {@link LastAccountsPanel}
	 */
	public LastAccountsPanel() {
		super(new GridLayout(0,2));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
	            .createTitledBorder("Last accounts"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		periodEndOnField = new JLabel("---");
		periodStartOnField = new JLabel("---");
		accountTypeField = new JLabel("---");
		madeUpToField = new JLabel("---");
		
		add(new JLabel("Period start on: "));
		add(periodStartOnField);
		add(new JLabel("Period end on: "));
		add(periodEndOnField);
		add(new JLabel("Account type: "));
		add(accountTypeField);
		JLabel madeUpToLabel = new JLabel("Made up to [deprecated]: ");
		madeUpToLabel.setToolTipText("Use 'Period end on' instead");
		add(madeUpToLabel);
		add(madeUpToField);
	}
	
	/**
	 * Load the {@link Accounts.LastAccounts} information and present in the GUI.
	 * 
	 * @param lastAccounts
	 * 	The {@link Accounts.LastAccounts} information to present.
	 */
	@SuppressWarnings("deprecation")
	public void load(LastAccounts lastAccounts) {
		
		if(lastAccounts==null) {
			periodStartOnField.setText("---");
			periodEndOnField.setText("---");
			accountTypeField.setText("---");
			madeUpToField.setText("---");
			return;
		}
		else {
			if(lastAccounts.period_end_on != null) {
				periodEndOnField.setText(dateFormat.format(lastAccounts.period_end_on));
			}
			else {
				periodEndOnField.setText("---");
			}
			if(lastAccounts.period_start_on != null) {
				periodStartOnField.setText(dateFormat.format(lastAccounts.period_start_on));
			}
			else {
				periodStartOnField.setText("---");
			}
			if(lastAccounts.made_up_to != null) {
				madeUpToField.setText(dateFormat.format(lastAccounts.made_up_to));
			}
			else {
				madeUpToField.setText("---");
			}
			accountTypeField.setText(lastAccounts.type.toString());

		}
	}
}
