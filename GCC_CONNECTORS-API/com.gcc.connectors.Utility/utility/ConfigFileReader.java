package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import BaseClass.BaseClassMethods;
import io.restassured.RestAssured;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;




public class ConfigFileReader {

	public static Properties pro;
	public static Properties apipro;

	public static Properties pro1;
	public static Properties pro2;
	public static Properties pro3;
	public static Properties pro4;
	public static Properties pro5;

	public static Properties gitsid;
	public static Properties gitsub;
	public static Properties gitjob;
	public static Properties gitconn;

	public static Properties sfccsid;
	public static Properties sfccconn;
	public static Properties sfccsub;
	public static Properties sfccjob;

	public static Properties ccosid;
	public static Properties ccoconn;
	public static Properties ccosub;
	public static Properties ccojob;

	public static Properties csmcsid;
	public static Properties csmcconn;
	public static Properties csmcsub;
	public static Properties csmcjob;

	public static Properties akeneosid;
	public static Properties akeneoconn;
	public static Properties akeneosub;
	public static Properties akeneojob;

	public static Properties zendesksid;
	public static Properties zendeskconn;
	public static Properties zendesksub;
	public static Properties zendeskjob;

	public static Properties eloquasid;
	public static Properties eloquaconn;
	public static Properties eloquasub;
	public static Properties eloquajob;

	public static Properties gggsid;
	public static Properties gggsub;
	public static Properties gggjob;
	public static Properties gggconn;

	
	public static Properties testsfccconn;
	
	
	//
	public static ExtentReports extent;// TODOextent 1 reporter class
	public static ExtentTest test;// TODO extent 2 reporter class
	public static ExtentHtmlReporter htmlreport;

	private static final Logger logger = Logger.getLogger(ConfigFileReader.class.getName());

		
	@BeforeClass
	public void setupURL() {
		RestAssured.baseURI = pro.getProperty("gccURL");
	}

	@BeforeTest
	public void loadlog4J() {
		String log4jConfPath = System.getProperty("user.dir") + "\\resources\\log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
	}

	@BeforeClass
	/// To read the property file
	public void setUp() throws IOException {
		/**
		 * Load the Property File
		 */
		System.out.println("+++++++++++++++++" + System.getProperty("user.dir"));
		FileInputStream fis = new FileInputStream(new File(
				System.getProperty("user.dir") + "\\src\\test\\java\\com\\gcc\\connector\\Config\\config.Properties"));

		// Create Properties class object to read properties file
		pro = new Properties();

		// Load file so we can use into our script
		pro.load(fis);

	}

	@BeforeClass
	/// To read the property file==================
	public void apisetUp() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream fis = new FileInputStream(new File(
				System.getProperty("user.dir") + "\\SFCC-Connector\\com\\gcc\\connector\\Config\\config.Properties"));

		// Create Properties class object to read properties file
		apipro = new Properties();

		// Load file so we can use into our script
		apipro.load(fis);

	}

//
	@BeforeClass
	// SETUP GITHUB CONNECTOR=====================================
	public void gitConncetorsetUp() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream gitfis = new FileInputStream(new File(
				System.getProperty("user.dir") + "\\GITHUB-Connector\\com\\gcc\\connector\\Config\\config.Properties"));

		// Create Properties class object to read properties file
		gitconn = new Properties();

		// Load file so we can use into our script
		gitconn.load(gitfis);

	}

	@BeforeTest
	// SETUP SFCC CONNECTOR============================================
	public void sfccConncetorsetUp() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream gitfis = new FileInputStream(new File(
				System.getProperty("user.dir") + "\\SFCC-Connector\\com\\gcc\\connector\\Config\\config.properties"));

		// Create Properties class object to read properties file
		sfccconn = new Properties();

		// Load file so we can use into our script
		sfccconn.load(gitfis);

	}
	//@BeforeTest
	// SETUP SFCC CONNECTOR============================================
	public void TestsfccConncetorsetUp1() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream testgitfis = new FileInputStream(new File("C:\\Connector\\SFCC\\SFCC-Connector-Key.txt"));
				//System.getProperty("user.dir") + "\\SFCC-Connector\\com\\gcc\\connector\\Config\\config.properties"));

		// Create Properties class object to read properties file
		testsfccconn = new Properties();

		// Load file so we can use into our script
		testsfccconn.load(testgitfis);

	}

	@BeforeTest
	// SETUP CCO CONNECTOR============================================
	public void ccoConncetorsetUp() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream ccofis = new FileInputStream(new File(
				System.getProperty("user.dir") + "\\CCO-Connector\\com\\gcc\\connector\\Config\\config.properties"));

		// Create Properties class object to read properties file
		ccoconn = new Properties();

		// Load file so we can use into our script
		ccoconn.load(ccofis);

	}

	@BeforeClass
	// SETUP CSMC CONNECTOR=======================================
	public void csmcConncetorsetUp() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream csmcfis = new FileInputStream(new File(
				System.getProperty("user.dir") + "\\CSMC-Connector\\com\\gcc\\connector\\Config\\config.properties"));

		// Create Properties class object to read properties file
		csmcconn = new Properties();

		// Load file so we can use into our script
		csmcconn.load(csmcfis);

	}

	@BeforeTest
	// SETUP AKENEO CONNECTOR=============================================
	public void akeneoConncetorsetUp() throws IOException {
		/**
		 * Load the Property File
		 */
		// System.out.println("==========1");
		FileInputStream akeneofis = new FileInputStream(new File(
				System.getProperty("user.dir") + "\\AKENEO-Connector\\com\\gcc\\connector\\Config\\config.properties"));

		// System.out.println("==========2");
		// Create Properties class object to read properties file
		akeneoconn = new Properties();
		// System.out.println("==========3");
		// Load file so we can use into our script
		akeneoconn.load(akeneofis);
		// System.out.println("==========4");

	}

	@BeforeTest
	// SETUP ZENDESK CONNECTOR=============================================
	public void zendeskConncetorsetUp() throws IOException {
		/**
		 * Load the Property File
		 */
		// System.out.println("==========1");
		FileInputStream zendeskfis = new FileInputStream(new File(System.getProperty("user.dir")
				+ "\\ZENDESK-Connector\\com\\gcc\\connector\\Config\\config.properties"));

		// System.out.println("==========2");
		// Create Properties class object to read properties file
		zendeskconn = new Properties();
		// System.out.println("==========3");
		// Load file so we can use into our script
		zendeskconn.load(zendeskfis);
		// System.out.println("==========4");

	}

	@BeforeTest
	// SETUP ELOQUA CONNECTOR=============================================
	public void eloquaConncetorsetUp() throws IOException {
		/**
		 * Load the Property File
		 */
		// System.out.println("==========1");
		FileInputStream eloquafis = new FileInputStream(new File(
				System.getProperty("user.dir") + "\\ELOQUA-Connector\\com\\gcc\\connector\\Config\\config.properties"));

		// System.out.println("==========2");
		// Create Properties class object to read properties file
		eloquaconn = new Properties();
		// System.out.println("==========3");
		// Load file so we can use into our script
		eloquaconn.load(eloquafis);
		// System.out.println("==========4");

	}


	@BeforeClass
	public void startup() {
		RestAssured.baseURI = pro.getProperty("gccURL");
	}

//	@BeforeMethod
//	// READ SESSION ID PROPERTY================================================
//	public static void readSessionidprop() throws IOException {
//		/**
//		 * Load the Property File
//		 */
//
//		FileInputStream fis1 = new FileInputStream(new File(
//				System.getProperty("user.dir") + "\\src\\main\\java\\com\\gcc\\connector\\Config\\sessionid.properties"));
//
//		// Create Properties class object to read properties file
//		pro1 = new Properties();
//
//		// Load file so we can use into our script
//		pro1.load(fis1);
//
//	}

	// WRITE SESSION ID PROPERTY FILE============================================
	public static void writeSessionPropertyFile() {
		try {
			Properties properties = new Properties();
			properties.setProperty("Session_Id", BaseClassMethods.session_id);
			File fis = new File(System.getProperty("user.dir")
					+ "\\src\\test\\java\\com\\gcc\\connector\\Config\\sessionid.properties");

			FileOutputStream fileOut = new FileOutputStream(fis);
			properties.store(fileOut, "# LISTING SESION IDS");
			fileOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	// WRITE GITHUB SESSION PROPERTY FILE
//	// ===============================================
	public static void writeGitSessionPropertyFile() {
		try {
			Properties properties = new Properties();
			properties.setProperty("Session_Id", BaseClassMethods.session_id);
			File fis = new File(System.getProperty("user.dir")
					+ "\\GITHUB-Connector\\com\\gcc\\connector\\Config\\sessionid.properties");

			FileOutputStream fileOut = new FileOutputStream(fis);
			properties.store(fileOut, "# LISTING GITHUB SESSION IDS");
			fileOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	/// READ GITHUB SESSIONID PROPERTY FILE
//	/// ==============================================
//	@BeforeTest
//	public static void readGITSessionidprop() throws IOException {
//		/**
//		 * Load the Property File
//		 */
//
//		FileInputStream fis1 = new FileInputStream(new File(
//				System.getProperty("user.dir") + "\\Github_Connector\\com\\gcc_conn\\Github\\Config\\sessionid.properties"));
//
//		// Create Properties class object to read properties file
//		gitsid = new Properties();
//
//		// Load file so we can use into our script
//		gitsid.load(fis1);
//
//	}

	// WRITE SFCC SESSION ID PROPERTY FILE
	// ==================================================
	public static void writeSfccSessionPropertyFile() {
		try {
			Properties properties = new Properties();
			properties.setProperty("Session_Id", BaseClassMethods.apisession_id);
			File fis = new File(System.getProperty("user.dir")
					+ "\\SFCC-Connector\\com\\gcc\\connector\\Config\\sessionid.properties");

			FileOutputStream fileOut = new FileOutputStream(fis);
			properties.store(fileOut, "# LISTING SFCC-CONNECTOR SESSION IDS");
			fileOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// WRITE CSMC SESSION PROPERTY FILE
	// ==================================================
	public static void writecsmcSessionPropertyFile() {
		try {
			Properties properties = new Properties();
			properties.setProperty("Session_Id", BaseClassMethods.apisession_id);
			File fis = new File(System.getProperty("user.dir")
					+ "\\CSMC-Connector\\com\\gcc\\connector\\Config\\sessionid.properties");

			FileOutputStream fileOut = new FileOutputStream(fis);
			properties.store(fileOut, "# LISTING CSMC-CONNECTOR SESSION IDS");
			fileOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// READ SFCC SESSION PROPERTY FILE
	// ==================================================
//	@BeforeMethod
//	public static void readSFCCSessionidprop() throws IOException {
//
//		/**
//		 * Load the Property File
//		 */
//
//		FileInputStream fis1 = new FileInputStream(new File(
//				System.getProperty("user.dir") + "\\SFCC-Connector\\com\\gcc\\connector\\Config\\sessionid.properties"));
//
//		// Create Properties class object to read properties file
//		sfccsid = new Properties();
//
//		// Load file so we can use into our script
//		sfccsid.load(fis1);
//
//	}

	// WRITE CCO SESSION PROPERTY FILE
	// ==================================================
	public static void writeCCOSessionPropertyFile() {
		try {
			Properties properties = new Properties();
			properties.setProperty("Session_Id", BaseClassMethods.apisession_id);
			File fis = new File(System.getProperty("user.dir")
					+ "\\CCO-Connector\\com\\gcc\\connector\\Config\\sessionid.properties");

			FileOutputStream fileOut = new FileOutputStream(fis);
			properties.store(fileOut, "# LISTING CCO-CONNECTOR SESSION IDS");
			fileOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	// READ CCO SESSION PROPERTY FILE
//	// ==================================================
	@BeforeMethod
	public static void readCCOSessionidprop() throws IOException {

		/**
		 * Load the Property File
		 */

		FileInputStream fis1 = new FileInputStream(new File(
				System.getProperty("user.dir") + "\\CCO-Connector\\com\\gcc\\connector\\Config\\sessionid.properties"));

		// Create Properties class object to read properties file
		ccosid = new Properties();

		// Load file so we can use into our script
		ccosid.load(fis1);

	}

	// READ CSMC SESSION PROPERTY FILE
	// ==================================================
//	@BeforeMethod
//	public static void readCSMCSessionidprop() throws IOException {
//
//		/**
//		 * Load the Property File
//		 */
//
//		FileInputStream fis1 = new FileInputStream(new File(
//				System.getProperty("user.dir") + "\\CSMC-Connector\\com\\gcc\\connector\\Config\\sessionid.properties"));
//
//		// Create Properties class object to read properties file
//		csmcsid = new Properties();
//
//		// Load file so we can use into our script
//		csmcsid.load(fis1);
//
//	}

	// WRITE AKENEO SESSION PROPERTY FILE
	// ==================================================
	public static void writeAkeneoSessionPropertyFile() {
		try {
			System.out.println("first");
			Properties properties = new Properties();
			System.out.println("second2");
			properties.setProperty("Session_Id", BaseClassMethods.apisession_id);
			System.out.println("second");
			File fis = new File(System.getProperty("user.dir")
					+ "\\AKENEO-Connector\\com\\gcc\\connector\\Config\\sessionid.properties");
			System.out.println("third");
			FileOutputStream fileOut = new FileOutputStream(fis);
			properties.store(fileOut, "# LISTING AKENEO SESSION IDS");
			fileOut.close();
			System.out.println("last");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/// READ AKENEO SESSIONID PROPERTY FILE
	/// ==================================================
	@BeforeClass
	public static void readAKENEOSessionidprop() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream fis1 = new FileInputStream(new File(System.getProperty("user.dir")
				+ "\\AKENEO-Connector\\com\\gcc\\connector\\Config\\sessionid.properties"));

		// Create Properties class object to read properties file
		akeneosid = new Properties();

		// Load file so we can use into our script
		akeneosid.load(fis1);

	}

	// WRITE ZENDESK SESSION PROPERTY FILE
	// ==================================================
	public static void writezendeskSessionPropertyFile() {
		try {
			System.out.println("first");
			Properties properties = new Properties();
			System.out.println("second2");
			properties.setProperty("Session_Id", BaseClassMethods.apisession_id);
			System.out.println("second");
			File fis = new File(System.getProperty("user.dir")
					+ "\\ZENDESK-Connector\\com\\gcc\\connector\\Config\\sessionid.properties");
			System.out.println("third");
			FileOutputStream fileOut = new FileOutputStream(fis);
			properties.store(fileOut, "# LISTING ZENDESK SESSION IDS");
			fileOut.close();
			System.out.println("last");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/// READ AKENEO SESSIONID PROPERTY FILE
//	/// ==================================================
//	@BeforeMethod
//	public static void readZENDESKSessionidprop() throws IOException {
//		/**
//		 * Load the Property File
//		 */
//
//		FileInputStream fis1 = new FileInputStream(new File(System.getProperty("user.dir")
//				+ "\\ZENDESK-Connector\\com\\gcc\\connector\\Config\\sessionid.properties"));
//
//		// Create Properties class object to read properties file
//		zendesksid = new Properties();
//
//		// Load file so we can use into our script
//		zendesksid.load(fis1);
//
//	}

//	// WRITE ggg SESSION PROPERTY FILE
//	// ==================================================
//	public static void writegggSessionPropertyFile() {
//		try {
//
//			Properties properties = new Properties();
//
//			properties.setProperty("Session_Id", BaseClassMethods.session_id);
//
//			File fis = new File(
//					System.getProperty("user.dir") + "\\GGG-Connector\\com\\gcc\\ggg\\Config\\sessionid.properties");
//
//			FileOutputStream fileOut = new FileOutputStream(fis);
//			properties.store(fileOut, "# LISTING GGG SESSION IDS");
//			fileOut.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	/// READ ggg SESSIONID PROPERTY FILE
//	/// ==================================================
//	@BeforeMethod
//	public static void readgggSessionidprop() throws IOException {
//		/**
//		 * Load the Property File
//		 */
//
//		FileInputStream fisgg = new FileInputStream(new File(
//				System.getProperty("user.dir") + "\\GGG-Connector\\com\\gcc\\ggg\\Config\\sessionid.properties"));
//
//		// Create Properties class object to read properties file
//		gggsid = new Properties();
//
//		// Load file so we can use into our script
//		gggsid.load(fisgg);
//
//	}

	// WRITE ZENDESK SESSION PROPERTY FILE
	// ==================================================
	public static void writeeloquaSessionPropertyFile() {
		try {
			System.out.println("first");
			Properties properties = new Properties();
			System.out.println("second2");
			properties.setProperty("Session_Id", BaseClassMethods.apisession_id);
			System.out.println("second");
			File fis = new File(System.getProperty("user.dir")
					+ "\\ELOQUA-Connector\\com\\gcc\\connector\\Config\\sessionid.properties");
			System.out.println("third");
			FileOutputStream fileOut = new FileOutputStream(fis);
			properties.store(fileOut, "# LISTING ELOQUA SESSION IDS");
			fileOut.close();
			System.out.println("last");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/// READ ELOQUA SESSIONID PROPERTY FILE
	/// ==================================================
	@BeforeClass
	public static void readELOQUASessionidprop() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream fis1 = new FileInputStream(new File(System.getProperty("user.dir")
				+ "\\ELOQUA-Connector\\com\\gcc\\connector\\Config\\sessionid.properties"));

		// Create Properties class object to read properties file
		eloquasid = new Properties();

		// Load file so we can use into our script
		eloquasid.load(fis1);

	}
//
//	// READ CONTENT ID PROPERTY FILE
//	// ==================================================
//	@BeforeMethod
//	public static void readContentidprop() throws IOException {
//		/**
//		 * Load the Property File
//		 */
//
//		FileInputStream fis2 = new FileInputStream(new File(
//				System.getProperty("user.dir") + "\\src\\main\\java\\com\\gcc\\apis\\Config\\contentid.properties"));
//
//		// Create Properties class object to read properties file
//		pro2 = new Properties();
//
//		// Load file so we can use into our script
//		pro2.load(fis2);
//
//	}

	// WRITE CONTENT ID PROPERTY FILE
	// ==================================================
	public static void writeContentPropertyFileold() {
		try {
			Properties properties = new Properties();
			String content = (String) properties.setProperty("Content_Id", BaseClassMethods.content_id);

			File fis = new File(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\gcc\\apis\\Config\\ids.properties");
			FileOutputStream fileOut = new FileOutputStream(fis);
			for (int i = 5; i < 7; i++) {
				properties.store(fileOut, "# content id");
			}
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// READ REFERENCE ID PROPERTY FILE
	// ==================================================
//	@BeforeMethod
//	public static void readReferenceidprop() throws IOException {
//		/**
//		 * Load the Property File
//		 */
//
//		FileInputStream fis3 = new FileInputStream(new File(
//				System.getProperty("user.dir") + "\\src\\main\\java\\com\\gcc\\apis\\Config\\referenceid.properties"));
//
//		// Create Properties class object to read properties file
//		pro3 = new Properties();
//
//		// Load file so we can use into our script
//		pro3.load(fis3);
//
//	}

	// WRITE REFERENCE ID PROPERTY FILE
	// ==================================================
	public static void writeReferencePropertyFile() {
		try {
			Properties properties = new Properties();
			properties.setProperty("Reference_Id", BaseClassMethods.reference_id);

			File fis = new File(System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\gcc\\apis\\Config\\referenceid.properties");

			FileOutputStream fileOut = new FileOutputStream(fis);
			properties.store(fileOut, "# LISTING REFERENCE IDS");
			fileOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// WRITE SUBMISSION ID PROPERTY FILE
	// ==================================================
	public static void writeSubmissionPropertyFile() {
		try {

			Properties properties = new Properties();
			properties.setProperty("Submission_Id", Long.toString(BaseClassMethods.submission_id));

			File fis = new File(System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\gcc\\apis\\Config\\submissionid.properties");

			FileOutputStream fileOut = new FileOutputStream(fis);
			properties.store(fileOut, "# LISTING SUBMISSION IDS");
			fileOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// READ GITHUB SUBMISSION ID PROPERTY FILE
//	 ==================================================
	@BeforeMethod
	public static void readGitSubmissionidprop() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream fisgithub = new FileInputStream(new File(System.getProperty("user.dir")
				+ "\\GITHUB-Connector\\com\\gcc\\connector\\Config\\submissionid.Properties"));

		// Create Properties class object to read properties file
		gitsub = new Properties();

		// Load file so we can use into our script
		gitsub.load(fisgithub);

	}

	// READ SFCC SUBMISSION ID PROPERTY FILE
	// ==================================================
	@BeforeMethod
	public static void readsfccSubmissionidprop() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream fissfcc = new FileInputStream(new File(System.getProperty("user.dir")
				+ "\\SFCC-Connector\\com\\gcc\\connector\\Config\\submissionid.Properties"));

		// Create Properties class object to read properties file
		sfccsub = new Properties();

		// Load file so we can use into our script
		sfccsub.load(fissfcc);

	}

	// READ CCO SUBMISSION ID PROPERTY FILE
	// ==================================================
	@BeforeMethod
	public static void readCCOSubmissionidprop() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream fiscco = new FileInputStream(new File(System.getProperty("user.dir")
				+ "\\CCO-Connector\\com\\gcc\\connector\\Config\\submissionid.Properties"));

		// Create Properties class object to read properties file
		ccosub = new Properties();

		// Load file so we can use into our script
		ccosub.load(fiscco);

	}

	// READ CSMC SUBMISSION ID PROPERTY FILE
	// ==================================================
	@BeforeMethod
	public static void readCSMCSubmissionidprop() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream fiscsmc = new FileInputStream(new File(System.getProperty("user.dir")
				+ "\\CSMC-Connector\\com\\gcc\\connector\\Config\\submissionid.Properties"));

		// Create Properties class object to read properties file
		csmcsub = new Properties();

		// Load file so we can use into our script
		csmcsub.load(fiscsmc);

	}

	// READ AKENEO SUBMISSION ID PROPERTY FILE
	// ==================================================
	@BeforeMethod
	public static void readAKENEOSubmissionidprop() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream fisakeneo = new FileInputStream(new File(System.getProperty("user.dir")
				+ "\\AKENEO-Connector\\com\\gcc\\connector\\Config\\submissionid.Properties"));

		// Create Properties class object to read properties file
		akeneosub = new Properties();

		// Load file so we can use into our script
		akeneosub.load(fisakeneo);

	}

	// READ ZENDESK SUBMISSION ID PROPERTY FILE
	// ==================================================
	@BeforeMethod
	public static void readZENDESKSubmissionidprop() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream fisZENDESK = new FileInputStream(new File(System.getProperty("user.dir")
				+ "\\ZENDESK-Connector\\com\\gcc\\connector\\Config\\submissionid.Properties"));

		// Create Properties class object to read properties file
		zendesksub = new Properties();

		// Load file so we can use into our script
		zendesksub.load(fisZENDESK);

	}

//
//	// READ ELOQUA SUBMISSION ID PROPERTY FILE
//	// ==================================================
	@BeforeMethod
	public static void readELOQUASubmissionidprop() throws IOException {
		/**
		 * Load the Property File
		 */

		FileInputStream fiseloquasub = new FileInputStream(new File(System.getProperty("user.dir")
				+ "\\ELOQUA-Connector\\com\\gcc\\connector\\Config\\submissionid.Properties"));

		// Create Properties class object to read properties file
		eloquasub = new Properties();

		// Load file so we can use into our script
		eloquasub.load(fiseloquasub);

	}

//	// READ GGG SUBMISSION ID PROPERTY FILE
//		// ==================================================
//		@BeforeMethod
//		public static void FSubmissionidprop() throws IOException {
//			/**
//			 * Load the Property File
//			 */
//System.out.println("ggggggggggggggggggggggggggggggggggggggggggggggggggg");
//			FileInputStream fisggg = new FileInputStream(new File(System.getProperty("user.dir")
//					+ "\\GGG-Connector\\com\\gcc\\ggg\\Config\\submissionid.Properties"));
//
//			// Create Properties class object to read properties file
//			gggsub = new Properties();
//
//			// Load file so we can use into our script
//			gggsub.load(fisggg);
//
//			
//			System.out.println("g1111111111111111111111111111111111111111111111111111g");
//		}
//		

	// READ SUBMISSION ID PROPERTY FILE
	// ==================================================
//	@BeforeMethod
//	public static void readSubmissionidprop() throws IOException {
//		/**
//		 * Load the Property File
//		 */
//
//		FileInputStream fis4 = new FileInputStream(new File(
//				System.getProperty("user.dir") + "\\src\\main\\java\\com\\gcc\\apis\\Config\\submissionid.properties"));
//
//		// Create Properties class object to read properties file
//		pro4 = new Properties();
//
//		// Load file so we can use into our script
//		pro4.load(fis4);
//
//	}

	// WRITE CONTENT ID PROPERTY FILE
	// ==================================================
	public static void writeContentPropertyFile() {
		try {
			Properties properties = new Properties();
			properties.setProperty("Content_Id", BaseClassMethods.content_id);
			File fis = new File(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\gcc\\apis\\Config\\contentid.properties");
			FileOutputStream fileOut = new FileOutputStream(fis);
			properties.store(fileOut, "# LISTING  CONTENT IDS");
			fileOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

//	public static void writeJobPropertyFile() {
//		try {
//			Properties properties = new Properties();
//			properties.setProperty("Job_Id", BaseClassMethods.content_id);
//			File fis = new File(
//					System.getProperty("user.dir") + "\\src\\main\\java\\com\\gcc\\apis\\Config\\contentid.properties");
//			FileOutputStream fileOut = new FileOutputStream(fis);
//			properties.store(fileOut, "# LISTING  CONTENT IDS");
//			fileOut.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}



