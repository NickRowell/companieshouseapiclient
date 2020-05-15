package dm;

import java.io.Serializable;

/**
 * Stores very limited data on a {@link Company} that is only required to uniquely identify it when building
 * a graph. This provides significant memory optimzation. The full data for the {@link Company} is stored to
 * a file on disk for retrieval if needed.
 *
 * @author nrowell
 * @version $Id$
 */
public class CompanyLite implements Serializable, Vertex {
	
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 8063042097204569032L;

	/**
	 * The number of the company, e.g. "00000006"
	 */
	public String company_number;
	
	/**
	 * Link to file containing a serialised {@link Company} containing the complete data.
	 */
	public String file_link;
	
	/**
	 * The default constructor.
	 */
	public CompanyLite() {
		
	}
	
	/**
	 * Constructor used to derive a {@link CompanyLite} from a {@link Company}.
	 * 
	 * @param fullCompany
	 * 	The {@link Company} containing full data; the relevant fields will be picked out to initialise this {@link CompanyLite}.
	 */
	public CompanyLite(Company fullCompany) {
		company_number = fullCompany.company_number;
	}
	
	/**
	 * Constructor taking only the company number. This uniquely identifies the {@link CompanyLite}, and is the
	 * field used in the {@link #equals(Object)} and {@link #hashCode()} methods.
	 * 
	 * @param company_number
	 * 	The company number.
	 */
	public CompanyLite(String company_number) {
		this.company_number = company_number;
	}
	
	@Override
	public String toString() {
		return company_number;
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompanyLite other = (CompanyLite) obj;
        
        // Match on company number
        return company_number.equals(other.company_number);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return company_number.hashCode();
    }
}
