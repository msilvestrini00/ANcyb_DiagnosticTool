package it.univpm.ancyb_diagnosticTool.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentDateTime {
	
	public static String currentDateTime() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH.mm.ss");
		LocalDateTime now = LocalDateTime.now(); 
		String currDateTime = dtf.format(now);
		return currDateTime;
		
	}
	
	public static String currentDate() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		LocalDateTime now = LocalDateTime.now(); 
		String currDate = dtf.format(now);
		return currDate;
		
	}
	
	public static String currentTime() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH.mm.ss");
		LocalDateTime now = LocalDateTime.now(); 
		String currTime = dtf.format(now);
		return currTime;
		
	}
	
}
