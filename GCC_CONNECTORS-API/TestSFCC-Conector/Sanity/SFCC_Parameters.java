package Sanity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import BaseClass.BaseClassMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utility.ConfigFileReader;

public class SFCC_Parameters extends ConfigFileReader {

	private static final Logger logger = Logger.getLogger(SFCC_Parameters.class.getName());

	String session_id;

	static String txtFile;
	static String jsonFile;
	
	String jsonbody;
	String conn_key;

	@Parameters({"Sfcc_connector_key","Sfcc_json_path" })
	@Test(priority = 0, enabled = true)

	public void sfccConnect(String Sfcc_connector_key,String Sfcc_json_path) throws IOException {

		logger.info("******************* SFCC CONNECTOR SESSION START *******************");

		try {
			session_id = BaseClassMethods.getSessionKEYAPI();

			System.out.println("The Session Id is :" + session_id);
			logger.info("----------------SFCC CONNECT SESSION ID IS -----" + session_id);

			// String Sfcc_Conn_key=sfcc.TestsfccConncetorsetUp(Sfcc_connector_key);
			///////////////////
			listf(Sfcc_json_path);
			////////////////////////////
			
			  String jsonbody = new String(Files.readAllBytes(Paths.get(jsonFile))); String
			 conn_key= new String(Files.readAllBytes(Paths.get(txtFile)));
			  System.out.println("33333333333333333333333333"+conn_key);
			 
			
			System.out.println("33333333333333333333333333"+jsonbody);
			System.out.println("33333333333333333333333333"+conn_key);
			
			// Get all files from a directory.
			BaseClass.ConstantsKeywords.resp = RestAssured.given()
					.headers("Authorization", "Bearer " + session_id, "Sfcc_connector_key", conn_key)
					.body(jsonbody).log().all().when().post("/api/v2/submissions/create").then()
					.body("status", equalTo(200)).and()
					.body("message", equalTo("Submission created and added for preprocess")).log().all().extract()
					.response();
			System.out.println("------------------HHHHHHH111111111111111HHHHHHHHHH");
			JsonPath js3 = BaseClass.BaseClassMethods.rawToJson(BaseClass.ConstantsKeywords.resp);

			Long submission_id = js3.get("response_data.submission_id");

			String message = js3.getString("message");
			System.out.println("--------------------------------------" + message);
			System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");

			assertEquals(200, BaseClass.ConstantsKeywords.resp.getStatusCode());
			assertEquals("Submission created and added for preprocess", message);

			System.out.println("-Submission ID is-: " + submission_id);

			int count = js3.get("response_data.jobs.size()");

			for (int i = 0; i < count; i++) {

				System.out.println("Job ID for " + js3.get("response_data.jobs[" + i + "].connector_locale") + " is "
						+ js3.get("response_data.jobs[" + i + "].job_id"));

/////////////////////////////job id ///////////////////////////////////////
				String job1 = js3.get("response_data.jobs[0].job_id").toString();
				String job2 = js3.get("response_data.jobs[1].job_id").toString();

				Properties properties1 = new Properties();
				properties1.setProperty("fr-FR", job2);
				properties1.setProperty("de-DE", job1);
				File fis1 = new File(System.getProperty("user.dir")
						+ "\\SFCC-Connector\\com\\gcc\\connector\\Config\\jobid.Properties");
				FileOutputStream fileOut1 = null;

				try {
					fileOut1 = new FileOutputStream(fis1);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					properties1.store(fileOut1, "# LISTING ALL SFCC-CONNECTOR JOB  IDS");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				fileOut1.close();

				///////////////////////////////////// submission_id////////////////////////////////////////////////////////
				Properties prop = new Properties();
				prop.setProperty("Submission_Id", Long.toString(submission_id));
				File fis = new File(System.getProperty("user.dir")
						+ "\\SFCC-Connector\\com\\gcc\\connector\\Config\\submissionid.Properties");
				FileOutputStream fileOut = null;
				try {
					fileOut = new FileOutputStream(fis);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					prop.store(fileOut, "# LISTING SFCC-CONNECTOR SUBMISSION IDS");
					fileOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				////////////////////////////////////////// submission_id///////////////////////////////////////////////////////

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(
					"**************************PROBLEM WITH SFCC CONNECTOR ******************************************");
			logger.error(ex);
		}

	}

	@Parameters({ "Sfcc_json_path" })
	@BeforeTest
	public static List<File> listf(String Sfcc_json_path) {
		File directory = new File(Sfcc_json_path);

		List<File> resultList = new ArrayList<File>();

		// get all the files from a directory
		File[] fList = directory.listFiles();

		resultList.addAll(Arrays.asList(fList));
		for (File file : fList) {
			// System.out.println("LIST IS___________" + file);

			String str = file.getName();
			// System.out.println("==>>" + str);

			if (str.contains("SFCC")) {
				if (file.getAbsolutePath().endsWith("txt")) {
					txtFile = file.getAbsolutePath();
					System.out.println("111111111111" + txtFile);
				}
				if (file.getAbsolutePath().endsWith("json")) {
					jsonFile = file.getAbsolutePath();
					System.out.println("222222222" + jsonFile);
				} else if (file.isFile()) {
					// System.out.println("abs" + file.getAbsolutePath());
				} else if (file.isDirectory()) {
					resultList.addAll(listf(file.getAbsolutePath()));
				}
			}
		}
		// System.out.println(fList);
		return resultList;
	}
	@BeforeTest
	public void pub() throws IOException {
		// TODO Auto-generated method stub
		 jsonbody = new String(Files.readAllBytes(Paths.get(jsonFile)));
		 conn_key= new String(Files.readAllBytes(Paths.get(txtFile)));

	}

//	@Parameters({ "Sfcc_connector_key" })
//	public String TestsfccConncetorsetUp(String Sfcc_connector_key) throws IOException {
//		/**
//		 * Load the Property File
//		 */
//
//		FileInputStream testgitfis = new FileInputStream(new File(Sfcc_connector_key));
//		// System.getProperty("user.dir") +
//		// "\\SFCC-Connector\\com\\gcc\\connector\\Config\\config.properties"));
//
//		// Create Properties class object to read properties file
//		testsfccconn = new Properties();
//
//		// Load file so we can use into our script
//		testsfccconn.load(testgitfis);
//		return Sfcc_connector_key;
//
//	}
	@Parameters({ "Sfcc_connector_key"})
	@Test(priority = 1, enabled = true, dependsOnMethods = "sfccConnect")
	public void submissionSFCCstatus (String Sfcc_connector_key) throws IOException {

		boolean isDelivered = false;
		try {

			logger.info("*******************SFCC-CONNECTOR submissionSFCCstatus  START *******************");

			//String connector_key_value = sfccconn.getProperty("connector_key");
			//System.out.println("SFCC connector key" + connector_key_value);

			String Submission_id = sfccsub.getProperty("Submission_Id");
			System.out.println("Submission ID --" + Submission_id);

			String body = "{\r\n  \"submission_id\" :\"" + Submission_id + "\"\r\n}";

			int counter = 0;
			int maxretry = 10;
			int respCode = 0;
			while (!isDelivered) {

				System.out.println("=======counter no ===========" + counter);
				Thread.sleep(60000);

				BaseClass.ConstantsKeywords.resp = RestAssured.given()
						.headers("Authorization", "Bearer " + session_id, "connector_key", Sfcc_connector_key).log()
						.headers().queryParam("submission_id", Submission_id).log().all().when()
						.get("/api/v2/submissions/status").then().body("status", equalTo(200)).log().all().and()
						.body("message", equalTo("Current submission status")).log().all().extract().response();

				System.out.println("======RequestSpecification is===" + BaseClass.ConstantsKeywords.resp);
				JsonPath js1 = BaseClass.BaseClassMethods.rawToJson(BaseClass.ConstantsKeywords.resp);
				String message = js1.getString("message");

				assertEquals(200, BaseClass.ConstantsKeywords.resp.getStatusCode());
				assertEquals("Current submission status", message);

				String status = js1.getString("response_data.state_name");

				if (!status.equals("Delivered")) {
					counter++;
					System.out.println("----status is---------" + status);
					System.out.println("----c---------" + counter);
					if (counter == maxretry) {
						System.out.println("------------------------------FAILURE----------------------------");
						break;
					}
					continue;
				} else {
					isDelivered = true;
					respCode = 200;
					logger.info("----------------SFCC SUBMISSION IS SUCCESSFULLY COMPLETED---------------------------");
					System.out.println(
							"-----------------------SFCC CONNECTOR SUBMISSION IS SUCCESSFULLY COMPLETED ---------------------------");
				}

			}

			assertEquals(200, respCode);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(
					"**************************PROBLEM WITH SFCC CONNECTOR submissionSFCCstatus******************************************");
		}
	}

///////////////////////////
	@Test(priority = 2, enabled = true, dependsOnMethods = "submissionSFCCstatus")
	public void sessionterminatesTC() {
		try {

			BaseClass.ConstantsKeywords.resp = given()

					.param("user_session_key", session_id).when().post("/api/v2/session/terminate").then().assertThat()
					.statusCode(200).and().body("message", equalTo("Session terminates successfully")).extract()
					.response();

			JsonPath js = BaseClass.BaseClassMethods.rawToJson(BaseClass.ConstantsKeywords.resp);
			String resString1 = BaseClass.ConstantsKeywords.resp.asString();

			System.out.println(
					"!!!!!!!!!!! SFCC CONNECTOR SESSION TERMINATE SUCESSFULLY !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			logger.info("----------------SFCC CONNECTOR SUBMISSION IS SUCCESSFULLY COMPLETED----------------");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			logger.error(
					"********************************** ERROR IN SFCC CONNECTOR ************************************");
		}

	}
}
