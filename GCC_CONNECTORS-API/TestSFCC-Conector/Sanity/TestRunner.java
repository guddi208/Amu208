package Sanity;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;



public class TestRunner {

	
	static TestNG testNg;
	
	
	public static void main1(String[] args) {
		// TODO Auto-generated method stub
	
		
		// Create object of TestNG Class
		TestNG runner=new TestNG();
		 
		// Create a list of String 
		List<String> suitefiles=new ArrayList<String>();
		 
		// Add xml file which you have to execute
		suitefiles.add("C:\\GCC_REST_API\\GCC_CONNECTORS\\testng.xml");
		 
		// now set xml file for execution
		runner.setTestSuites(suitefiles);
		 
		// finally execute the runner using run method
		runner.run();
		
		
	}

}
