package BaseClass;



import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ConstantsKeywords extends utility.ConfigFileReader {

	
	public static Response resp;
	public static StringBuffer respstr;
	
	
	public static String user = (pro.getProperty("username"));
	public static String pass = (pro.getProperty("password"));
	public static String agent = (pro.getProperty("user_agent"));
	
	
	public static String apiuser = (apipro.getProperty("username"));
	public static String apipass = (apipro.getProperty("password"));
	public static String apiagent = (apipro.getProperty("user_agent"));
	
	//@Override
	@BeforeClass
	public void startup() {
		RestAssured.baseURI = pro.getProperty("gccURL");
	}
	
	public static final String JSONBODY ="{" + "\"username\": \"" + user + "\"," 
											+ "\"password\": \"" + pass + "\"," 
										
											+ "\"user_agent\": \""+ agent + "\"" + "}";
	
	public static final String JSONBODYAPI ="{" + "\"username\": \"" + apiuser + "\"," 
			+ "\"password\": \"" + apipass + "\"," 
			+ "\"user_agent\": \""+ apiagent + "\"" + "}";
			
	
	
	public static final String metadata ="{\"CS01001\": \"QA_TEST\","+ "\"QATest\" : \"22\","+ "\"SQA001\" : \"PUNE(IN)\"}";
	public static final String attributes ="{\"CS01001\": \"TEXT\","+ "\"QATest\" : \"22\","+"\"RQA001\" : \"QA\","+ "\"SQA001\" : \"PUNE(IN)\"}";
}
