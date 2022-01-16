package it.univpm.ancyb_diagnosticTool.filters;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;


public class FilterForecastByTime implements FilterInterface {
	
	private Forecast forecastToFilter;
	private String time;
	private ForecastObject filteredForecast ;

	public FilterForecastByTime(Forecast forecastToFilter, String time) {
		
		this.forecastToFilter = forecastToFilter;
		this.time = time;
		this.filteredForecast = null;
 }

	
	@Override
	public Forecast getDataToFilter() {
		return this.forecastToFilter;
	}
	
	@Override
	public ForecastObject getFilteredData() throws FilterFailure{	
		
		if(filteredForecast == null) {
			throw new FilterFailure("Filter not yet computed. Please, first invoke the method 'computeFilter()'.");
		}
		return filteredForecast;	
	}


	@Override
	public void computeFilter() throws FilterFailure {
				
		for(ForecastObject fobj : forecastToFilter.getForecastList()) {
					
			if(fobj.getForecastTime().equals(this.time)) {
				this.filteredForecast = fobj;
				break;
			}
		}
		
		if(filteredForecast == null) {
			throw new FilterFailure("No forecast with time: " + this.time + " has been found.");
		}
		
	}
}
