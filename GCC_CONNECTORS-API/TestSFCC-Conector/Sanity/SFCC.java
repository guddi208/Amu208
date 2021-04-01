package Sanity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import BaseClass.BaseClassMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utility.ConfigFileReader;

public class SFCC extends ConfigFileReader {

	private static final Logger logger = Logger.getLogger(SFCC.class.getName());

	String session_id;

	@Test(priority = 0, enabled = true)
	
	public void sfccConnect() throws IOException {
		
		
		logger.info("******************* SFCC CONNECTOR SESSION START *******************");
		try {
			session_id = BaseClassMethods.getSessionKEYAPI();

			System.out.println("The Session Id is :" + session_id);
			logger.info("----------------SFCC CONNECT SESSION ID IS -----"+session_id);
		
 
			String connector_key_value = testsfccconn.getProperty("connector_key");
			System.out.println("conn" + connector_key_value);
			logger.info("----------------SFCC CONNECTOR KEY IS----- "+connector_key_value);
			
			////////Read Json file into JSONObject
			File testfile = new File("C:\\Connector\\SFCC\\SFCCJson.json.txt");
			BaseClass.ConstantsKeywords.resp = RestAssured.given()
					.headers("Authorization", "Bearer " + session_id, "connector_key", connector_key_value)
					.body(testfile).log().all()
					.when().post("/api/v2/submissions/create")
					.then()
					.body("status", equalTo(200)).and()
					.body("message", equalTo("Submission created and added for preprocess")).log().all().extract().response();
			
			
			
			
			JsonPath js3 = BaseClass.BaseClassMethods.rawToJson(BaseClass.ConstantsKeywords.resp);

			Long submission_id = js3.get("response_data.submission_id");

			String message = js3.getString("message");
			System.out.println("--------------------------------------" + message);

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

	@Test(priority = 1, enabled = true, dependsOnMethods = "sfccConnect")
	public void submissionSFCCstatus() throws IOException {

		boolean isDelivered = false;
		try {

			logger.info("*******************SFCC-CONNECTOR submissionSFCCstatus  START *******************");

			String connector_key_value = sfccconn.getProperty("connector_key");
			System.out.println("SFCC connector key" + connector_key_value);

			String Submission_id = sfccsub.getProperty("Submission_Id");
			System.out.println("Submission ID --" + Submission_id);

			String body = "{\r\n  \"submission_id\" :\"" + Submission_id + "\"\r\n}";
			
			/*int counter = 0;
			int maxretry = 10;

			while (!isDelivered) {

				System.out.println("=======counter no ===========" + counter);
				Thread.sleep(60000);
				
				BaseClass.ConstantsKeywords.resp = RestAssured.given()
						.headers("Authorization", "Bearer " + session_id, "connector_key", connector_key_value).log()
						.headers().queryParam("submission_id", Submission_id).log().all().when()
						.get("/api/v2/submissions/tasks").then()
					//	.body("status", equalTo(200)).log().all().and()
						.body("message", equalTo("Submission Task List")).log().all().extract().response();

				System.out.println("======RequestSpecification is===" + BaseClass.ConstantsKeywords.resp);
				JsonPath js1 = BaseClass.BaseClassMethods.rawToJson(BaseClass.ConstantsKeywords.resp);

				List<String> status = js1.getList("response_data.tasks_list.status");
				for (String st : status) {
					System.out.println("status is------------" + st);

					logger.info("************************ Status is*********************" + st);

					if (!st.equals("Delivered")) {
						break;
					}
					isDelivered = true;
				}

				counter++;
				System.out.println("----counter no---------" + counter);

				if (counter == maxretry) {
					System.out.println("++++++++++++++++++++++++FAILURE++++++++++++++++++++++++++++++++");
					break;
				}

			}
			logger.info("****SFCC CONNECTOR SUBMISSION IS SUCCESSFULLY COMPLETED*****");
			
			System.out.println(
					"++++++++++++++++ SFCC CONNECTOR SUBMISSION IS SUCCESSFULLY COMPLETED +++++++++++++++++++++++++++");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(
					"**************************PROBLEM WITH SFCC CONNECTOR submissionSFCCTask******************************************");
		}*/
			
			int counter = 0;
			int maxretry = 10;
			int respCode = 0;
			while (!isDelivered) {

				System.out.println("=======counter no ===========" + counter);
				Thread.sleep(60000);

				BaseClass.ConstantsKeywords.resp = RestAssured.given()
						.headers("Authorization", "Bearer " + session_id, "connector_key", connector_key_value).log()
						.headers().queryParam("submission_id", Submission_id).log().all().when()
						.get("/api/v2/submissions/status").then()
						.body("status", equalTo(200)).log().all().and()
						.body("message", equalTo("Current submission status")).log().all()
						.extract().response();

				System.out.println("======RequestSpecification is===" + BaseClass.ConstantsKeywords.resp);
				JsonPath js1 = BaseClass.BaseClassMethods.rawToJson(BaseClass.ConstantsKeywords.resp);
				String message = js1.getString("message");
				
				 assertEquals(200, BaseClass.ConstantsKeywords.resp.getStatusCode());
			      assertEquals("Current submission status",message);
				 
			
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
					  System.out.println("-----------------------SFCC CONNECTOR SUBMISSION IS SUCCESSFULLY COMPLETED ---------------------------");
				  }
				 	

			}
			
			assertEquals(200, respCode);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error("**************************PROBLEM WITH SFCC CONNECTOR submissionSFCCstatus******************************************");
		}
	}

///////////////////////////
	@Test(priority = 2, enabled = true, dependsOnMethods = "submissionSFCCstatus")
	public void sessionterminatesTC() {
		try
		{

		BaseClass.ConstantsKeywords.resp = given()

				.param("user_session_key", session_id).when().post("/api/v2/session/terminate").then().assertThat()
				.statusCode(200).and().body("message", equalTo("Session terminates successfully")).extract().response();

		JsonPath js = BaseClass.BaseClassMethods.rawToJson(BaseClass.ConstantsKeywords.resp);
		String resString1 = BaseClass.ConstantsKeywords.resp.asString();

		System.out.println(
				"!!!!!!!!!!! SFCC CONNECTOR SESSION TERMINATE SUCESSFULLY !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.info(
				"----------------SFCC CONNECTOR SUBMISSION IS SUCCESSFULLY COMPLETED----------------");
	} catch (Exception ex) {
		ex.printStackTrace();
		logger.error(ex);
		logger.error(
				"********************************** ERROR IN SFCC CONNECTOR ************************************");
	}

	}
}
