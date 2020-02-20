package exec;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import dm.Appointment;
import dm.Appointments;
import dm.Company;
import dm.CompanyOfficer;
import dm.CompanyOfficers;
import util.QueryUtil;

/**
 * This application examines cases of duplicate company officers with different appointment links
 * to help find ways to identify and merge these cases.
 * 
 * @author nrowell
 * @version $Id$
 */
public class ExamineDuplicateOfficers {
	
	/**
	 * Main application entry point.
	 * 
	 * @param args
	 * 	The command line args (ignored).
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// An example case of a duplicated officer:
		
		// name = OLIVER, Nicholas Martin
		// appointments = /officers/rZT_8vqfiJtA1yGnonKZUD09CN0/appointments

		// name = OLIVER, Nicholas Martin
		// appointments = /officers/phPP03Op67kehiZ5vEWvXW2UuYQ/appointments
		
		Appointments appointments1 = QueryUtil.getAppointments("/officers/rZT_8vqfiJtA1yGnonKZUD09CN0/appointments");
		Appointments appointments2 = QueryUtil.getAppointments("/officers/phPP03Op67kehiZ5vEWvXW2UuYQ/appointments");
		
		System.out.println("etag 1 = " + appointments1.etag);		
		System.out.println("etag 2 = " + appointments2.etag);
		
		System.out.println("DoB 1 = " + (appointments1.date_of_birth != null ? appointments1.date_of_birth.toString() : "null"));
		System.out.println("DoB 2 = " + (appointments2.date_of_birth != null ? appointments2.date_of_birth.toString() : "null"));
		
		System.out.println("Appointments 1:");
		for(Appointment appointment : appointments1.items) {
			System.out.println("company_name = " + appointment.appointed_to.company_name);
			System.out.println("appointed_on = " + appointment.appointed_on.toString());
			System.out.println("officer_role = " + appointment.officer_role);
		}

		System.out.println("Appointments 2:");
		for(Appointment appointment : appointments2.items) {
			System.out.println("company_name = " + appointment.appointed_to.company_name);
			System.out.println("appointed_on = " + appointment.appointed_on.toString());
			System.out.println("officer_role = " + appointment.officer_role);
		}
		
		
		
	}
	
}