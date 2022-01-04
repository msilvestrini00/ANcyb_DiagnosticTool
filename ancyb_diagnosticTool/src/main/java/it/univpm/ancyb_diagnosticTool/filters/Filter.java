package it.univpm.ancyb_diagnosticTool.filters;


import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;




public class Filter {

	Forecast f;

	public Filter(Forecast f) {
		
		this.f = f;
	}
	
	
	
	public Forecast getF() {
		return f;
	}

	public void setF(Forecast f) {
		this.f = f;
	}


	public ForecastObject getFilteredForecastObject() {
		
		return null;
	}

	
}
