package it.univpm.ancyb_diagnosticTool.utilities;

public class ForecastTimeConv {


	public static int ForecastTimeToHour(String time) {
		
		return Integer.parseInt(time.substring(11, 13));
	}

	public static int ForecastTimeToDay(String time) {
		
		return Integer.parseInt(time.substring(8, 10)) + 1;
	}
}
