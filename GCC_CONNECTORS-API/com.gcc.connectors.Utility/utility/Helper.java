package utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
	
	public static String getCurrentDateTime()
	{
		DateFormat customFormat=new SimpleDateFormat("MM_dd-yyyy-HH-mm-ss");
		
		Date currentDate=new Date();
			
		return customFormat.format(currentDate);
		
	}

}
