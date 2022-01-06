package it.univpm.ancyb_diagnosticTool.filters;


import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;

//Rinonima in FilterForecast
public class Filter {

	
	Forecast forecast;

	
	public Filter(Forecast f) {
		
		this.forecast = f;
	}
	
	
	public Forecast getForecast() {
		return forecast;
	}

	public void setForecast(Forecast f) {
		this.forecast = f;
	}


	public ForecastObject getFilteredForecastObject() {
		
		return null;
	}

	
}
