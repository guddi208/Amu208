package BaseClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BaseClassMethods extends utility.ConfigFileReader {

	// public Logger logger;
	public static String session_id;
	public static String apisession_id;
	public static String content_id;
	public static String reference_id;
	public static Long submission_id;
	public static String st=null;
	public static String due_date;
	
	public static String message;

	// ===============================================================================================================
	//ROW_TO_JSON
	// ===============================================================================================================
	public static JsonPath rawToJson(Response resp) {
		// By using the resp.asString() method, we can convert the body into the string	// representation.
		String strresp = resp.asString();
		System.out.println("1>-The Response is==> :" + strresp);

		// First get the JsonPath object instance from the Response interface
		JsonPath jsresp = new JsonPath(strresp);
		System.out.println("The Json Response is :" + jsresp);
		return jsresp;
	}

	// ===============================================================================================================
    ///SESSION KEY//
     //gcauto===>>password1!
	// ===============================================================================================================
	public static String getSessionKEY() {

	ConstantsKeywords.resp = given().body(BaseClass.ConstantsKeywords.JSONBODY).when().post("/api/v2/session/start")
				.then().assertThat().statusCode(200).and().body("message", equalTo("Logged in successfully")).log().all().extract()
				.response();

		JsonPath js = BaseClassMethods.rawToJson(ConstantsKeywords.resp);
		session_id = js.get("response_data.user_session_key");
		message = js.get("message");

		
		return session_id;
	}
	
	// ===============================================================================================================
	///SESSION KEY//
	//api_qa===>>password1!
	// ===============================================================================================================
	public static String getSessionKEYAPI() {
		
			ConstantsKeywords.resp = given().body(BaseClass.ConstantsKeywords.JSONBODYAPI).when()
					.post("/api/v2/session/start")
					.then().assertThat().statusCode(200).and()
					.body("message", equalTo("Logged in successfully")).log().all()
					.extract().response();
		
			JsonPath js = BaseClassMethods.rawToJson(ConstantsKeywords.resp);
					apisession_id = js.get("response_data.user_session_key");
			message = js.get("message");

			return apisession_id;
		}
	
	//===============================================================================================================
//	public static String getMessage() {

//	RestAssured.baseURI = pro.getProperty("gccURL");
//
//		ConstantsKeywords.resp = given().body(BaseClass.ConstantsKeywords.JSONBODY).when().post("/api/v2/session/start")
//				.then().assertThat().statusCode(200).and().body("message", equalTo("Logged in successfully")).extract()
//			.response();
//		JsonPath js = BaseClassMethods.rawToJson(ConstantsKeywords.resp);
//		message = js.get("message");
//
//		return message;
//	}
	@Test(dependsOnMethods = "getSessionKEY")
	public static String getSessionMessage() {

	String message=BaseClassMethods.message;
	System.out.println("message is :"+ message);
	return message;
	}
	
//===============================================================================================================

	///FOR DOC FILE UPLOAD
	///CONTENT ID
	public static String getcontentIDTest(String file) {//Connectors list
		

		//RestAssured.baseURI = pro.getProperty("gccURL");
	
		String connector_key_value = pro.getProperty("connector_key");
		
		String metadata ="{\"CS01001\": \"QA_TEST\","+ "\"QATest\" : \"22\","+ "\"SQA001\" : \"PUNE(IN)\"}";
		
				ConstantsKeywords.resp = RestAssured.given()
				.headers("Authorization", "Bearer " + pro1.getProperty("Session_Id"), "connector_key",
						connector_key_value)

				.multiPart("file", new File(System.getProperty("user.dir") + "\\data\\DocData\\" + file))
				.multiPart("file_type", "DOCX").multiPart("name", "test.docx")
				.multiPart("metadata", metadata).log().parameters()
				.when().post("/api/v2/content/file")
				
				.then().body("status", equalTo(200)).and().body("message", equalTo("File is successfully added")).log().all()
				.extract().response();
	
		System.out.println("======RequestSpecification is======" + ConstantsKeywords.resp);

		JsonPath js1 = BaseClassMethods.rawToJson(ConstantsKeywords.resp);

		String name = js1.get("response_data.name");
		System.out.println("Name is: " + name);

		content_id = js1.get("response_data.content_id");
	    message=js1.getString("message");

		return content_id;
	}
	
	@Test(dependsOnMethods = "getcontentID")
	public static String getcontentMessageTest() {
	String message=BaseClassMethods.message;
	System.out.println("message is :"+ message);
	return message;
	}
	// ===============================================================================================================

	///FOR XML FILE UPLOAD
	public static String getcontentID(String file) {//Connectors list
		
	String connector_key_value = pro.getProperty("connector_key");
		
		ConstantsKeywords.resp = RestAssured.given()
				.headers("Authorization", "Bearer " + pro1.getProperty("Session_Id"), "connector_key",
						connector_key_value)

				.multiPart("file", new File(System.getProperty("user.dir") + "\\data\\XmlData\\" + file))
				.multiPart("file_type", "XML").multiPart("name", "test.xml")
				.multiPart("metadata", ConstantsKeywords.metadata).log().parameters()
				.when().post("/api/v2/content/file")
				
				.then().body("status", equalTo(200)).and().body("message", equalTo("File is successfully added")).log().all()
				.extract().response();
	
		System.out.println("======RequestSpecification is======" + ConstantsKeywords.resp);

		JsonPath js1 = BaseClassMethods.rawToJson(ConstantsKeywords.resp);

		String name = js1.get("response_data.name");
		System.out.println("Name is: " + name);

		content_id = js1.get("response_data.content_id");
	    message=js1.getString("message");

		return content_id;
	}
	
	@Test(dependsOnMethods = "getcontentID")
	public static String getcontentMessage() {
	String message=BaseClassMethods.message;
	System.out.println("message is :"+ message);
	return message;
	}
	
	////////////////////////////////////////////////////////////
public static String submissionperlocalecontentID(String file) {//Connectors list
		
		String connector_key_value = pro.getProperty("connector_key");
		
	
		ConstantsKeywords.resp = RestAssured.given()
				.headers("Authorization", "Bearer " + pro1.getProperty("Session_Id"),"connector_key",
						connector_key_value)

				.multiPart("file", new File(System.getProperty("user.dir") + "\\data\\XmlData\\" + file))
				.multiPart("file_type", "XML").multiPart("name", "test.xml")
				.multiPart("metadata", ConstantsKeywords.metadata).log().parameters()
				.when().post("/api/v2/content/file")
				
				.then().body("status", equalTo(200)).and().body("message", equalTo("File is successfully added")).log().all()
				.extract().response();
	
		System.out.println("======RequestSpecification is======" + ConstantsKeywords.resp);

		JsonPath js1 = BaseClassMethods.rawToJson(ConstantsKeywords.resp);

		String name = js1.get("response_data.name");
		System.out.println("Name is: " + name);

		content_id = js1.get("response_data.content_id");
	    message=js1.getString("message");

		return content_id;
	}
	
	@Test(dependsOnMethods = "getcontentID")
	public static String submissionperlocalecontent() {
	String message=BaseClassMethods.message;
	System.out.println("message is :"+ message);
	return message;
	}
	/////////////////////////////////////////////////////////////////////
	///Reference ID
	public static String getReferenceID(String file) {

		RestAssured.baseURI = pro.getProperty("gccURL");

		String connector_key_value = pro.getProperty("connector_key");

		BaseClass.ConstantsKeywords.resp = RestAssured.given()
				.headers("Authorization", "Bearer " + pro1.getProperty("Session_Id"), "connector_key",
						connector_key_value)
				.multiPart("file", new File(System.getProperty("user.dir") + "\\data\\DocData\\" + file)).when()
				.post("/api/v2/content/reference").then().body("status", equalTo(200)).and()
				.body("message", equalTo("Reference file upload successful")).log().all().extract().response();

		System.out.println("======RequestSpecification is=====" + BaseClass.ConstantsKeywords.resp);

		JsonPath js2 = BaseClass.BaseClassMethods.rawToJson(BaseClass.ConstantsKeywords.resp);

		reference_id = js2.get("response_data.reference_id");
		 message=js2.getString("message");

		writeUsingFileWriter("Reference id =" + reference_id);
		readUsingFileWriter("Reference  id =" + reference_id);
		  
		return reference_id;

	}
	@Test(dependsOnMethods = "getReferenceID")
	public static String getReferenceMessage() {
		String message=BaseClassMethods.message;
		System.out.println("message is :"+ message);
		return message;
	}
	
	// ===============================================================================================================
	//Submission ID
	public static Long getSubmission(String sub_Name) {

		RestAssured.baseURI = pro.getProperty("gccURL");
		
		due_date=pro.getProperty("due_date");
		
		String body = "{" + "\"submission_name\":\"" + sub_Name + "\"," + "\"due_date\": \"" + due_date +"\","
				+ "\"instructions\": \"check by QA with instructions\"," + "\"source_locale\": \"en-US\","
				+ "\"target_locale\": [\"de-DE\", \"fr-FR\"]," + "\"content_list\": [\"" + content_id + "\"],"
				+ "\"reference_file_list\" :[ \"" + reference_id + "\"]," + "\"callback_url\" : \"\","
				+ "\"attributes\" : {}," + "\"config\":{\"is_select_only_new_files\": true}" + "}";

		String connector_key_value = (pro.getProperty("connector_key"));
		
		BaseClass.ConstantsKeywords.resp = RestAssured.given()
				.headers("Authorization", "Bearer " + session_id, "connector_key", connector_key_value).body(body)
				.when().post("/api/v2/submissions/submit").then().body("status", equalTo(200)).and()
				.body("message", equalTo("Submission created and added for preprocess")).log().all().extract().response();

		System.out.println("======RequestSpecification is==========" + BaseClass.ConstantsKeywords.resp);

		JsonPath js3 = BaseClass.BaseClassMethods.rawToJson(BaseClass.ConstantsKeywords.resp);

		submission_id = js3.get("response_data.submission_id");
		
		 message=js3.getString("message");

		return submission_id;
	}
	@Test(dependsOnMethods = "getSubmissionID")
	public static String getSubmissionMessage() {
		
		String message=BaseClassMethods.message;
		System.out.println("message is"+ message);
		return message;
	}
	// =========================================================================
	public static String writeUsingFileWriter(String data) {
		File file = new File("c:\\FileWriter.txt");
		FileWriter fr = null;
		try {
			fr = new FileWriter(file);
			fr.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close resources
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	// =========================================================================
	public static String readUsingFileWriter(String data) {
		File file = new File("c:\\FileWriter.txt");

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while ((st = br.readLine()) != null)
				System.out.println("=============");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return st;
	}

//=====================================================================================//*
//	public static void writePropertyFile() {
//		try {
//			Properties properties = new Properties();
//			properties.setProperty("Session_Id", session_id);
//			properties.setProperty("Content_Id", content_id);
//			properties.setProperty("Reference_Id", reference_id);
//			// properties.setProperty("Submission Id", submission_id);
//			File fis = new File(
//					System.getProperty("user.dir") + "\\src\\main\\java\\com\\gcc\\apis\\Config\\ids.properties");
//			// File file = new File("ids.properties");
//			FileOutputStream fileOut = new FileOutputStream(fis);
//			properties.store(fileOut, "# LISTING ALL IDS");
//			fileOut.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	
//	

}
