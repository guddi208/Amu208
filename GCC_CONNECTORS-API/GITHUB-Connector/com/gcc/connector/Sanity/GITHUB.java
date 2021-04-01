package com.gcc.connector.Sanity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.io.File;
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
import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import BaseClass.BaseClassMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utility.ConfigFileReader;


public class GITHUB extends ConfigFileReader  {

	private static final Logger logger = Logger.getLogger(GITHUB.class.getName());

	String session_id;

	@Test(priority = 0, enabled = true)
	public void githubConnect() throws IOException {

		try {

			logger.info("******************* GITHUB CONNECTOR SESSION START *******************");

			session_id = BaseClassMethods.getSessionKEY();
			System.out.println("The Session Id is :" + session_id);
			logger.info("------------------ GITHUB CONNECT SESSION ID IS---" + session_id);
			test.info("GITHUB CONNECT SESSION ID GENERATED");

			String connector_key_value = gitconn.getProperty("connector_key");
			logger.info("------------------ GITHUB CONNECTOR KEY IS --" + connector_key_value);

			System.out.println("conn " + connector_key_value);

			//////// Read Json file into JSONObject////////////////////////////////
			JSONParser parser = new JSONParser();
			JSONObject jObj = null;
			try {
				jObj = (JSONObject) parser.parse(new FileReader(new File(System.getProperty("user.dir")
						+ "\\GITHUB-Connector\\com\\gcc\\connector\\Json\\GITHUB.json")));
				if (jObj.containsKey("submission_name")) {
					String sName = (String) jObj.get("submission_name");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					sName = sName + sdf.format(timestamp);
					jObj.put("submission_name", sName);
				}

			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}

			if (jObj != null) {
				BaseClass.ConstantsKeywords.resp = RestAssured.given()
						.headers("Authorization", "Bearer " + session_id, "connector_key", connector_key_value)
						.body(jObj).log().all().when().post("/api/v2/submissions/create").then()
						.body("status", equalTo(200)).and()
						.body("message", equalTo("Submission created and added for preprocess")).log().all().extract()
						.response();
			}

			JsonPath js3 = BaseClass.BaseClassMethods.rawToJson(BaseClass.ConstantsKeywords.resp);
			Long submission_id = js3.get("response_data.submission_id");
			String message = js3.getString("message");

			assertEquals(200, BaseClass.ConstantsKeywords.resp.getStatusCode());
			assertEquals("Submission created and added for preprocess", message);

			System.out.println("-Submission ID is-: " + submission_id);

			int count = js3.get("response_data.jobs.size()");
			System.out.println("count is " + count);

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
						+ "\\GITHUB-Connector\\com\\gcc\\connector\\Config\\jobid.Properties");
				FileOutputStream fileOut1 = null;

				try {
					fileOut1 = new FileOutputStream(fis1);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					properties1.store(fileOut1, "# LISTING ALL GITHUB-CONNECTOR JOB  IDS");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				fileOut1.close();

				///////////////////////////////////// submission_id////////////////////////////////////////////////////////
				Properties prop = new Properties();
				prop.setProperty("Submission_Id", Long.toString(submission_id));
				File fis = new File(System.getProperty("user.dir")
						+ "\\GITHUB-Connector\\com\\gcc\\connector\\Config\\submissionid.Properties");
				FileOutputStream fileOut = null;
				try {
					fileOut = new FileOutputStream(fis);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				prop.store(fileOut, "# LISTING GITHUB-CONNECTOR SUBMISSION IDS");
				fileOut.close();

			}

			

		} catch (Exception ex) {
			
			ex.printStackTrace();

			logger.error(
					"**************************PROBLEM WITH GITHUB CONNECTOR ******************************************");
			logger.error(ex);
		}
	}

	@Test(priority = 1, enabled = true, dependsOnMethods = "githubConnect")
	public void submissiongithubstatus() throws IOException {

		boolean isDelivered = false;
		try {

			String connector_key_value = gitconn.getProperty("connector_key");
			System.out.println("connector-key ------------------ " + connector_key_value);

			String Submission_id = gitsub.getProperty("Submission_Id");
			System.out.println("submission ID------------------ " + Submission_id);

			String body = "{\r\n  \"submission_id\" :\"" + Submission_id + "\"\r\n}";

			int counter = 0;
			int maxretry = 10;
			int respCode = 0;
			while (!isDelivered) {

				System.out.println("=======counter no ===========" + counter);
				Thread.sleep(60000);

				BaseClass.ConstantsKeywords.resp = RestAssured.given()
						.headers("Authorization", "Bearer " + session_id, "connector_key", connector_key_value).log()
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
					System.out.println("----counter---------" + counter);

					if (counter == maxretry) {

						System.out.println("-----------------------------!!!-FAILURE-!!!---------------------------");

						break;
					}
					continue;
				} else {
					isDelivered = true;
					respCode = 200;
					logger.info(
							"----------------GITHUB SUBMISSION IS SUCCESSFULLY COMPLETED---------------------------");
					System.out.println(
							"-----------------------GITHUB CONNECTOR SUBMISSION IS SUCCESSFULLY COMPLETED ---------------------------");
				}

			}

			assertEquals(200, respCode);
			Assert.assertTrue(true);
			
		} catch (Exception ex) {
			Assert.assertTrue(false);
			ex.printStackTrace();
			logger.error(
					"*********************PROBLEM WITH GITHUB CONNECTOR submissiongithubstatus***************************");
		}
	}

///////////////////////////
	@Test(priority = 2, enabled = true, dependsOnMethods = "submissiongithubstatus")
	public void sessionGITHUBterminatesTC() {

		try {

		BaseClass.ConstantsKeywords.resp = given()

					.param("user_session_key", session_id).when().post("/api/v2/session/terminate").then().assertThat()
					.statusCode(200).and().body("message", equalTo("Session terminates successfully")).extract()
					.response();

			JsonPath js = BaseClass.BaseClassMethods.rawToJson(BaseClass.ConstantsKeywords.resp);
			String resString1 = BaseClass.ConstantsKeywords.resp.asString();

			assertEquals(200, BaseClass.ConstantsKeywords.resp.getStatusCode());

			
			Assert.assertTrue(true);
			
			System.out.println(
					"!!!!!!!!!!! GITHUB CONNECTOR SESSION TERMINATE SUCESSFULLY !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			logger.info(
					"*********************GITHUB CONNECTOR SESSION TERMINATE SUCESSFULLY*******************");

		} catch (Exception ex) {
			logger.error(
					"**************************PROBLEM WITH GITHUB CONNECTOR ******************************************");
			Assert.assertTrue(false);
			ex.printStackTrace();

			logger.error(ex);
		}

	}

}
