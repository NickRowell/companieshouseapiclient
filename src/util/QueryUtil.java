package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import dm.Appointments;
import dm.Company;
import dm.CompanyOfficers;

@SuppressWarnings("deprecation")
public class QueryUtil {

	public static final Map<Integer, String> httpUrlConnectionResponseMap = new TreeMap<>();
	
	public static final int HTTP_RATE_THROTTLING = 429;
	
	static {
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_ACCEPTED, "HTTP_ACCEPTED");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_BAD_GATEWAY, "HTTP_BAD_GATEWAY");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_BAD_METHOD, "HTTP_BAD_METHOD");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_BAD_REQUEST, "HTTP_BAD_REQUEST");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_CLIENT_TIMEOUT, "HTTP_CLIENT_TIMEOUT");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_CONFLICT, "HTTP_CONFLICT");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_CREATED, "HTTP_CREATED");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_ENTITY_TOO_LARGE, "HTTP_ENTITY_TOO_LARGE");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_FORBIDDEN, "HTTP_FORBIDDEN");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_GATEWAY_TIMEOUT, "HTTP_GATEWAY_TIMEOUT");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_GONE, "HTTP_GONE");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_INTERNAL_ERROR, "HTTP_INTERNAL_ERROR");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_LENGTH_REQUIRED, "HTTP_LENGTH_REQUIRED");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_MOVED_PERM, "HTTP_MOVED_PERM");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_MOVED_TEMP, "HTTP_MOVED_TEMP");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_MULT_CHOICE, "HTTP_MULT_CHOICE");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_NO_CONTENT, "HTTP_NO_CONTENT");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_NOT_ACCEPTABLE, "HTTP_NOT_ACCEPTABLE");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, "HTTP_NOT_AUTHORITATIVE");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_NOT_FOUND, "HTTP_NOT_FOUND");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_NOT_IMPLEMENTED, "HTTP_NOT_IMPLEMENTED");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_NOT_MODIFIED, "HTTP_NOT_MODIFIED");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_OK, "HTTP_OK");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_PARTIAL, "HTTP_PARTIAL");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_PAYMENT_REQUIRED, "HTTP_PAYMENT_REQUIRED");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_PRECON_FAILED, "HTTP_PRECON_FAILED");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_PROXY_AUTH, "HTTP_PROXY_AUTH");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_REQ_TOO_LONG, "HTTP_REQ_TOO_LONG");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_RESET, "HTTP_RESET");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_SEE_OTHER, "HTTP_SEE_OTHER");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_SERVER_ERROR, "HTTP_SERVER_ERROR");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_UNAUTHORIZED, "HTTP_UNAUTHORIZED");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_UNAVAILABLE, "HTTP_UNAVAILABLE");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_UNSUPPORTED_TYPE, "HTTP_UNSUPPORTED_TYPE");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_USE_PROXY, "HTTP_USE_PROXY");
		httpUrlConnectionResponseMap.put(HttpURLConnection.HTTP_VERSION, "HTTP_VERSION");
		httpUrlConnectionResponseMap.put(HTTP_RATE_THROTTLING, "HTTP_COMPANIES_HOUSE_API_RATE_THROTTLING");
	}
	
	/**
	 * API key for this application
	 */
	private static String apiKey = "unzngwpKvT8FOSSJJkyuNP7SsJo6v8f4w4FciVy2";
	
	/**
	 * URL for the company search, with a placeholder and format specifier for the company number
	 */
	private static String companyUrl = "https://api.companieshouse.gov.uk/company/%s";
	
	/**
	 * Base URL for the companies house searches, with a placeholder for any extensions
	 */
	private static String baseUrl = "https://api.companieshouse.gov.uk%s";

	/**
	 * Instance of {@link Gson} used to parse Java Objects from JSON format. We write our own implementation
	 * below in order to handle empty date strings.
	 */
	private static Gson gson = null;
	
	// Static initialisation
	static {
		// Create our own Gson so that we can handle empty data strings
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        @Override
	        public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
	                throws JsonParseException {
	            try {
	                return df.parse(json.getAsString());
	            } catch (ParseException e) {
	                return null;
	            }
	        }
	    });
	    gson = gsonBuilder.create();
	}
	
	/**
	 * Queries the Companies House database via the API for the {@link Company} with the
	 * given number.
	 * 
	 * @param companyNumber
	 * 	The number of the {@link Company} to look up.
	 * @return
	 * 	The {@link Company} with the given number, or null if this doesn't exist.
	 * @throws IOException
	 * 	If there's a problem connecting with the Companies House database.
	 * @throws InterruptedException 
	 */
	public static Company getCompany(String companyNumber) throws IOException, InterruptedException {
		
		// Create connection
		URL url = new URL(String.format(companyUrl, companyNumber));
		
		// Get the contents of the URL
		String contents = get(url);
		
		if(contents == null) {
			return null;
		}

		// Parse from a JSON to a Java object
		Company company = null;
		try {
			company = gson.fromJson(contents, Company.class);
		}
		catch(com.google.gson.JsonSyntaxException e) {
			System.out.println("Error: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.out.println("Problem parsing this company string:");
			System.out.println(contents);
		}
		
		return company;
	}
	
	/**
	 * Queries the Companies House database via the API for the {@link CompanyOfficers} with the
	 * given link.
	 * 
	 * @param officersLink
	 * 	The link to the officers resource. This is doesn't include the base URL and is what is contained in the
	 * {@link Company.Links#officers} string, e.g. "/company/11432768/officers"
	 * @return
	 * 	A {@link CompanyOfficers} encapsulating the results of the query.
	 * @throws IOException
	 * 	If there's a problem connecting with the Companies House database.
	 * @throws InterruptedException 
	 */
	public static CompanyOfficers getCompanyOfficers(String officersLink) throws IOException, InterruptedException {
		
		// Create connection
		URL url = new URL(String.format(baseUrl, officersLink));
		
		// Get the contents of the URL
		String contents = get(url);
		
		if(contents == null) {
			return null;
		}
		
		// Parse from a JSON to a Java object
		CompanyOfficers officer = null;
		try {
			officer = gson.fromJson(contents, CompanyOfficers.class);
		}
		catch(com.google.gson.JsonSyntaxException e) {
			System.out.println("Error: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.out.println("Problem parsing this string:");
			System.out.println(contents);
		}
		
		return officer;
	}
	
	/**
	 * Queries the Companies House database via the API for the {@link Appointments} with the
	 * given link.
	 * 
	 * @param appointmentsLink
	 * 	The link to the officer appointments resource. This is doesn't include the base URL and is what is contained in the
	 * {@link Company.Links#officers} string, e.g. 
	 * @return
	 * 	A {@link Appointments} encapsulating the results of the query.
	 * @throws IOException
	 * 	If there's a problem connecting with the Companies House database.
	 * @throws InterruptedException 
	 */
	public static Appointments getAppointments(String appointmentsLink) throws IOException, InterruptedException {
		
		// Create URL
		URL url = new URL(String.format(baseUrl, appointmentsLink));
		
		// Get the contents of the URL
		String contents = get(url);
		
		if(contents == null) {
			return null;
		}
		
		// Parse from a JSON to a Java object
		Appointments appointments = null;
		try {
			appointments = gson.fromJson(contents, Appointments.class);
		}
		catch(com.google.gson.JsonSyntaxException e) {
			System.out.println("Error: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.out.println("Problem parsing this string:");
			System.out.println(contents);
		}
		
		return appointments;
	}
	
	/**
	 * Get the contents of the given {@link URL}.
	 * 
	 * @param url
	 * 	The {@link URL}.
	 * @return
	 * 	A {@link String} containing the results of the query.
	 * @throws IOException
	 * 	If there's a problem reading the contents from the {@link HttpURLConnection}.
	 * @throws InterruptedException 
	 */
	private static String get(URL url) throws IOException, InterruptedException {
		
		String encoded = new String(Base64.encodeBase64(StringUtils.getBytesUtf8(apiKey+":")));
		
		// To deal with rate throttling, we loop the connection until we get a response code
		// that is not HTTP_RATE_THROTTLING
		HttpURLConnection connection = null;
		int httpUrlConnectionResponseCode;
		
		do {
			
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty ("Authorization", "Basic " + encoded);
			connection.setRequestProperty("Content-Type", "application/json");
		
			// Send request
			httpUrlConnectionResponseCode = connection.getResponseCode();
			
			if(httpUrlConnectionResponseCode == QueryUtil.HTTP_RATE_THROTTLING) {
				// Exceeded the limit for the number of queries - pause for 5 mins
				System.out.println("Exceeded query limit, pausing for 5 mins...");
				Thread.sleep(5 * 60 * 1000);
				System.out.println("Resuming");
			}
		}
		while(httpUrlConnectionResponseCode == QueryUtil.HTTP_RATE_THROTTLING);

		if(httpUrlConnectionResponseCode == HttpURLConnection.HTTP_OK) {
			// Get response
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		}
		else {
			System.out.println(String.format("%s\t%s\t%d", url, 
					QueryUtil.httpUrlConnectionResponseMap.get(httpUrlConnectionResponseCode), httpUrlConnectionResponseCode));
			return null;
		}
	}
	
}
