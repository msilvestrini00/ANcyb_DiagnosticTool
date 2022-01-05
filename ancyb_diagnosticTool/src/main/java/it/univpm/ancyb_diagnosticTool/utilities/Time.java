package it.univpm.ancyb_diagnosticTool.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//TODO se ci sono più autori??
/**
 * 
 * @author giaco
 *
 */
public class Time {
	
	/**
	 * Questo metodo restituisce in stringa la data e il tempo corrente
	 * @return
	 */
	public static String currentDateTime() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH.mm.ss");
		LocalDateTime now = LocalDateTime.now(); 
		String currDateTime = dtf.format(now);
		return currDateTime;
		
	}
	
	/**
	 * Questo metodo restituisce in stringa la data corrente
	 * @return
	 */
	public static String currentDate() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		LocalDateTime now = LocalDateTime.now(); 
		String currDate = dtf.format(now);
		return currDate;
		
	}
	
	/**
	 * Questo metodo restituisce in stringa il tempo corrente
	 * @return
	 */
	public static String currentTime() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH.mm.ss");
		LocalDateTime now = LocalDateTime.now(); 
		String currTime = dtf.format(now);
		return currTime;
		
	}
	
	//format used for filtering ForecastObjects by the time string
	public static String currentDateTime2() {	//"2022-01-05T00:00:00+00:00"
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:00:00+00:00");
		LocalDateTime now = LocalDateTime.now(); 
		String currDateTime = dtf.format(now);
		String newcurrDateTime = currDateTime.replace('.', 'T');
		return newcurrDateTime;
		
	}
}
