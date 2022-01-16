package it.univpm.ancyb_diagnosticTool.filters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.service.ForecastDataManager;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

class FilterForecastByTimeTest {

	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		
		
		ForecastDataManager dataManager = new ForecastDataManager(macAddr);
		Forecast f = dataManager.getForecast();

	    FilterForecastByTime forecastFilter = new FilterForecastByTime(f, Time.currentDateTime2());
	    forecastFilter.computeFilter();
		return forecastFilter.getFilteredData();
		
		
		
		
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
