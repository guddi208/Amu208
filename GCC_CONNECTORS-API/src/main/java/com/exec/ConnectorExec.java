package com.exec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;


import org.testng.TestNG;
import org.testng.collections.Lists;
import org.xml.sax.SAXException;

import com.beust.jcommander.JCommander;

import org.testng.xml.Parser;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;




public class ConnectorExec {

	
	//static TestNG testNg;//no need
	
	
	public static void main(String[] args) throws SAXException, ParserConfigurationException {
		// TODO Auto-generated method stub

		
	
		
		
		  TestNG testng = new TestNG(); 
		 List<String> suites = Lists.newArrayList();
		 suites.add("C:\\Users\\amundhare\\eclipse-workspace\\GCC_CONNECTORS-API\\testng.xml");//path to xml..
		 
		 testng.setTestSuites(suites);
		 testng.run();
		 
		 
		
		

		
	
	}

}
