package dm;

import org.jgrapht.graph.DefaultEdge;

import enums.api.OfficerRole;

/**
 * Ths class represents on edge in the company/officer graph. Edges link one {@link Company} with one {@link CompanyOfficer},
 * and have the attribute {@link OfficerRole}. The {@link #equals(Object)} and {@link #hashCode()} methods
 * are overridden in order to make two {@link Edge}s that connect the same {@link Company} and {@link CompanyOfficer} with
 * the same {@link OfficerRole} identical. This is useful for establishing the equality of {@link org.jgrapht.Graph}s.
 * 
 * @author nrowell
 */
public class Edge extends DefaultEdge {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -2415922465484835605L;
	
	/**
	 * The {@link OfficerRole} associated with this {@link Edge}.
	 */
	public final OfficerRole role;
	
	/**
	 * The company number for the {@link Company} vertex.
	 */
	private final String company_number;
	
	/**
	 * The appointments url for the {@link CompanyOfficer} vertex.
	 */
	private final String appointments;
	
	/**
	 * The main constructor.
	 * 
	 * @param role
	 * 	The {@link OfficerRole}.
	 */
	public Edge(OfficerRole role, Company company, CompanyOfficer officer) {
		this.role = role;
		company_number = company.company_number;
		appointments = officer.links.officer.appointments;
	}
	
	@Override
	public String toString() {
		return role + ": " + company_number + " -> " + appointments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appointments == null) ? 0 : appointments.hashCode());
		result = prime * result + ((company_number == null) ? 0 : company_number.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (appointments == null) {
			if (other.appointments != null)
				return false;
		} else if (!appointments.equals(other.appointments))
			return false;
		if (company_number == null) {
			if (other.company_number != null)
				return false;
		} else if (!company_number.equals(other.company_number))
			return false;
		if (role != other.role)
			return false;
		return true;
	}
}
