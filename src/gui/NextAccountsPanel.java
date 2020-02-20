package gui;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dm.Company.Accounts;
import dm.Company.Accounts.NextAccounts;

/**
 * A {@code JPanel} to display the {@link Accounts.NextAccounts} information.
 *
 *
 * @author nrowell
 * @version $Id$
 */
public class NextAccountsPanel extends JPanel {

	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 4423751197800030557L;

	/**
	 * Format for rendering dates within this class.
	 */
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d YYYY");
	
	/**
	 * A {@link JLabel} to display the {@link Accounts.NextAccounts#due_on} field.
	 */
	JLabel dueOnField;
	
	/**
	 * A {@link JLabel} to display the {@link Accounts.NextAccounts#period_start_on} field.
	 */
	JLabel periodStartOnField;
	
	/**
	 * A {@link JLabel} to display the {@link Accounts.NextAccounts#period_end_on} field.
	 */
	JLabel periodEndOnField;
	
	/**
	 * A {@link JLabel} to display the {@link Accounts.NextAccounts#overdue} field.
	 */
	JLabel overdueField;
	
	/**
	 * The main constructor for the {@link NextAccountsPanel}
	 */
	public NextAccountsPanel() {
		super(new GridLayout(0,2));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
	            .createTitledBorder("Next accounts"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		dueOnField = new JLabel("---");
		periodStartOnField = new JLabel("---");
		periodEndOnField = new JLabel("---");
		overdueField = new JLabel("---");
		
		add(new JLabel("Due on: "));
		add(dueOnField);
		add(new JLabel("Period start on: "));
		add(periodStartOnField);
		add(new JLabel("Period end on: "));
		add(periodEndOnField);
		add(new JLabel("Overdue: "));
		add(overdueField);
	}
	
	/**
	 * Load the {@link Accounts.NextAccounts} information and present in the GUI.
	 * 
	 * @param nextAccounts
	 * 	The {@link Accounts.NextAccounts} information to present.
	 */
	public void load(NextAccounts nextAccounts) {
		
		
		if(nextAccounts==null) {
			dueOnField.setText("---");
			periodStartOnField.setText("---");
			periodEndOnField.setText("---");
			overdueField.setText("---");
			return;
		}
		else {
			if(nextAccounts.due_on != null) {
				dueOnField.setText(dateFormat.format(nextAccounts.due_on));
			}
			else {
				dueOnField.setText("---");
			}
			if(nextAccounts.period_start_on != null) {
				periodStartOnField.setText(dateFormat.format(nextAccounts.period_start_on));
			}
			else {
				periodStartOnField.setText("---");
			}
			if(nextAccounts.period_end_on != null) {
				periodEndOnField.setText(dateFormat.format(nextAccounts.period_end_on));
			}
			else {
				periodEndOnField.setText("---");
			}
			if(nextAccounts.overdue != null) {
				overdueField.setText(nextAccounts.overdue.toString());
			}
			else {
				overdueField.setText("---");
			}

		}
	}
}
