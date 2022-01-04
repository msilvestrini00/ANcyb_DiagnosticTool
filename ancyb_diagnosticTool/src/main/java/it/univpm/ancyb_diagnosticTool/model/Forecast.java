package it.univpm.ancyb_diagnosticTool.model;

import java.util.ArrayList;


public class Forecast {

	ArrayList<ForecastObject> forecastList = new ArrayList<ForecastObject>();
	
	
	public Forecast(ArrayList<ForecastObject> forecastList) {
		
		this.forecastList = forecastList;
	}
	
	
	public int getForecastLength() {
		
		return this.forecastList.size();
	}
	
	public void addToForecast(ForecastObject obj) {
		forecastList.add(obj);
	}
	
	
	public ForecastObject getForecastObject(int i) {
		
		return this.forecastList.get(i);
	}
	

	

	
	
	
	
	
	
}
