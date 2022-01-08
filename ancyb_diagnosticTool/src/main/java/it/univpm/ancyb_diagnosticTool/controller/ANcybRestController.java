package it.univpm.ancyb_diagnosticTool.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.filters.FilterForecastByTime;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;
import it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolService;
import it.univpm.ancyb_diagnosticTool.service.ForecastDataManager;
import it.univpm.ancyb_diagnosticTool.stats.AverageCurrentDirection;
import it.univpm.ancyb_diagnosticTool.stats.AverageWaveHeight;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

@RestController
public class ANcybRestController {
	@Autowired
	private AncybDiagnosticToolService a;
	private JSONObject j;
	private Forecast f;
	ArrayList<ANcybFishData> list;


	/**
	 * 
	 * Rotta che restituisce le previsioni meteo in base alla posizione in tempo reale del dispositivo,
	 * il quale mac è stato inserito come PathVariable
	 * 
	 */
	@RequestMapping(value = "/{macAddr}/forecast", method = RequestMethod.GET)
	public ResponseEntity<Object> getRealTimeForecast(@PathVariable("macAddr") String macAddr) {
		
		//TEST
		ANcybFishData ancybData1 = new ANcybFishData_VerGT(Time.currentDate(), Time.currentTime2(), "A4:cf:12:76:76:95", "Ver_GT", (float) 43.684017, (float) 13.354755, "3", 10.5f);
		//ANcybFishData ancybData2 = new ANcybFishData_VerG("2022.01.06", "18:25:52", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
		//ANcybFishData ancybData3 = new ANcybFishData_VerGT("2022.01.06", "18:26:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "NO_signal", 10.5f);
		//ANcybFishData ancybData4 = new ANcybFishData_VerG("2022.01.06", "18:27:38", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
		//ANcybFishData ancybData5 = new ANcybFishData_VerGT("2022.01.06", "18:28:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "5", 10.5f);
		//ANcybFishData ancybData6 = new ANcybFishData_VerG("2022.01.06", "18:29:38", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
		//ANcybFishData ancybData7 = new ANcybFishData_VerGT("2022.01.06", "18:30:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "NO_signal", 10.5f);

		try {
			
			f = a.getForecast(macAddr);
			j = null;
		
		    FilterForecastByTime filter = new FilterForecastByTime(f, Time.currentDateTime2());
			j = filter.getFilteredData().toJSON();

		} catch (FilterFailure | VersionMismatch e) {
			System.err.println("Exception" + e);
			return new ResponseEntity<>(j.toMap(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(j.toMap(), HttpStatus.OK);
		
	}
	
	
	/**
	 * 
	 * Rotta che restituisce le previsioni meteo in base all'orario inserito come parametro 
	 * (parametro 'date' in formato "yyy-mm-dd" e parametro 'hour' in formato numero intero, da 0 a 23)
	 *  e alla posizione del dispositivo, il quale mac è stato inserito come PathVariable
	 * 
	 */
	
	@RequestMapping(value = "/{macAddr}/forecast/filter", method = RequestMethod.POST)
	public ResponseEntity<Object> getSelectedTimeForecast(@PathVariable("macAddr") String macAddr, @RequestParam(name = "date") String date, 
																								   @RequestParam(name = "hour") int hour) {
		
		//TEST
		//ANcybFishData ancybData1 = new ANcybFishData_VerGT(Time.currentDate(), Time.currentTime2(), "A4:cf:12:76:76:95", "Ver_GT", 43.684017f, 13.354755f, "3", 10.5f);
		//ANcybFishData ancybData2 = new ANcybFishData_VerG("2022.01.06", "18:25:52", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
		ANcybFishData ancybData3 = new ANcybFishData_VerGT("2022.01.10", "18:26:38", "A4:cf:12:76:76:95", "Ver_GT", 43.670050f, 13.793283f, "NO_signal", 10.5f);
		//ANcybFishData ancybData4 = new ANcybFishData_VerG("2022.01.06", "18:27:38", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
		//ANcybFishData ancybData5 = new ANcybFishData_VerGT("2022.01.06", "18:28:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "5", 10.5f);
		//ANcybFishData ancybData6 = new ANcybFishData_VerG("2022.01.06", "18:29:38", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
		//ANcybFishData ancybData7 = new ANcybFishData_VerGT("2022.01.06", "18:30:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "NO_signal", 10.5f);

		try {
			
			f = a.getForecast(macAddr);
			j = null;
		
		    FilterForecastByTime filter = new FilterForecastByTime(f, date + "T" + hour + ":00:00+00:00");
		    filter.computeFilter();
			j = filter.getFilteredData().toJSON();

		} catch (FilterFailure | VersionMismatch e) {
			System.err.println("Exception" + e);
			return new ResponseEntity<>(j.toMap(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(j.toMap(), HttpStatus.OK);
		
	}
	
	
	/*
	 * 
	 * PER LA ROTTA FORECAST/STATS:
	 * - crei oggetti ANcybfishdata
	 * - usare j = a.getLastPositionByMac(macAddr).toJSON();
	 * - crei forecast
	 * - fai "new stats(forecast, giorni per cui fare la stats)
	 * - fai stats.computeStats();
	 * 
	 * 
	 * 
	 */
	
	@RequestMapping(value = "/{macAddr}/forecast/stats", method = RequestMethod.POST)
	public ResponseEntity<Object> getForecastStats(@PathVariable("macAddr") String macAddr, @RequestParam(name = "days") int days) {
			
			//TEST
			ANcybFishData ancybData1 = new ANcybFishData_VerGT(Time.currentDate(), Time.currentTime2(), "A4:cf:12:76:76:95", "Ver_GT", 43.684017f, 13.354755f, "3", 10.5f);
			//ANcybFishData ancybData2 = new ANcybFishData_VerG("2022.01.06", "18:25:52", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
			//ANcybFishData ancybData3 = new ANcybFishData_VerGT("2022.01.10", "18:26:38", "A4:cf:12:76:76:95", "Ver_GT", 43.670050f, 13.793283f, "NO_signal", 10.5f);
			//ANcybFishData ancybData4 = new ANcybFishData_VerG("2022.01.06", "18:27:38", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
			//ANcybFishData ancybData5 = new ANcybFishData_VerGT("2022.01.06", "18:28:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "5", 10.5f);
			//ANcybFishData ancybData6 = new ANcybFishData_VerG("2022.01.06", "18:29:38", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
			//ANcybFishData ancybData7 = new ANcybFishData_VerGT("2022.01.06", "18:30:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "NO_signal", 10.5f);

			try {
				
				f = a.getForecast(macAddr);
			
				
				//creo il JSONObject finale
				ForecastDataManager m = new ForecastDataManager(macAddr);
				j = m.createForecastStatsJSONObject(f, days);
				
				
			    AverageWaveHeight avgWaveHeight = new AverageWaveHeight(f, days);
			    avgWaveHeight.computeStats();
			    
			    AverageCurrentDirection avgCurrentDirection = new AverageCurrentDirection(f, days);
			    avgCurrentDirection.computeStats();

			    String wave = avgWaveHeight.getStats().getString("WaveHeight");
			    String curr = avgCurrentDirection.getStats().getString("CurrentDirection");

			    
			    j.put("waveHeight", wave );
			    j.put("currentDirection", curr);
				
			} catch (FilterFailure | StatsFailure | VersionMismatch e) {
				System.err.println("Exception" + e);
				return new ResponseEntity<>(j.toMap(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(j.toMap(), HttpStatus.OK);
			
		}
		
	
	
	
	
	
	/**
	 * Rotta che restituisce l'ultima istanza (e posizione) inviata dal dispositivo
	 * corrispondente al Mac address inserito come parametro.
	 * @param macAddr
	 * @return
	 */
	@RequestMapping(value = "/{macAddr}/device/filter/last", method = RequestMethod.GET)
	public ResponseEntity<Object> getLastPosition(@PathVariable("macAddr") String macAddr) {
		/*
		ANcybFishData ancybData1 = new ANcybFishData_VerGT(Time.currentDate(), Time.currentTime2(), "A4:cf:12:76:76:95", "Ver_GT", 43.684017f, 13.354755f, "3", 10.5f);
		ANcybFishData ancybData2 = new ANcybFishData_VerG("2022.01.06", "18:25:52", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
		ANcybFishData ancybData3 = new ANcybFishData_VerGT("2022.01.10", "18:26:38", "A4:cf:12:76:76:95", "Ver_GT", 43.670050f, 13.793283f, "NO_signal", 10.5f);
		ANcybFishData ancybData4 = new ANcybFishData_VerG("2022.01.06", "18:27:38", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
		ANcybFishData ancybData5 = new ANcybFishData_VerGT("2022.01.06", "18:28:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "5", 10.5f);
		ANcybFishData ancybData6 = new ANcybFishData_VerG("2022.01.06", "18:29:38", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
		ANcybFishData ancybData7 = new ANcybFishData_VerGT("2022.01.06", "18:30:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "NO_signal", 10.5f);
		*/
		j = null;
		try {
			j = a.getLatestPositionByMac(macAddr).toJSON();
			return new ResponseEntity<>(j.toMap(), HttpStatus.OK);
		} catch (VersionMismatch | FilterFailure e) {
			System.err.println("Exception: " + e);
			return new ResponseEntity<>(j.toMap(), HttpStatus.BAD_REQUEST);
		}
		
	}

	/**
	 * Rotta che restituisce tutte le istanze (e le posizioni) dei dati inviati dal 
	 * dispositivo corrispondente al Mac address inserito come parametro.
	 * @param macAddr
	 * @return
	 */
	@RequestMapping(value = "/{macAddr}/device/filter/all", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllPositions(@PathVariable("macAddr") String macAddr) {
	
		try {
			ArrayList<ANcybFishData> historyFishData = a.getAllPositionsByMac(macAddr);
			Collection<ANcybFishData> collANcyb = historyFishData;
			return new ResponseEntity<>( collANcyb, HttpStatus.OK);
		} catch (VersionMismatch | FilterFailure e) {
			System.err.println("Exception: " + e);
			return new ResponseEntity<>(j.toMap(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	/**
	 * Rotta che restituisce le statisticheo.
	 * @param macAddr
	 * @return
	 */
	@RequestMapping(value = "/{macAddr}/device/stats", method = RequestMethod.GET)
	public ResponseEntity<Object> getDeviceStats(@PathVariable("macAddr") String macAddr) {
		
		try {
			ArrayList<ANcybFishData> historyFishData = a.getAllPositionsByMac(macAddr);
			j = a.getFishStats(historyFishData);
			return new ResponseEntity<>( j.toMap() , HttpStatus.OK);
		} catch (VersionMismatch | FilterFailure e) {
			System.err.println("Exception: " + e);
			return new ResponseEntity<>(j.toMap(), HttpStatus.BAD_REQUEST);
		}
		
	}	

}



