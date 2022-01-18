package it.univpm.ancyb_diagnosticTool.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <b>Classe</b> a scopo generico utilizzata per ottenere data e orario correnti in formati differenti.
 * @author Giacomo Fiara
 * @author Manuele Silvestrini
 *
 */
public class Time {
	
	/*
	 * @return Stringa sulla data e ora correnti nel formato "yyyy.MM.dd_HH.mm.ss"
	 */
	public static String currentDateTime() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH.mm.ss");
		LocalDateTime now = LocalDateTime.now(); 
		String currDateTime = dtf.format(now);
		return currDateTime;
	}
	
	/*
	 * @return Stringa sulla data e ora correnti nel formato "yyyy-MM-dd.HH:00:00+00:00"
	 */
	public static String currentDateTime2() {	
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:00:00+00:00");
		LocalDateTime now = LocalDateTime.now(); 
		String currDateTime = dtf.format(now);
		String newcurrDateTime = currDateTime.replace('.', 'T');
		return newcurrDateTime;
	}
	
	/*
	 * @return Stringa sulla data corrente nel formato "yyyy.MM.dd"
	 */
	public static String currentDate() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		LocalDateTime now = LocalDateTime.now(); 
		String currDate = dtf.format(now);
		return currDate;
	}
	
	/*
	 * @return Stringa sul giorno corrente nel formato "dd"
	 */
	public static String currentDay() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");
		LocalDateTime now = LocalDateTime.now(); 
		String currDate = dtf.format(now);
		return currDate;
	}
	
	/*
	 * @return Stringa sull'orario nel formato "HH.mm.ss"
	 */
	public static String currentTime() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH.mm.ss");
		LocalDateTime now = LocalDateTime.now(); 
		String currTime = dtf.format(now);
		return currTime;
	}
	
	/*
	 * @return Stringa sull'orario nel formato "HH:mm:ss"
	 */
	public static String currentTime2() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now(); 
		String currTime = dtf.format(now);
		return currTime;
	}
	
}
