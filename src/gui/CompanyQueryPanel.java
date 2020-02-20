package gui;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dm.Company;
import util.QueryUtil;

public class CompanyQueryPanel extends JPanel {

	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = -2108190226294729609L;
	
	/**
	 * The text field for the user to enter the company number.
	 */
	JTextField companyNumberField;
	
	/**
	 * A reference to the {@link CompanyPanel} that's displaying the {@link Company}
	 * information retrieved by this instance.
	 */
	CompanyPanel companyPanel;
	
	public CompanyQueryPanel(CompanyPanel companyPanel, JFrame parent) {
		super(new GridLayout(1,3));

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory
	            .createTitledBorder("Submit query")));
		
		this.companyPanel =  companyPanel;
		companyNumberField = new JTextField("00000000");
		
		JButton loadCompanyButton = new JButton("Load");
		
		loadCompanyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Retrieve the user-entered company number
				String companyNumber = companyNumberField.getText();
				// Try to convert to an integer in the range 0->99999999
//				int companyNumber = -1;
//				try {
//					companyNumber = Integer.parseInt(companyNumberString);
//				}
//				catch(NumberFormatException exception) {
//					JOptionPane.showMessageDialog(parent, String.format("Couldn't parse company number from \"%s\"", companyNumberString),
//	                        "Input Error", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
				
				// Number was successfully parsed; is it in the valid range?
//				if(companyNumber < 0 || companyNumber > 99999999) {
//					JOptionPane.showMessageDialog(parent, String.format("Company number (%d) is outside allowed range (%d -> %d)", 
//							companyNumber, 0, 99999999), "Input Error", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
				
				// Zero-pad the text the user entered to emphasise the fact the company number is in range 0:99999999
				companyNumberField.setText(String.format("%s", companyNumber));
				
				// Got a valid company number - query the Companies House API
				Company company = null;
				try {
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					company = QueryUtil.getCompany(companyNumber);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(parent, String.format("Received following response from query: %s", 
							e1.getMessage()), 
							"Input Error", JOptionPane.ERROR_MESSAGE);
					return;
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					setCursor(Cursor.getDefaultCursor());
				}
				
				// TODO: Inform user of problems if company could not be returned
				if(company != null) {
					companyPanel.loadCompany(company);
				}				
			}});
		
		add(new JLabel("Company number = "));
		add(companyNumberField);
		add(loadCompanyButton);
		
	}
	

}
