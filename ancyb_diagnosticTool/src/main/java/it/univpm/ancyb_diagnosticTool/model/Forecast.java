package it.univpm.ancyb_diagnosticTool.model;

import java.util.ArrayList;


public class Forecast {

	private ArrayList<ForecastObject> forecastList = new ArrayList<ForecastObject>();
	
	
	public Forecast(ArrayList<ForecastObject> forecastList) {
		this.forecastList = forecastList;
	}
	
	public ArrayList<ForecastObject> getForecastList() {
		return this.forecastList;
	}
	
	public long getForecastLength() {
		return this.forecastList.size();
	}
	
	public void addToForecast(ForecastObject obj) {
		forecastList.add(obj);
	}
	
	public ForecastObject getForecastObject(int index) {
		return this.forecastList.get(index);
	}
	
	public String getForecastMacAddr() {
		return forecastList.get(0).getMacAddress();
	}
	

	

	
	
	
	
	
	
}
