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
	public ForecastObject getFilteredForecastObject() {
		
		ForecastObject fobj = null;
		
		for(int i=0; i<getF().getForecastLength(); i++) {
			
			if(getF().getForecastObject(i).getTime().equals(this.time)) {
				
				fobj = getF().getForecastObject(i);
			}
		}
		
		return fobj;
	}
	
	
}
