package util;

import java.io.File;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Creates an automatically extensible directory tree for serialization and storage of a large number of objects, 
 * with a specified limit on the number of files in each automatically created subdirectory. You can stuff objects
 * into it and it will write them to a file and return the location.
 * 
 * @author nrowell
 */
public class RecursiveFileStore {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(RecursiveFileStore.class.getName());
	
	/**
	 * Top level of the directory tree.
	 */
	private File root;
	
	/**
	 * The maximum number of files (or directories) to store in each node of the tree.
	 */
	private int n;
	
	/**
	 * Current number of levels in the directory tree
	 */
	private int numLevels;
	
	/**
	 * Index used to track the number of files currently in the store, and the location in the
	 * directory tree to place the next file.
	 */
	private int idx;
	
	/**
	 * Main constructor. The {@link RecursiveFileStore} is capable of detecting an existing store
	 * and continuing from the next available slot, but only works properly if the same value of
	 * maxFilesPerDirectory is used.
	 * 
	 * @param topLevel
	 * 	Top level of the directory tree, i.e first files will be stored here then placed recursively into
	 * subdirectories starting in this location.
	 * @param maxFilesPerDirectory
	 * 	The maximum number of files & directories to store in each node of the tree.
	 */
	public RecursiveFileStore(File topLevel, int maxFilesPerDirectory) {

		root = topLevel;
		n = maxFilesPerDirectory;
		
		// Initialise counter
		idx = 0;
		
		// Initialise number of levels
		numLevels = 0;
		
		// Check for any existing files; set the file index accordingly
		while(getNextLocation().exists()) {
			idx++;
		}
		
		if(idx > 0 ) {
			logger.info("Found " + idx + " existing files in directory tree.");
		}
	}
	
	/**
	 * Compute the integer power.
	 * 
	 * @param base
	 * 	The base.
	 * @param exponent
	 * 	The exponent.
	 * @return
	 * 	base^{exponent}.
	 */
	private static int pow(int base, int exponent) {
		int result = 1;
		for(int a=0; a<exponent; a++) {
			result *= base;
		}
		return result;
	}
	
	/**
	 * Calculates the total capacity of the file store up to the given level.
	 * 
	 * @param level
	 * 	The level of the file store.
	 * @return
	 *  The total number of files that can be stored up to the given level.
	 */
	private int capacity(int level) {
		
		if(level == -1) {
			return 0;
		}
		else {
			// Sum of number of files in this level, and total number of files in preceding levels
			return (pow(n, level+1) + capacity(level-1));
		}
	}
	
	/**
	 * Return the {@link File} corresponding to the current value of {@link #idx}.
	 * 
	 * @return
	 * 	The {@link File} corresponding to the current value of {@link #idx}.
	 */
	private File getNextLocation() {
		
		// Detect if we're incrementing a level
		if(idx == capacity(numLevels)) {
			// This file will be the first written to the new level
			numLevels++;
		}
		
		// Derive the location for the next file
		File location = new File(root, "");
		
		// Index of this file within the current level
		int tmp = idx - capacity(numLevels-1);
		
		for(int i=0; i<numLevels; i++) {
			
			int k = pow(n, numLevels-i);
			
			// Subdirectory index at this level of tree
			int subDirIdx = tmp / k;
			
			// Carry remainder to next level
			tmp = tmp % k;
			
			// Add path element
			location = new File(location, Integer.toString(subDirIdx));
		}
		
		// Detect if we need to create the subdirectory (doesn't apply if we're in the top level, which already exists)
		if(idx%n==0 && numLevels > 0) {
			// This is the first file to be placed in this location - need to create the 
			// parent directory
			location.mkdir();
		}
		
		return new File(location, idx + ".ser");
	}
	
	/**
	 * Writes the given {@link Serializable} object to an appropriate slot in the directory
	 * tree and returns a {@link File} corresponding to the location.
	 * 
	 * @param object
	 * 	The {@link Serializable} object to be stored.
	 * @param fileName
	 * 	The name for the file to be written.
	 * @return
	 * 	A {@link File} corresponding to the location.
	 */
	public File storeObject(Serializable object) {
		
		// Get the path to store this file
		File file = getNextLocation();
		
		// Increment the counter
		idx++;
		
		// Write it
		IOUtil.serialize(file, object);
		
		return file;
	}
	
	/**
	 * Main application entry point, for testing purposes.
	 * @param args
	 * 	The command line arguments (ignored).
	 */
	public static void main(String[] args) {
		
		int maxFilesPerDirectory = 7;
		
		RecursiveFileStore dtb = new RecursiveFileStore(new File("/home/nrowell/Temp/tmp/tree"), maxFilesPerDirectory);
		
		for(int i=0; i<80; i++) {
			System.out.println(i + "\t" + dtb.storeObject(new Integer(i)).getAbsolutePath());
		}
	}
	
}