package it.univpm.ancyb_diagnosticTool.filters;

import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;


public class FilterByTime extends Filter {
	
	private String time;
	
	public FilterByTime(Forecast f, String time) {
		
		super(f);
		
		this.time = time;
 }

	@Override
	public ForecastObject getFilteredForecastObject() {	//TODO qui ci va un'eccezione
		
		ForecastObject fobj = null;
		
		forecastToFilter = getForecastToFilter();
		
		for(int i=0; i<forecastToFilter.getForecastLength(); i++) {	//TODO mettere il for each?
			
			if(forecastToFilter.getForecastObject(i).getTime().equals(this.time)) {
				
				fobj = forecastToFilter.getForecastObject(i);
			}
		}
		
		return fobj;
	}
	
	
}
