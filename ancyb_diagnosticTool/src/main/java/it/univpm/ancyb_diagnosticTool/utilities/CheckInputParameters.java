package it.univpm.ancyb_diagnosticTool.utilities;

import it.univpm.ancyb_diagnosticTool.Exception.InvalidParameter;

/*
 * 
 */

public class CheckInputParameters {

	
	public static void CheckForecastFilterParameters(String macAddr, String date, int hour) throws InvalidParameter {
		
		try {
		CheckMacAddr(macAddr); 
		} catch(InvalidParameter e) {
			throw new InvalidParameter("Invalid mac address parameter.");
		}
		
		if(CheckDate(date)) throw new InvalidParameter("Invalid date parameter.");
		
		if(hour < 0 || hour > 23) throw new InvalidParameter("Invalid hour parameter.");
	}
	
	
	public static void CheckForecastStatsParameters(String macAddr, int days) throws InvalidParameter {
		
		try {
		CheckMacAddr(macAddr); 
		} catch(InvalidParameter e) {
			throw new InvalidParameter("Invalid mac address parameter.");
		}
		
		if(days < 1 || days > 7) throw new InvalidParameter("Invalid days parameter.");
	}
	
	
	public static void CheckMacAddr(String macAddr) throws InvalidParameter{
		
		String[] tokens = macAddr.split(":");
		int i;

		for(i = 0; i < tokens.length; i++) {
			
			if(tokens[i].length() != 2) throw new InvalidParameter("Invalid mac address parameter.");
		}
		if(i != 6) throw new InvalidParameter("Invalid mac address parameter.");
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
		
		String[] tokens = time.split(":");
		
		if(tokens.length != 3) throw new InvalidParameter("Invalid time parameter");
		for(int i=0; i<3; i++) {
			if(tokens[i].length() != 2) throw new InvalidParameter("Invalid time parameter");
			try {
				Double.parseDouble(tokens[i]); 
			  } catch(NumberFormatException e){  
				throw new InvalidParameter("Invalid time parameter"); 
			  }  
		}
	}
}
