package it.univpm.ancyb_diagnosticTool.utilities;

import it.univpm.ancyb_diagnosticTool.Exception.InvalidParameter;

/*
 * 
 */

public class CheckInputParameters {

	
	public static void CheckForecastFilterParameters(String macAddr, String date, byte hour) throws InvalidParameter {
		
		CheckMacAddr(macAddr); 

		if(CheckDate(date)) throw new InvalidParameter("InvalidParameter(FilterParameters) -> invalid date");
		
		if(hour < 0 || hour > 23) throw new InvalidParameter("Invalid parameter(hour)");
	}
	
	public static void CheckForecastStatsParameters(String macAddr, byte days) throws InvalidParameter {
		
		CheckMacAddr(macAddr); 
		
		if(days < 1 || days > 9) throw new InvalidParameter("Invalid parameter: days");
	}
	
	public static void CheckMacAddr(String macAddr) throws InvalidParameter{
		
		boolean exc = false;
		String[] tokens = macAddr.split(":");
		int i;

		for(i = 0; i < tokens.length; i++) {
			
			if(tokens[i].length() != 2) exc = true;
		}
		if(i != 6) exc = true;
		
		if(exc) throw new InvalidParameter("Invalid parameter: mac address");
	}
	
	private static boolean CheckDate(String date) {
		
		String[] tokens = date.split("-");
		
		if(tokens.length != 3) return true;

		if(tokens[0].length() != 4) return true;
		if(tokens[1].length() != 2) return true;
		if(tokens[2].length() != 2) return true;

		return false;
	}

	public static void CheckTime(String time) throws InvalidParameter {
		
		boolean exc = false;
		String[] tokens = time.split(":");
		
		if(tokens.length != 3) exc = true;
		for(int i=0; i<3; i++) {
			if(tokens[i].length() != 2) exc = true;
			try {
				Double.parseDouble(tokens[i]); 
			  } catch(NumberFormatException e){  
				exc = true;			  
			}  
		}
		if(exc) throw new InvalidParameter("Invalid parameter: time");
	}
	
}
