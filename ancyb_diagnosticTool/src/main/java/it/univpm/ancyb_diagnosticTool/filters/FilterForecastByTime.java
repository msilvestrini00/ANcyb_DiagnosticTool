package it.univpm.ancyb_diagnosticTool.filters;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;


public class FilterForecastByTime implements FilterInterface {
	
	private Forecast forecastToFilter;
	private String time;

	public FilterForecastByTime(Forecast forecastToFilter, String time) {
		
		this.forecastToFilter = forecastToFilter;
		this.time = time;
 }

	
	@Override
	public Forecast getDataToFilter() {
		return this.forecastToFilter;
	}
	
	@Override
	public ForecastObject getDataFiltered() throws FilterFailure{	
		
		ForecastObject output = null;
				
		//for(int i=0; i<forecastToFilter.getForecastLength(); i++) {
		
		for(ForecastObject fobj : forecastToFilter.getForecastList()) {
					
			if(fobj.getTime().equals(this.time)) output = fobj;
		}
		if(output ==null) {
			throw new FilterFailure("Nessuna previsione trovata per l'orario inserito: " + this.time);
		}
		return output;	
	}
}
