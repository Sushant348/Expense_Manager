package Model;


import java.util.Calendar;
import java.util.Date;

public class ServiceClass {
	
	
//	public void minDate() {
//		Calendar calendar = Calendar.getInstance();
//		System.out.println(calendar.getTime());
//		System.out.println(calendar.getActualMinimum(calendar.DATE));
//		System.out.println(calendar.getActualMaximum(calendar.DATE));
//	
//		Date d = new Date();
//		System.out.println(d.getTime());
//		System.out.println(d.getYear()+1900); // 124+1900
//		System.out.println(d.getMonth()+1); // 1+1
//		
//	}
	
	public  String startPoint() {
		
		Calendar calendar = Calendar.getInstance();
		String startPoint = "";
		int date = calendar.getActualMinimum(calendar.DATE);
		
		Date d = new Date();
		int month = d.getMonth()+1;
		int year = d.getYear()+1900;
		
		if(month<10)
		startPoint = year+"-0"+month+"-0"+date;
		else
			startPoint = year+"-"+month+"-0"+date;
		System.out.println(startPoint);
		return startPoint;
		
	}

	public String endPoint() {
		
		Calendar calendar = Calendar.getInstance();
		String endPoint = "";
		int date = calendar.getActualMaximum(calendar.DATE);
		
		Date d = new Date();
		int month = d.getMonth()+1;
		int year = d.getYear()+1900;
		if(month<10)
		endPoint = year+"-0"+month+"-"+date;
		else
			endPoint = year+"-"+month+"-"+date;	
		System.out.println(endPoint);
		return endPoint;
		
	}

}
