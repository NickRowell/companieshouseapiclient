/*
 * Gaia CU5 DU10
 *
 * (c) 2005-2020 Gaia Data Processing and Analysis Consortium
 *
 *
 * CU5 photometric calibration software is free software; you can redistribute
 * it and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * CU5 photometric calibration software is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this CU5 software; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 *-----------------------------------------------------------------------------
 */

package util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * Utilities associated with Gnuplot.
 *
 *
 * @author nrowell
 * @version $Id$
 */
public class GnuplotUtil {

    /**
     * The Logger.
     */
    protected static Logger logger = Logger.getLogger(GnuplotUtil.class.getCanonicalName());

    /**
     * Environment variable to set when executing the Gnuplot process. This is used to ensure that Gnuplot 
     * finds the right libraries if it's executing in a modified environment. For example, applications that
     * use the Matlab Java engine tend to link against different versions of certain Qt libraries that are 
     * undled with Matlab, and which may be incompatible with the version of Gnuplot installed on the system.
     */
    private static String LD_LIBRARY_PATH = ":/lib:/usr/lib:/usr/local/lib:/usr/include/";
    
    /**
     * Writes the given {@link String} to a temporary file, marks it for deletion on exit, and returns
     * a {@link File}.
     * @param string
     * 	The {@link String} to be written to a temporary file.
     * @return
     * 	The {@link File} containing the {@link String}.
     * @throws IOException
     */
    private static File stringToTmpFile(String string) throws IOException {
    	File file = File.createTempFile("gnuplot", null);
    	BufferedWriter out = new BufferedWriter(new FileWriter(file));
    	out.write(string);
    	out.close();
    	file.deleteOnExit();
    	return file;
    }

    /**
     * Writes the script to a temporary file, executes it, deletes the file and returns the
     * exit code.
     * 
     * @param script
     * 		String containing the Gnuplot script to execute.
     * @param logger
     * 		The logger to use.
	 * @return
	 * 		The exit code returned by GNUplot process
     * @throws IOException 
     * @throws InterruptedException 
     */
    public static int executeScript(String script, Logger logger) throws IOException, InterruptedException {
    	return executeScript(stringToTmpFile(script), logger);
    }
    
	/**
	 * Spawns a Gnuplot process, executes the given File as a Gnuplot script and does not
	 * attempt to read any output (e.g. an image) from the process. This is useful for
	 * scripts that don't produce a plot to the output stream, i.e. where the output has
	 * been redirected to file or cases where there is no output.
	 * 
	 * @param script
	 * 		The Gnuplot script file to execute.
     * @param logger
     * 		The logger to use.
	 * @return
	 * 		The exit code returned by GNUplot process
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
    public static int executeScript(File script, Logger logger) throws IOException, InterruptedException
    {
    	String[] command = new String[]{"gnuplot", script.getAbsolutePath()};
    	String[] envp = { "LD_LIBRARY_PATH="+LD_LIBRARY_PATH};
    	
        // Run GNUplot from Java, with script file just written.
        final Process proc = Runtime.getRuntime().exec(command, envp);

        // Capture error stream from Gnuplot process
        StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), logger);
        
        // Start errorgobbler
        errorGobbler.start();
        
        // Wait for GNUplot process to complete.
        int exitCode = proc.waitFor();
        
        // wait for error (messages) thread output to finish
        errorGobbler.join();
        
        return exitCode;
    }

    /**
     * Writes the script to a temporary file, executes it, deletes the file and returns the
     * resulting image.
     * 
     * @param script
     * 		String containing the Gnuplot script to execute.
     * @param logger
     * 		The logger to use.
	 * @return
	 * 		The BufferedImage produced as a result of using Gnuplot to execute the script
     * @throws IOException 
     * @throws InterruptedException 
     */
    public static BufferedImage executeScriptImage(String script, Logger logger) throws IOException, InterruptedException {
    	return executeScriptImage(stringToTmpFile(script), logger);
    }
    
	/**
	 * Spawns a Gnuplot process, executes the given File as a Gnuplot script and attempts
	 * to read the output as a BufferedImage.
	 * @param script
	 * 		The Gnuplot script file to execute
	 * @return
	 * 		The BufferedImage produced as a result of using Gnuplot to execute the script
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
    public static BufferedImage executeScriptImage(File script, Logger logger) throws IOException, InterruptedException
    {
    	String[] command = new String[]{"gnuplot", script.getAbsolutePath()};
    	String[] envp = { "LD_LIBRARY_PATH="+LD_LIBRARY_PATH};
    	
        // Run GNUplot from Java, with script file just written.
        final Process proc = Runtime.getRuntime().exec(command, envp);

        // Capture error stream from Gnuplot process
        StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), logger);
        
        // Capture image from Gnuplot output
        ImageGobbler imageGobbler = new ImageGobbler(proc.getInputStream(), logger);
        
        // Start errorgobbler
        errorGobbler.start();
        imageGobbler.start();
        
        // Wait for GNUplot process to complete.
        proc.waitFor();
        
        // wait for error (messages) thread output to finish
        errorGobbler.join();
        
        // wait for output (image related) thread to finish
        imageGobbler.join();
        
        // Get image from imageGobbler
        return imageGobbler.img;
    }
	
    /**
     * Class used to read a {@link BufferedImage} from an {@link InputStream}.
     *
     * @author nrowell
     * @version $Id$
     */
    private static class ImageGobbler extends Thread {
    	
    	/**
    	 * The logger of the calling class; messages are relayed here.
    	 */
    	Logger logger;
    	
    	/**
    	 * The InputStream to gobble.
    	 */
        InputStream is;
        
        /**
         * The {@link BufferedImage} read from the {@link InputStream}.
         */
        public BufferedImage img;

        /**
         * Main constructor.
         * 
         * @param is
         * 	The {@link InputStream} to read
         * @param logger
         * 	The {@link Logger} to relay messages to
         */
        public ImageGobbler(InputStream is, Logger logger) {
            this.is = is;
            this.logger = logger;
        }

        @Override
        public void run() {
            try {
                img = ImageIO.read(is);
            } 
            catch (IOException ex) {
                logger.severe("Unable to create image: "+ex.getMessage());
            }
        }
    }

    /**
     * Inner class used to handle IO streams from a separate process.
     *
     * @author nrowell
     * @version $Id$
     */
    private static class StreamGobbler extends Thread {
    	
    	/**
    	 * The logger of the calling class; messages are relayed here.
    	 */
    	Logger logger;
    	
    	/**
    	 * The InputStream to gobble.
    	 */
        InputStream is;

        /**
         * Main constructor for the {@link StreamGobbler}.
         * @param is
         * 	The {@link InputStream} to listen to.
         * @param logger
         * 	The {@link Logger} to relay messages to
         */
        public StreamGobbler(InputStream is, Logger logger) {
            this.is = is;
            this.logger = logger;
        }

        @Override
        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);

                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    logger.info("Gnuplot: " + line);
                }
            } 
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
	
}