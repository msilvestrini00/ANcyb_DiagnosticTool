package it.univpm.ancyb_diagnosticTool.filters.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.filters.FilterForecastByTime;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

/**
 * 
 * @author Manuele Silvestrini
 *
 */
class FilterForecastByTimeTest {

	static Forecast f;
	@SuppressWarnings("unused")
	private String s;

	@BeforeAll
	static void setUp() throws Exception {
		
		ArrayList<ForecastObject> list = new ArrayList<ForecastObject>();
		
		ForecastObject f1 = new ForecastObject("00:00:00:00:00:00", 0, 0, Time.currentDateTime2(), 0, 0);
		ForecastObject f2 = new ForecastObject("11:11:11:11:11:11", 0, 0, "2022-01-01T00:00:00+00:00", 0, 0);

		list.add(f1);
		list.add(f2);

		f = new Forecast(list);

	}
	
	void tearDown() {}
	
	@Test
	@DisplayName("Test sul filtro 'FilterForecastByTime' per il tempo reale")
	void testRealTime() {
		
		String s = null;
		
		try {
			
	    FilterForecastByTime forecastFilter = new FilterForecastByTime(f, Time.currentDateTime2());
	    forecastFilter.computeFilter();
	    s = forecastFilter.getFilteredData().getMacAddress();
		}
		
		catch (FilterFailure e ) {
			System.err.println("Exception: " + e);
		}
		assertEquals(s, "00:00:00:00:00:00");
	}
	
	@Test
	@DisplayName("Test sul filtro 'FilterForecastByTime' per il tempo selezionato (corretto)")
	void testSelectedTime() {
		
		String s = null;
		
		try {
			
	    FilterForecastByTime forecastFilter = new FilterForecastByTime(f, "2022-01-01T00:00:00+00:00");
	    forecastFilter.computeFilter();
	    s = forecastFilter.getFilteredData().getMacAddress();
		}
		
		catch (FilterFailure e ) {
			System.err.println("Exception: " + e);
		}
		assertEquals(s, "11:11:11:11:11:11");
	}
	
	@Test
	@DisplayName("Test sul filtro 'FilterForecastByTime' per il tempo selezionato (fallimentare, per generare l'eccezione)")
	void testSelectedTimeFail() {
				
	    Exception exception = assertThrows(FilterFailure.class, () -> {

			FilterForecastByTime forecastFilter = new FilterForecastByTime(f, "2022-01-20T00:00:00+00:00");
			forecastFilter.computeFilter();
			s = forecastFilter.getFilteredData().getMacAddress();

	    });

	    String expectedMessage = "No forecast with time: 2022-01-20T00:00:00+00:00 has been found.";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}

}
