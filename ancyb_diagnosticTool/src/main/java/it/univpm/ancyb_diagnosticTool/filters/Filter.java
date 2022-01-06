package it.univpm.ancyb_diagnosticTool.filters;


import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;

//Rinonima in FilterForecast
public class Filter {

	Forecast forecastToFilter;

	
	public Filter(Forecast forecastToFilter) {
		
		this.forecastToFilter = forecastToFilter;
	}
	
	
	public Forecast getForecastToFilter() {
		return forecastToFilter;
	}

	public ForecastObject getFilteredForecastObject() {
		return null;
	}

	
}
