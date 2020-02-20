package gui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dm.Company.Accounts;
import dm.Links;

/**
 * A {@code JPanel} to display the {@link Accounts.LastAccounts} information.
 *
 *
 * @author nrowell
 * @version $Id$
 */
public class LinksPanel extends JPanel {

	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = -7973080574209723818L;

	/**
	 * A {@link JLabel} to display the {@link Links#charges} field.
	 */
	JLabel chargesField;
	
	/**
	 * A {@link JLabel} to display the {@link Links#filing_history} field.
	 */
	JLabel filing_historyField;
	
	/**
	 * A {@link JLabel} to display the {@link Links#insolvency} field.
	 */
	JLabel insolvencyField;
	
	/**
	 * A {@link JLabel} to display the {@link Links#officers} field.
	 */
	JLabel officersField;
	
	/**
	 * A {@link JLabel} to display the {@link Links#persons_with_significant_control} field.
	 */
	JLabel persons_with_significant_controlField;
	
	/**
	 * A {@link JLabel} to display the {@link Links#persons_with_significant_control_statements} field.
	 */
	JLabel persons_with_significant_control_statementsField;
	
	/**
	 * A {@link JLabel} to display the {@link Links#registers} field.
	 */
	JLabel registersField;
	
	/**
	 * A {@link JLabel} to display the {@link Links#self} field.
	 */
	JLabel selfField;

	/**
	 * The main constructor for the {@link LinksPanel}
	 */
	public LinksPanel() {
		super(new GridLayout(0,2));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
	            .createTitledBorder("Links"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		chargesField = new JLabel("---");
		filing_historyField = new JLabel("---");
		insolvencyField = new JLabel("---");
		officersField = new JLabel("---");
		persons_with_significant_controlField = new JLabel("---");
		persons_with_significant_control_statementsField = new JLabel("---");
		registersField = new JLabel("---");
		selfField = new JLabel("---");
		
		add(new JLabel("Charges: "));
		add(chargesField);
		add(new JLabel("Filing history: "));
		add(filing_historyField);	
		add(new JLabel("Insolvency: "));
		add(insolvencyField);
		add(new JLabel("Officers: "));
		add(officersField);
		add(new JLabel("Persons with significant control: "));
		add(persons_with_significant_controlField);
		add(new JLabel("Persons with significant control statements: "));
		add(persons_with_significant_control_statementsField);
		add(new JLabel("Registers: "));
		add(registersField);
		add(new JLabel("Self: "));
		add(selfField);
	}
	
	/**
	 * Load the {@link Links} information and present in the GUI.
	 * 
	 * @param links
	 * 	The {@link Accounts.LastAccounts} information to present.
	 */
	public void load(Links links) {
		
		if(links==null) {
			chargesField.setText("---");
			filing_historyField.setText("---");
			insolvencyField.setText("---");
			officersField.setText("---");
			persons_with_significant_controlField.setText("---");
			persons_with_significant_control_statementsField.setText("---");
			registersField.setText("---");
			selfField.setText("---");
			return;
		}
		else {
			if(links.charges != null) {
				chargesField.setText(links.charges);
			}
			else {
				chargesField.setText("---");
			}
			
			if(links.filing_history != null) {
				filing_historyField.setText(links.filing_history);
			}
			else {
				filing_historyField.setText("---");
			}
			
			if(links.insolvency != null) {
				insolvencyField.setText(links.insolvency);
			}
			else {
				insolvencyField.setText("---");
			}
			
			if(links.officers != null) {
				officersField.setText(links.officers);
			}
			else {
				officersField.setText("---");
			}
			
			if(links.persons_with_significant_control != null) {
				persons_with_significant_controlField.setText(links.persons_with_significant_control);
			}
			else {
				persons_with_significant_controlField.setText("---");
			}
			
			if(links.persons_with_significant_control_statements != null) {
				persons_with_significant_control_statementsField.setText(links.persons_with_significant_control_statements);
			}
			else {
				persons_with_significant_control_statementsField.setText("---");
			}
			
			if(links.registers != null) {
				registersField.setText(links.registers);
			}
			else {
				registersField.setText("---");
			}
			
			if(links.self != null) {
				selfField.setText(links.self);
			}
			else {
				selfField.setText("---");
			}
		}
	}
}
