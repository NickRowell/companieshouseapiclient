package exec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.lang.model.SourceVersion;

/**
 * This class provides an application to generate source code for Java enumerated types representing the
 * enumerations used by the Companies House API. It loads and reads the file misc/api-enumerations/constants.yml
 * and generates enums for each type that it finds in there.
 *
 * @author nrowell
 * @version $Id$
 */
public class GenerateEnumeratedTypes {
	
	/**
	 * Path to the api-enumerations constants.yml file.
	 */
	private static File apiEnumerationsFile = new File("misc/api-enumerations/constants.yml");
	
	/**
	 * Path to the directory in which to save the generated enum source code.
	 */
	private static File enumFolder = new File("src/enums/api");
	
	/**
	 * Main application entry point.
	 * 
	 * @param args
	 * 	The command line arguments (ignored)
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		// Open reader on the input api-enumerations file
		BufferedReader in = new BufferedReader(new FileReader(apiEnumerationsFile));
		
		// The writer used to write enum specifications
		BufferedWriter out = null;
		
		String enumName = null;
		String line;
		while((line = in.readLine()) != null) {
			
			// Check for blank lines, comments etc
			if(line.isEmpty()) {
				continue;
			}
			else if(line.startsWith("---")) {
				// Printed at the start of the file
				continue;
			}
			
			// We're either at the start of a new enumeration or partway through an existing one
			
			// Check if the line is indented
			if(line.startsWith("    ")) {
				// Continuing an existing enum
				
				// The enum entry string has the form 'foreign-insolvency' : "Foreign insolvency"
				// where the first token contains the enum name and the second contains the description string.
				String[] tokens = line.split(":",2);
				String enumAwkwardValue = tokens[0].replace("'", "").trim();
				String enumDescription = tokens[1].replace("\"", "").trim();
				
				// Convert the enumAwkwardName to a string that can be used for the enum value, i.e. foreign-insolvency -> foreignInsolvency
				StringBuilder enumValueBuilder = new StringBuilder();
				String[] words = enumAwkwardValue.split("-");
				enumValueBuilder.append(words[0]);
				for(int w=1; w<words.length; w++) {
					String cap = words[w].substring(0, 1).toUpperCase() + words[w].substring(1);
					enumValueBuilder.append(cap);
				}
				String enumValue = enumValueBuilder.toString();
				
				// Check that the enumValue is legal for a Java enum; add a fudge factor if not
				if(!Character.isJavaIdentifierStart(enumValue.charAt(0))) {
					enumValue = "_"+enumValue;
				}
				// Catch keywords, i.e. null, true, false etc
				else if(!SourceVersion.isName(enumValue)) {
					enumValue = "_"+enumValue;
				}
				
				out.write("\n\t@SerializedName(\""+enumAwkwardValue+"\")");
				out.write(String.format("\n\t%s(\"%s\"),", enumValue, enumDescription));
			}
			else {
				// New enum; close off any existing writer and open a new one
				if(out != null) {
					out.write(";\n\n");
					out.write(String.format("\t%s(String description) {\n", enumName));
					out.write("\t\tthis.description = description;\n");
					out.write("\t}\n");
					out.write("\n");
					out.write("\tprivate String description;\n");
					out.write("\tpublic String toString() {\n");
					out.write("\t\treturn description;\n");
					out.write("\t}\n");
					out.write("}\n");
					out.close();
				}
				
				// Extract the enum name; remove underscores, change to upper case and concatenate.
				// line contains something like "insolvency_case_date_type:", which we want to convert
				// to "InsolvencyCaseDateType" for the enum name.
				StringBuilder enumNameBuilder = new StringBuilder();
				for(String word : line.replace(":", "").split("_")) {
					String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
					enumNameBuilder.append(cap);
				}
				enumName = enumNameBuilder.toString();
				
				out = new BufferedWriter(new FileWriter(new File(enumFolder, enumName+".java")));
				// Write enum header
				out.write("package enums.api;\n");
				out.write("\n");
				out.write("import com.google.gson.annotations.SerializedName;\n");
				out.write("\n");
				out.write("public enum "+enumName+" {\n");
			}
		}
		
		// Close off the final enum
		out.write(";\n\n");
		out.write(String.format("\t%s(String description) {\n", enumName));
		out.write("\t\tthis.description = description;\n");
		out.write("\t}\n");
		out.write("\n");
		out.write("\tprivate String description;\n");
		out.write("\tpublic String toString() {\n");
		out.write("\t\treturn description;\n");
		out.write("\t}\n");
		out.write("}\n");
		out.close();
		
		in.close();
	}
}
