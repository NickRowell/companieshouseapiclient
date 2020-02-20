package gui;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dm.Company;

/**
 * This class provides a {@link JPanel} that provides a view of a single {@link Company}.
 *
 * @author nrowell
 * @version $Id$
 */
public class CompanyPanel extends JPanel {
	
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = -6111238147298580618L;
	
	/**
	 * A {@link LinksPanel} to display the {@link Company.Links} information.
	 */
	LinksPanel linksPanel;
	
	/**
	 * A {@link AccountsPanel} to display the {@link Company.Accounts} information.
	 */
	AccountsPanel accPanel;
	
	/**
	 * A {@link JLabel} to present the {@link Company#company_name} field.
	 */
	JLabel companyNameField;
	/**
	 * A {@link JLabel} to present the {@link Company#date_of_creation} field.
	 */
	JLabel dateOfCreationField;
	/**
	 * A {@link JLabel} to present the {@link Company#date_of_cessation} field.
	 */
	JLabel dateOfCessationField;
	/**
	 * A {@link JLabel} to present the {@link Company#company_status} field.
	 */
	JLabel companyStatusField;
	/**
	 * A {@link JLabel} to present the {@link Company#jurisdiction} field.
	 */
	JLabel jurisdictionField;
	/**
	 * A {@link JLabel} to present the {@link Company#type} field.
	 */
	JLabel typeField;
	
	/**
	 * Main constructor for the {@link CompanyPanel}.
	 */
	public CompanyPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		companyNameField = new JLabel("---");
		dateOfCreationField = new JLabel("---");
		dateOfCessationField = new JLabel("---");
		companyStatusField = new JLabel("---");
		jurisdictionField = new JLabel("---");
		typeField = new JLabel("---");
		accPanel = new AccountsPanel();
		linksPanel = new LinksPanel();
		
		JPanel basicInfoPanel = new JPanel(new GridLayout(0,2));
		basicInfoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		basicInfoPanel.add(new JLabel("Company name:"));
		basicInfoPanel.add(companyNameField);
		basicInfoPanel.add(new JLabel("Date of creation:"));
		basicInfoPanel.add(dateOfCreationField);
		basicInfoPanel.add(new JLabel("Date of cessation:"));
		basicInfoPanel.add(dateOfCessationField);
		basicInfoPanel.add(new JLabel("Company status:"));
		basicInfoPanel.add(companyStatusField);
		basicInfoPanel.add(new JLabel("Jurisdiction:"));
		basicInfoPanel.add(jurisdictionField);
		basicInfoPanel.add(new JLabel("Type:"));
		basicInfoPanel.add(typeField);
		
		add(basicInfoPanel);
		add(accPanel);
		add(linksPanel);
		
	}
	
	public void loadCompany(Company company) {
		
		accPanel.load(company.accounts);
		linksPanel.load(company.links);
		
		if(company.company_name != null) {
			companyNameField.setText(company.company_name);
		}
		else {
			companyNameField.setText("---");
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d YYYY");
		
		if(company.date_of_creation != null) {
			dateOfCreationField.setText(dateFormat.format(company.date_of_creation));
		}
		else {
			dateOfCreationField.setText("---");
		}

		if(company.date_of_cessation != null) {
			dateOfCessationField.setText(dateFormat.format(company.date_of_cessation));
		}
		else {
			dateOfCessationField.setText("---");
		}
		
		companyStatusField.setText(company.company_status.toString());
		jurisdictionField.setText(company.jurisdiction.toString());
		typeField.setText(company.type.toString());
	}
	
}
