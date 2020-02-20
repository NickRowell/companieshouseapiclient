package exec;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gui.CompanyPanel;
import gui.CompanyQueryPanel;

/**
 * Small GUI application that queir
 * 
 * TODO: display the remaining fields of the Company
 *
 * @author nrowell
 * @version $Id$
 */
public class CompaniesHouseApiGui extends JFrame {
	
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 8646809778938791860L;

	/**
	 * Default constructor for the {@link CompaniesHouseApiGui}.
	 */
	public CompaniesHouseApiGui() {
		
		super("Companies House API Explorer");
		
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 750);
        
        CompanyPanel companyPanel = new CompanyPanel();
        CompanyQueryPanel companyQueryPanel = new CompanyQueryPanel(companyPanel, this);
        
        add(companyQueryPanel, BorderLayout.NORTH);
        add(companyPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	/**
	 * Main application entry point.
	 * @param args
	 * 	The args - ignored
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CompaniesHouseApiGui();
            }
        });
	}
}