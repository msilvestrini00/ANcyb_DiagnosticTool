package it.univpm.ancyb_diagnosticTool.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.InvalidParameter;
import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;
import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolService;
import it.univpm.ancyb_diagnosticTool.utilities.CheckInputParameters;

@RestController
public class ANcybRestController {
	@Autowired
	//TODO rinominalo con "service" o un qualcosa di simile
	private AncybDiagnosticToolService a;
	private JSONObject j;
	
	
	//TODO TEST
	String str1 = "a4:cf:12:76:76:95 Ver_G 16:05:45 4334.3060N 1335.1580E 1"; //VerG
	String str2 = "b4:cf:12:76:76:95 Ver_GT 16:05:50 4031.2360N 7401.2330W 1 10.5"; //VerGT
	ANcybDataManager ancybDataManager = new ANcybDataManager();
	ANcybFishData fishdata1;
	ANcybFishData fishdata2;


	
	
	ArrayList<ANcybFishData> list;	
	
	/**
	 * 
	 * Rotta che restituisce le previsioni meteo in base alla posizione in tempo reale del dispositivo,
	 * il quale mac è stato inserito come PathVariable
	 * @throws MqttStringMismatch 
	 * 
	 */
	@RequestMapping(value = "/{macAddr}/forecast", method = RequestMethod.GET)
	public ResponseEntity<Object> getRealTimeForecast(@PathVariable("macAddr") String macAddr) {
		
		//TODO TEST
		try {
			fishdata1 = ancybDataManager.createDataObj(str1);
			fishdata2 = ancybDataManager.createDataObj(str2);
		} catch (MqttStringMismatch e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		
		try {	
			CheckInputParameters.CheckMacAddr(macAddr);
		
			j = a.getForecastByRealTime(macAddr).toJSON();
			return new ResponseEntity<>(j.toMap(), HttpStatus.OK);

		} catch (InvalidParameter | FilterFailure | VersionMismatch e) {
			System.err.println("Exception: " + e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);	
		}
		
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
																								   @RequestParam(name = "hour") byte hour) {

		//TODO TEST
		try {
			fishdata1 = ancybDataManager.createDataObj(str1);
			fishdata2 = ancybDataManager.createDataObj(str2);
		} catch (MqttStringMismatch e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		
		
		try {
			CheckInputParameters.CheckForecastFilterParameters(macAddr, date, hour);
			
			j = a.getForecastBySelectedTime(macAddr, date, hour).toJSON();
			return new ResponseEntity<>(j.toMap(), HttpStatus.OK);

		}catch (InvalidParameter | FilterFailure | VersionMismatch e) {
			System.err.println("Exception: " + e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		
	}
	
	
	@RequestMapping(value = "/{macAddr}/forecast/stats", method = RequestMethod.POST)
	public ResponseEntity<Object> getForecastStatistics(@PathVariable("macAddr") String macAddr, @RequestParam(name = "days") byte days) {
		
		//TODO TEST
		try {
			fishdata1 = ancybDataManager.createDataObj(str1);
			fishdata2 = ancybDataManager.createDataObj(str2);
		} catch (MqttStringMismatch e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		
		try {
			CheckInputParameters.CheckForecastStatsParameters(macAddr, days);
			
			j = a.getForecastStats(macAddr, days);	
			return new ResponseEntity<>(j.toMap(), HttpStatus.OK);

		} catch (InvalidParameter | FilterFailure | StatsFailure | VersionMismatch e) {
			System.err.println("Exception: " + e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
			
	}
		

	/**
	 * Rotta che restituisce l'ultima istanza (e posizione) inviata dal dispositivo
	 * corrispondente al Mac address inserito come parametro.
	 * @param macAddr
	 * @return
	 */
	@RequestMapping(value = "/{macAddr}/device/filter/last", method = RequestMethod.GET)
	public ResponseEntity<Object> getLastData(@PathVariable("macAddr") String macAddr) {
				
		//TODO TEST
		try {
			fishdata1 = ancybDataManager.createDataObj(str1);
			fishdata2 = ancybDataManager.createDataObj(str2);
		} catch (MqttStringMismatch e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		
		j = null;
		try {
			CheckInputParameters.CheckMacAddr(macAddr);
			j = a.getLatestPositionByMac(macAddr).toJSON();
			return new ResponseEntity<>(j.toMap(), HttpStatus.OK);
		} catch (VersionMismatch | FilterFailure | InvalidParameter e) {
			System.err.println("Exception: " + e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		
	}

	/**
	 * Rotta che restituisce tutte le istanze (e le posizioni) dei dati inviati dal 
	 * dispositivo corrispondente al Mac address inserito come parametro.
	 * @param macAddr
	 * @return
	 */
	@RequestMapping(value = "/{macAddr}/device/filter/all", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllData(@PathVariable("macAddr") String macAddr) {
		
		//TODO TEST
		try {
			fishdata1 = ancybDataManager.createDataObj(str1);
			fishdata2 = ancybDataManager.createDataObj(str2);
		} catch (MqttStringMismatch e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		
		try {
			CheckInputParameters.CheckMacAddr(macAddr);
			ArrayList<ANcybFishData> historyFishData = a.getAllResultsByMac(macAddr);
			Collection<ANcybFishData> collANcyb = historyFishData;
			return new ResponseEntity<>( collANcyb, HttpStatus.OK);
		} catch (FilterFailure | InvalidParameter e) {
			System.err.println("Exception: " + e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		
	}
	
	/**
	 * Rotta che restituisce tutte le statistiche disponibili per il dispositivo 
	 * corrispondente al Mac address inserito come parametro.
	 * @param macAddr
	 * @return
	 */
	@RequestMapping(value = "/{macAddr}/device/stats", method = RequestMethod.GET)
	public ResponseEntity<Object> getDeviceStats(@PathVariable("macAddr") String macAddr) {
				
		//TODO TEST
		try {
			fishdata1 = ancybDataManager.createDataObj(str1);
			fishdata2 = ancybDataManager.createDataObj(str2);
		} catch (MqttStringMismatch e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
				
		try {
			CheckInputParameters.CheckMacAddr(macAddr);
			ArrayList<ANcybFishData> historyFishData = a.getAllResultsByMac(macAddr);
			j = a.getFishStats(historyFishData);
			return new ResponseEntity<>( j.toMap() , HttpStatus.OK);
		} catch (VersionMismatch | FilterFailure | JSONException | StatsFailure | InvalidParameter e) {
			System.err.println("Exception: " + e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
	}	

}



