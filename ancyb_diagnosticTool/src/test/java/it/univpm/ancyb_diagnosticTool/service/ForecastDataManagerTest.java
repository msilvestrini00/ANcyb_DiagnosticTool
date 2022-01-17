package it.univpm.ancyb_diagnosticTool.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.ForecastBuildingFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;

class ForecastDataManagerTest {

	static String data = "{\"hours\":["
			+  "{\"airTemperature\":[{\"source\":\"sg\",\"value\":8.93},{\"source\":\"noaa\",\"value\":5.47},{\"source\":\"dwd\",\"value\":8.93}],"
			+  "\"cloudCover\":[{\"source\":\"sg\",\"value\":0.0},{\"source\":\"noaa\",\"value\":0.0},{\"source\":\"dwd\",\"value\":0.0}],"
			+  "\"currentDirection\":[{\"source\":\"sg\",\"value\":356.15},{\"source\":\"meto\",\"value\":356.15}],"
			+  "\"time\":\"2022-01-01T00:00:00+00:00\","
			+  "\"visibility\":[{\"source\":\"sg\",\"value\":24.13},{\"source\":\"noaa\",\"value\":24.13}],"
			+  "\"waveHeight\":[{\"source\":\"sg\",\"value\":0.27},{\"source\":\"noaa\",\"value\":0.29},{\"source\":\"dwd\",\"value\":0.16},{\"source\":\"icon\",\"value\":0.37},{\"source\":\"meteo\",\"value\":0.27}]},"
			
			+  "{\"airTemperature\":[{\"source\":\"sg\",\"value\":8.93},{\"source\":\"noaa\",\"value\":5.47},{\"source\":\"dwd\",\"value\":8.93}],"
			+  "\"cloudCover\":[{\"source\":\"sg\",\"value\":0.0},{\"source\":\"noaa\",\"value\":0.0},{\"source\":\"dwd\",\"value\":0.0}],"
			+  "\"currentDirection\":[{\"source\":\"sg\",\"value\":335.55},{\"source\":\"meto\",\"value\":356.15}],"
			+  "\"time\":\"2022-01-01T01:00:00+00:00\","
			+  "\"visibility\":[{\"source\":\"sg\",\"value\":24.13},{\"source\":\"noaa\",\"value\":24.13}],"
			+  "\"waveHeight\":[{\"source\":\"sg\",\"value\":0.33},{\"source\":\"noaa\",\"value\":0.29},{\"source\":\"dwd\",\"value\":0.16},{\"source\":\"icon\",\"value\":0.37},{\"source\":\"meteo\",\"value\":0.27}]}]}";
	
	static Forecast f = new Forecast(null);

	@SuppressWarnings("unused")
	private static ANcybFishData fishdata1;

	@BeforeAll
	static void setUp() throws Exception {
		
		ANcybDataManager ancybDataManager = new ANcybDataManager();
		fishdata1 = ancybDataManager.createDataObj("00:00:00:00:00:00 Ver_G 16:05:45 4334.3060N 01335.1580E 1");

	}
	
	void tearDown() {}
	
	@Test
	@DisplayName("Test sul metodo 'buildForecast()' (corretto)")
	void test1() {
		
		try {
			
			ForecastDataManager dataManager = new ForecastDataManager("00:00:00:00:00:00");
			f = dataManager.buildForecast(data);
		}
		catch (VersionMismatch | ForecastBuildingFailure | FilterFailure  e) {
			System.err.println("Exception: " + e);
		}

		double currDir0 = Math.round(f.getForecastObject(0).getCurrentDirection() * 100.0) / 100.0;
		String time0 = f.getForecastObject(0).getForecastTime();
		double waveHei0 = Math.round(f.getForecastObject(0).getWaveHeight() * 100.0) / 100.0;
		
		double currDir1 = Math.round(f.getForecastObject(1).getCurrentDirection() * 100.0) / 100.0;
		String time1 = f.getForecastObject(1).getForecastTime();
		double waveHei1 = Math.round(f.getForecastObject(1).getWaveHeight() * 100.0) / 100.0;
		
		assertEquals(356.15, currDir0);
		assertEquals("2022-01-01T00:00:00+00:00", time0);
		assertEquals(0.27, waveHei0);
		
		assertEquals(335.55, currDir1);
		assertEquals("2022-01-01T01:00:00+00:00", time1);
		assertEquals(0.33, waveHei1);

	}
	
	
	@Test
	@DisplayName("Test sul metodo 'buildForecast()' (fallimentare : causa eccezione per cui non viene trovato il JSONArray 'hours')")
	void test2() {
		
	    Exception exception = assertThrows(ForecastBuildingFailure.class, () -> {

			ForecastDataManager dataManager = new ForecastDataManager("00:00:00:00:00:00");
			f = dataManager.buildForecast(data.replace("hours", "hour"));

	    });

	    String expectedMessage = "'hours' JSONArray not found. Please retry.";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	@DisplayName("Test sul metodo 'buildForecast()' (fallimentare : causa eccezione per cui l'estrazione dei dati dalla sorgente 'sg' fallisce")
	void test3() {
		
	    Exception exception = assertThrows(ForecastBuildingFailure.class, () -> {
			
			ForecastDataManager dataManager = new ForecastDataManager("00:00:00:00:00:00");
			f = dataManager.buildForecast(data.replace("sg", "gg"));
			
	    });

	    String expectedMessage = "The extraction of the 'sg' source's data has gone wrong. Please retry.";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
}
