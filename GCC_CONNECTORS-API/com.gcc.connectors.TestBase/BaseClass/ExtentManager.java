package BaseClass;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.*;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utility.Helper;

public class ExtentManager {

	private static ExtentReports extent;

	private static String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date());
	private static String reportFileName = System.getProperty("user.dir") + "\\TestReport\\" + "GCC-Automaton-Report-"
			+ fileName + ".html";

	private static String fileSeperator = System.getProperty("file.separator");

	private static String reportFilepath = System.getProperty("user.dir") + fileSeperator + "TestReport";
	private static String reportFileLocation = reportFilepath + fileSeperator + reportFileName;

	public static ExtentReports getInstance() {
		if (extent == null) {
			extent = createInstance(reportFileName);
		}
		return extent;
	}

	// Create an extent report instance
	public static ExtentReports createInstance(String fileName) {
		// String fileName = getReportPath(reportFilepath);

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		// htmlReporter.config().set
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle("GCC API CONNECTORS");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("GCC CONNECTORS");
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		// Set environment details
		extent.setSystemInfo("Host Name", "GCC_CONNECTOR");
		extent.setSystemInfo("Environment", "REST API -Automation Testing");
		extent.setSystemInfo("User Name", "--");

		return extent;
	}

	// Create the report path
	private static String getReportPath(String path) {
		File testDirectory = new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				System.out.println("Directory: " + path + " is created!");
				return reportFileLocation;
			} else {
				System.out.println("Failed to create directory: " + path);
				return System.getProperty("user.dir");
			}
		} else {
			System.out.println("Directory already exists: " + path);
		}
		return reportFileLocation;
	}
}
