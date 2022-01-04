package it.univpm.ancyb_diagnosticTool.model;

import java.util.ArrayList;


public class Forecast {

	ArrayList<ForecastObject> forecastList = new ArrayList<ForecastObject>();
	
	
	public Forecast(ArrayList<ForecastObject> forecastList) {
		
		this.forecastList = forecastList;
	}
	
	
	public void addToForecast(ForecastObject obj) {
		forecastList.add(obj);
	}
	

	
	
	
	
	
	
}
