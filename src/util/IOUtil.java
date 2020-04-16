package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jgrapht.Graph;
import org.jgrapht.graph.Multigraph;

/**
 * Class providing various IO utilities.
 * 
 * @author nrowell
 *
 */
public final class IOUtil {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(IOUtil.class.getName());
	
	/**
	 * Private default constructor to guard against creating instances of the class.
	 */
	private IOUtil() {}
	
	/**
	 * Serialise the {@link Object} to a {@link File}.
	 * 
	 * @param outputFile
	 * 	The {@link File} to save the {@link Graph} to.
	 * @param object
	 * 	The {@link Object} to be serialized.
	 */
	public static void serialize(File outputFile, Object object) {

		// Write the accumulated {@link Window}s to file
		try (FileOutputStream file = new FileOutputStream(outputFile);
			 ObjectOutputStream output = new ObjectOutputStream(file);) {
			output.writeObject(object);
	    }  
	    catch(IOException ex){
	    	logger.log(Level.SEVERE, "IOException serializing object to file " + outputFile.getName(), ex);
	    }
	}
	
	/**
	 * Deserialise a {@link Multigraph} from a {@link File}.
	 * 
	 * @param outputFile
	 * 	The {@link File} to read the {@link Graph} from.
	 * @param edgeType
	 * 	The {@link Class} of the graph edges to be deserialized.
	 * @return
	 * 	The {@link Multigraph} read from the {@link File}.
	 */
	@SuppressWarnings("unchecked")
	public static <V,E> Multigraph<V,E> deserializeGraph(File outputFile, Class<E> edgeType) {
		
		Object object = null;
		
		try (FileInputStream fileIn = new FileInputStream(outputFile);
			 ObjectInputStream in = new ObjectInputStream(fileIn);) {
			object = in.readObject();
		} catch (IOException e) {
	    	logger.log(Level.SEVERE, "IOException deserializing graph from file " + outputFile.getName(), e);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "ClassNotFoundException deserializing graph from file " + outputFile.getName(), e);
		}
		
		try {
	        return (new Multigraph<V, E>(edgeType)).getClass().cast(object);
	    } catch(ClassCastException e) {
	    	throw new RuntimeException("Can't cast contents of " + outputFile.getName() + 
	    			" to type Multigraph<V," + edgeType.getCanonicalName() + ">");
	    }
	}
}
