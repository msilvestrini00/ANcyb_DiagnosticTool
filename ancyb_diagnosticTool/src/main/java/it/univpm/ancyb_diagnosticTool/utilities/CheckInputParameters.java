package it.univpm.ancyb_diagnosticTool.utilities;

import it.univpm.ancyb_diagnosticTool.Exception.InvalidParameter;

/**
 * <b>Classe</b> a scopo generico, utilizzata per controllare la validità di eventuali parametri d'ingresso.
 * 
 * @author Manuele Silvestrini
 */
public class CheckInputParameters {

	/**
	 * <b>Metodo</b> che controlla i parametri in ingresso durante la chiamata alla rotta '/forecast/filter'.
	 * @param macAddr Indirizzo mac da controllare.
	 * @param date Parametro della data da controllare.
	 * @param hour Parametro delle ore da controllare.
	 * @throws InvalidParameter
	 * 
	 *  @see it.univpm.ancyb_diagnosticTool.controller.ANcybRestController#getSelectedTimeForecast(String, String, byte)
	 */
	public static void CheckForecastFilterParameters(String macAddr, String date, byte hour) throws InvalidParameter {
		
		CheckMacAddr(macAddr); 
		CheckDate(date); 
		
		if(hour < 0 || hour > 23) throw new InvalidParameter("InvalidParameter(ForecastFilterParameters) -> invalid hour.");
	}
	
	/**
	 * <b>Metodo</b> che controlla i parametri in ingresso durante la chiamata alla rotta '/forecast/stats'.
	 * @param macAddr Indirizzo mac da controllare.
	 * @param days Parametro dei giorni da controllare.
	 * @throws InvalidParameter
	 * 
	 *  @see it.univpm.ancyb_diagnosticTool.controller.ANcybRestController#getForecastStatistics(String, byte)
	 */
	public static void CheckForecastStatsParameters(String macAddr, byte days) throws InvalidParameter {
		
		CheckMacAddr(macAddr); 
		
		if(days < 1 || days > 7) throw new InvalidParameter("InvalidParameter(ForecastStatsParameters) -> invalid days.");
	}
	
	/**
	 * <b>Metodo</b> che controlla l'indirizzo mac immesso.
	 * @param macAddr Indirizzo mac da controllare.
	 * @throws InvalidParameter
	 * 
	 *  @see it.univpm.ancyb_diagnosticTool.controller.ANcybRestController#getRealTimeForecast(String)
	 */
	public static void CheckMacAddr(String macAddr) throws InvalidParameter{
		
		boolean exc = false;
		String[] tokens = macAddr.split(":");
		int i;

		for(i = 0; i < tokens.length; i++) {
			
			if(tokens[i].length() != 2) exc = true;
		}
		if(i != 6) exc = true;
		
		if(exc) throw new InvalidParameter("InvalidParameter(CheckMacAddr) -> invalid MAC address.");
	}
	
	/**
	 * <b>Metodo</b> che controlla la data immessa.
	 * @param date Parametro della data da controllare.
	 * @throws InvalidParameter
	 */
	private static void CheckDate(String date) throws InvalidParameter{
		
		boolean exc = false;
		String[] tokens = date.split("-");
		
		if(tokens.length != 3) exc = true;

		if(tokens[0].length() != 4) exc = true;
		if(tokens[1].length() != 2) exc = true;
		if(tokens[2].length() != 2) exc = true;
		
		if(Integer.parseInt(tokens[2]) > (Integer.parseInt(Time.currentDay()) + 7)) exc = true;

		if(exc) throw new InvalidParameter("InvalidParameter(ForecastFilterParameters) -> invalid date.");
	}

	/**
	 * <b>Metodo</b> che controlla l'orario immesso.
	 * @param time Parametro dell'orario da controllare.
	 * @throws InvalidParameter
	 */
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
		if(exc) throw new InvalidParameter("InvalidParameter(CheckTime) -> invalid time.");
	}
	
}
