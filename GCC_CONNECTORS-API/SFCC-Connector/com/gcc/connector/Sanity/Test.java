package com.gcc.connector.Sanity;

import java.io.IOException;

import org.testng.TestNG;
import org.testng.annotations.DataProvider;

public class Test {

	static TestNG testNg;

	@DataProvider(name="data-provider")
	public static Object[][] createData() {
		return new Object[][] { { "data one" }, { "data two" } };
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		testNg = new TestNG();
		testNg.setTestClasses(new Class[] {SFCC.class});
		testNg.run();

	}

}
