package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
import dm.CompanyOfficer;
import dm.CompanyOfficers;
import dm.CompanySearch;
import dm.CompanySearch.CompanySearchCompany;
import enums.api.CompanyStatus;

@SuppressWarnings("deprecation")
public class QueryUtil {

	public static final Map<Integer, String> httpUrlConnectionResponseMap = new TreeMap<>();
	
	/**
	 * {@link Set} of HTTP URL connection response codes that indicate we should retry the connection.
	 */
	public static final Set<Integer> responseCodesIndicatingRepeat = new TreeSet<>();
	
	/**
	 * Counts the number of successful connections made to the Companies House API during the
	 * execution of the JVM.
	 */
	private static long connections = 0;
	
	/**
	 * Integer code indicating the Companies House API query limit has been reached.
	 */
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
		
		// Add a shutdown hook to report the number of connections made during the execution.
		Thread printingHook = new Thread(() -> System.out.println("Connections made: " + connections));
		Runtime.getRuntime().addShutdownHook(printingHook);
		
		responseCodesIndicatingRepeat.addAll(
			Arrays.asList(HttpURLConnection.HTTP_BAD_GATEWAY, HttpURLConnection.HTTP_GATEWAY_TIMEOUT));
	}
	
	/**
	 * Maximum number of connection attempts before aborting.
	 */
	private final static int maxAttempts = 5;
	
	/**
	 * API key for this application
	 */
	private final static String apiKey = "unzngwpKvT8FOSSJJkyuNP7SsJo6v8f4w4FciVy2";

	/**
	 * Base URL for the companies house searches, with a placeholder for any extensions
	 */
	private final static String baseUrl = "https://api.companieshouse.gov.uk%s";
	 
	/**
	 * URL for the company search, with a placeholder and format specifier for the company number
	 */
	private final static String companyUrl = String.format(baseUrl, "/company/%s");
	
	/**
	 * URL for the company officers search, with a placeholder and format specifier for the company number
	 */
	private final static String companyOfficersUrl = String.format(baseUrl, "/company/%s/officers");
	
	/**
	 * URL for company search facility.
	 */
	private final static String companySearchUrl = String.format(baseUrl, "/search/companies?q=%s");
	
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
	 * Queries the Companies House database via the API for the {@link Company} resource at the following location:
	 * 
	 * "https://api.companieshouse.gov.uk/company/{company_number}"
	 * 
	 * @param companyNumber
	 * 	The number of the {@link Company} to look up.
	 * @param httpUrlConnectionResponseCode
	 * 	A one-element int array; on exit this contains the HTTP URL response code. This can give diagnostic information
	 * in case the returned object is null.
	 * @return
	 * 	The {@link Company} with the given number, or null if this doesn't exist.
	 * @throws IOException
	 * 	If there's a problem connecting with the Companies House database.
	 * @throws InterruptedException 
	 */
	public static Company getCompany(String companyNumber, int[] httpUrlConnectionResponseCode) throws IOException, InterruptedException {
		
		// Create connection
		URL url = new URL(String.format(companyUrl, companyNumber));
		
		// Get the contents of the URL
		String contents = get(url, httpUrlConnectionResponseCode);
		
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
	 * Performs a search using the Companies House API to look for a company with the given name. Although some
	 * homogenisation of the company names is performed (e.g. to match "COMPANY (THE)" and "THE COMPANY"), the
	 * search may still return more than one company if the name is ambiguous. In these cases the user should
	 * manually check the results to decide which company is the correct match.
	 * 
	 * @param companyName
	 * 	The name of the company to search for.
	 * @param httpUrlConnectionResponseCode
	 * 	A one-element int array; on exit this contains the HTTP URL response code. This can give diagnostic information
	 * in case the returned object is null.
	 * @return
	 * 	A {@link List} of zero, one or more companies that have been matched to the given name, stored as 
	 * {@link CompanySearchCompany} types to encapsulate the fields returned by the Companies House API.
	 * @throws IOException
	 * 	If there's a problem forming the URL or performing the query.
	 * @throws InterruptedException
	 * 	If there's a problem performing the query.
	 */
	public static List<CompanySearchCompany> getActiveCompanyByName(String companyName, int[] httpUrlConnectionResponseCode) throws IOException, InterruptedException {
		
		// Homogenise the whitespace in the company name string
		companyName = ParseUtil.homogeniseWhiteSpace(companyName);

		// Homogenise variants of 'PLC' in the company name string
		companyName = ParseUtil.homogenisePlc(companyName);

		// Convert '&' to 'AND'
		companyName = ParseUtil.homogeniseAnd(companyName);

		// Remove 'THE' from the start of the name, remove '(THE)' from within the name
		companyName = ParseUtil.homogeniseThe(companyName);
		
		// Move the first substring in parentheses to the start
		companyName = ParseUtil.homogeniseParentheses(companyName);
		
		// Create connection
		URL url = new URL(String.format(companySearchUrl, URLEncoder.encode(companyName)));
		
		// Get the contents of the URL
		String contents = get(url, httpUrlConnectionResponseCode);
		
		if(contents == null) {
			System.out.println("Received null from " + companyName);
			return null;
		}
		
		// Parse from a JSON to a Java object
		CompanySearch companySearch = null;
		try {
			companySearch = gson.fromJson(contents, CompanySearch.class);
		}
		catch(com.google.gson.JsonSyntaxException e) {
			System.out.println("Error: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.out.println("Problem parsing this company string:");
			System.out.println(contents);
		}
		
		// Contains companies for which the homogenised names are exact matches. There may be more than
		// one, which may indicate that the name homogenisation has removed an important feature, or that
		// some rare situation has been encountered with more than one active company having the same name.
		List<CompanySearchCompany> matchingCompanies = new LinkedList<>();
		
		// Examine the matches and find the one with the same name
		for(CompanySearchCompany company : companySearch.items) {
			
			// Skip dissolved companies
			if(company.company_status == null || company.company_status.equals(CompanyStatus.dissolved)) {
				continue;
			}

			// Homogenise whitespace in API company name
			String title = ParseUtil.homogeniseWhiteSpace(company.title);

			// Homogenise variants of 'PLC' in the company name string
			title = ParseUtil.homogenisePlc(title);

			// Convert '&' to 'AND'
			title = ParseUtil.homogeniseAnd(title);

			// Remove 'THE' from the start of the name, remove '(THE)' from within the name
			title = ParseUtil.homogeniseThe(title);

			// Move the first substring in parentheses to the start
			title = ParseUtil.homogeniseParentheses(title);
			
			if(companyName.equalsIgnoreCase(title)) {
				matchingCompanies.add(company);
			}
		}
		
		if(!matchingCompanies.isEmpty()) {
			return matchingCompanies;
		}
		else {
			// If we didn't find exact matches then return everything
			return Arrays.asList(companySearch.items);
		}
	}
	
	/**
	 * Queries the Companies House database via the API for the {@link CompanyOfficers} resource at the
	 * following location:
	 * 
	 * "https://api.companieshouse.gov.uk/company/{company_number}/officers"
	 * 
	 * @param companyNumber
	 * 	The number of the {@link Company} to look up.
	 * @param httpUrlConnectionResponseCode
	 * 	A one-element int array; on exit this contains the HTTP URL response code. This can give diagnostic information
	 * in case the returned object is null.
	 * @return
	 * 	A {@link CompanyOfficers} encapsulating the results of the query.
	 * @throws IOException
	 * 	If there's a problem connecting with the Companies House database.
	 * @throws InterruptedException 
	 */
	public static CompanyOfficers getCompanyOfficers(String companyNumber, int[] httpUrlConnectionResponseCode) throws IOException, InterruptedException {
		
		// Create connection
		URL url = new URL(String.format(companyOfficersUrl, companyNumber));
		
		// Get the contents of the URL
		String contents = get(url, httpUrlConnectionResponseCode);
		
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
	 * Queries the Companies House database via the API for the {@link Appointments} resource at the following location:
	 * 
	 * "https://api.companieshouse.gov.uk/officers/{officer_id}/appointments"
	 * 
	 * @param appointmentsLink
	 * 	The link to the officer appointments resource. This is doesn't include the base URL and is what is contained in the
	 * {@link Company.Links#officers} string, e.g. "/officers/SfT6idy3dA8q_pUOe_qfgUqKc_Y/appointments"
	 * @param httpUrlConnectionResponseCode
	 * 	A one-element int array; on exit this contains the HTTP URL response code. This can give diagnostic information
	 * in case the returned object is null.
	 * @return
	 * 	A {@link Appointments} encapsulating the results of the query.
	 * @throws IOException
	 * 	If there's a problem connecting with the Companies House database.
	 * @throws InterruptedException 
	 */
	public static Appointments getAppointments(String appointmentsLink, int[] httpUrlConnectionResponseCode) throws IOException, InterruptedException {
		
		// Create URL
		URL url = new URL(String.format(baseUrl, appointmentsLink));
		
		// Get the contents of the URL
		String contents = get(url, httpUrlConnectionResponseCode);
		
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
	 * @param httpUrlConnectionResponseCode
	 * 	A one-element int array; on exit this contains the HTTP URL response code. This can give diagnostic information
	 * in case the returned object is null.
	 * @return
	 * 	A {@link String} containing the results of the query.
	 * @throws IOException
	 * 	If there's a problem reading the contents from the {@link HttpURLConnection}.
	 * @throws InterruptedException 
	 */
	private static String get(URL url, int[] httpUrlConnectionResponseCode) throws IOException, InterruptedException {
		
		String encoded = new String(Base64.encodeBase64(StringUtils.getBytesUtf8(apiKey+":")));
		
		int attempts = 0;
		
		HttpURLConnection connection = null;
		boolean repeat;
		
		do {
			
			repeat = false;
			
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty ("Authorization", "Basic " + encoded);
			connection.setRequestProperty("Content-Type", "application/json");
		
			// Send request; watch out for brief network outages
			try {
				httpUrlConnectionResponseCode[0] = connection.getResponseCode();
			}
			catch(UnknownHostException e) {
				System.out.println("Caught UnknownHostException, pausing for 1 min then retrying...");
				Thread.sleep(1 * 60 * 1000);
				System.out.println("Resuming");
				repeat = true;
			}
			catch(ConnectException e) {
				// End up here on "Connection timed out (Connection timed out)"
				System.out.println("Caught ConnectException, pausing for 5 min then retrying...");
				Thread.sleep(5 * 60 * 1000);
				System.out.println("Resuming");
				repeat = true;
			}
			
			if(httpUrlConnectionResponseCode[0] == QueryUtil.HTTP_RATE_THROTTLING) {
				// Exceeded the limit for the number of queries - pause for 5 mins then resume
				System.out.println("Exceeded query limit, pausing for 1 min...");
				Thread.sleep(1 * 60 * 1000);
				System.out.println("Resuming");
				repeat = true;
			}
			else if(responseCodesIndicatingRepeat.contains(httpUrlConnectionResponseCode[0])) {
				System.out.println("Received response code " + QueryUtil.httpUrlConnectionResponseMap.get(httpUrlConnectionResponseCode[0])
				+ " from URL " + url + "; retrying in 5 seconds...");
				Thread.sleep(5 * 1000);
				System.out.println("Resuming");
				repeat = true;
			}
			
			attempts++;
		}
		while(repeat && attempts < maxAttempts);

		if(httpUrlConnectionResponseCode[0] == HttpURLConnection.HTTP_OK) {
			
			connections++;
			
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
					QueryUtil.httpUrlConnectionResponseMap.get(httpUrlConnectionResponseCode[0]), httpUrlConnectionResponseCode[0]));
			return null;
		}
	}
	
	/**
	 * Main method to allow testing of the methods in this class.
	 * 
	 * @param args
	 * 	The command line args (ignored).
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
		String companyNumber = "02396957";
		
		int[] httpUrlResponseCode = new int[1];
		
		CompanyOfficers officers = QueryUtil.getCompanyOfficers(companyNumber, httpUrlResponseCode);
		
//		System.out.println(String.format("Response code: %s\t%d", QueryUtil.httpUrlConnectionResponseMap.get(httpUrlResponseCode[0]), httpUrlResponseCode[0]));
		
		System.out.println("Company number = " + companyNumber);
		System.out.println("Officers = " + officers.items.length);
		
		for(CompanyOfficer officer : officers.items) {
			
			// Get officer's DoB from the appointments link
			Appointments appts = getAppointments(officer.links.officer.appointments, httpUrlResponseCode);
			
			System.out.println(officer.name + "\t" + officer.date_of_birth + "\t" + appts.date_of_birth);
			
			System.out.println(officer.name + "\t" + officer.address.postal_code + "\t" + appts.items[0].address.postal_code);
			
			
		}
	}
}
