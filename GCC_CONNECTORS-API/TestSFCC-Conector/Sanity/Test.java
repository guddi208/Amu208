package Sanity;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.plexus.util.FileUtils;

public class Test {

	public static List<File> listf(String directoryName) {
		File directory = new File(directoryName);

		List<File> resultList = new ArrayList<File>();

		// get all the files from a directory
		File[] fList = directory.listFiles();

		resultList.addAll(Arrays.asList(fList));
		for (File file : fList) {
			// System.out.println("LIST IS___________" + file);

		
			String txtFile;
			String jsonFile;
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
				}
				else
				if (file.isFile()) {
					// System.out.println("abs" + file.getAbsolutePath());
				} else if (file.isDirectory()) {
					resultList.addAll(listf(file.getAbsolutePath()));
				}
			}
		}
		// System.out.println(fList);
		return resultList;
	}

	public static void main(String[] args) {
		listf("C:\\Connector");
	}

}
