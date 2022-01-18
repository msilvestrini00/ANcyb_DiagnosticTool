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
import it.univpm.ancyb_diagnosticTool.Exception.ForecastBuildingFailure;
import it.univpm.ancyb_diagnosticTool.Exception.InvalidParameter;
import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;
import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolService;
import it.univpm.ancyb_diagnosticTool.utilities.CheckInputParameters;

/*
 * Classe che gestisce le chiamate effettuate al Server dall'utente.
 * Le rotte disponibili sono di due tipi: 
 * - le prime tre riguardano il servizio di previsione metereologica marittima (in particolare fornendo dati sull'altezza delle onde e la direzione della corrente)
 * - le altre tre sono rivolte alla diagnostica del dispositivo
 * 
 * @author Giacomo Fiara
 * @author Manuele Silvestrini
 */
@RestController
public class ANcybRestController {
	
	@Autowired
	/*
	 * Definizione dell'interfaccia della classe dei servizi,
	 * tramite la quale utilizzo i metodi principali dell'applicativo.
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolService
	 */
	private AncybDiagnosticToolService service;
	
	/*
	 * JSONObject generico utilizzato per veicolare l'output di ciascuna rotta 
	 */
	private JSONObject j;
	
	
	//TODO TEST
	String str1 = "a4:cf:12:76:76:95 Ver_G 16:05:45 4334.3060N 01335.1580E 1"; //VerG
	String str2 = "b4:cf:12:76:76:95 Ver_GT 16:05:50 4031.2360N 07401.2330W 1 10.5"; //VerGT

	ANcybDataManager ancybDataManager = new ANcybDataManager();
	ANcybFishData fishdata1;
	ANcybFishData fishdata2;
	ANcybFishData fishdata3;
	
	ArrayList<ANcybFishData> list;	
	
	/**
	 * 
	 * Rotta che restituisce la situazione meteorologica del dispositivo selezionato in tempo reale.
	 * 
	 * @param macAddr Indirizzo mac tramite il quale seleziono un dispositivo, ricavandone le coordinate. 
	 * @return Oggetto JSON sulla previsione oraria corrente in base alle coordinate ricavate.
	 * @throws InvalidParameter
	 * @throws VersionMismatch
	 * @throws FilterFailure
	 * @throws ForecastBuildingFailure
	 * @see it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolServiceImpl#getForecastByRealTime(String macAddr)
	 * @author Manuele Silvestrini
	 */
	@RequestMapping(value = "/{macAddr}/forecast", method = RequestMethod.GET)
	public ResponseEntity<Object> getRealTimeForecast(@PathVariable("macAddr") String macAddr) {
		
		//TODO TEST
		try {
			fishdata1 = ancybDataManager.createDataObj(str1);
			fishdata2 = ancybDataManager.createDataObj(str2);
		} catch (MqttStringMismatch e) {
			//System.err.println("Exception: " + e);
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		//
		
		try {	
			CheckInputParameters.CheckMacAddr(macAddr);
			j = service.getForecastByRealTime(macAddr).toJSON();

		} catch (InvalidParameter | VersionMismatch | FilterFailure  | ForecastBuildingFailure e) {
			System.err.println("Exception: " + e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);	
		}
		return new ResponseEntity<>(j.toMap(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * Rotta che restituisce le previsioni meteorologiche sulla posizione corrente del dispositivo selezionato, 
	 * in base alla data e l'orario inseriti come parametri.
	 * 
	 * @param date Parametro sulla data in formato "yyy-mm-dd" (sono disponibili previsioni fino a 9 giorni successivi).
	 * @param hour Parametro sull'ora in formato numero intero, da 0 a 23.
	 * @return Oggetto JSON sulla previsione oraria selezionata in base alle coordinate ricavate.
	 * @throws InvalidParameter
	 * @throws VersionMismatch
	 * @throws FilterFailure
	 * @throws ForecastBuildingFailure
	 * @see it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolServiceImpl#getForecastBySelectedTime(String macAddr)
	 * @author Manuele Silvestrini
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
			//System.err.println("Exception: " + e);
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		//
		
		try {
			CheckInputParameters.CheckForecastFilterParameters(macAddr, date, hour);
			j = service.getForecastBySelectedTime(macAddr, date, hour).toJSON();

		}catch (InvalidParameter | FilterFailure | VersionMismatch | ForecastBuildingFailure e) {
			System.err.println("Exception: " + e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		return new ResponseEntity<>(j.toMap(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * Rotta che restituisce i valori medi delle previsioni meteorologiche sulla posizione corrente del dispositivo selezionato, 
	 * dall'ora e il giorno corrente fino al numero dei giorni prossimi inserito come parametro.
	 * 
	 * @param days Parametro sul numero dei giorni per cui si vuole estendere la statistica (sono disponibili previsioni fino a 9 giorni successivi).
	 * @return Oggetto JSON sulle statistiche di previsione meteorologica in base alle coordinate ricavate.
	 * @throws InvalidParameter
	 * @throws VersionMismatch
	 * @throws FilterFailure
	 * @throws StatsFailure
	 * @throws ForecastBuildingFailure
	 * @see it.univpm.ancyb_diagnosticTool.service#AncybDiagnosticToolServiceImpl#getForecastStats(String macAddr, int days)
	 * 
	 */
	@RequestMapping(value = "/{macAddr}/forecast/stats", method = RequestMethod.POST)
	public ResponseEntity<Object> getForecastStatistics(@PathVariable("macAddr") String macAddr, @RequestParam(name = "days") byte days) {
		
		//TODO TEST
		try {
			fishdata1 = ancybDataManager.createDataObj(str1);
			fishdata2 = ancybDataManager.createDataObj(str2);
		} catch (MqttStringMismatch e) {
			//System.err.println("Exception: " + e);
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		//
		
		try {
			CheckInputParameters.CheckForecastStatsParameters(macAddr, days);
			j = service.getForecastStats(macAddr, days);	

		} catch (InvalidParameter | FilterFailure | StatsFailure | VersionMismatch | ForecastBuildingFailure e) {
			System.err.println("Exception: " + e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
		return new ResponseEntity<>(j.toMap(), HttpStatus.OK);
	}
		

	/**
	 * Rotta che restituisce l'ultima istanza (e posizione) inviata dal dispositivo
	 * corrispondente al Mac address inserito come parametro.
	 * @param macAddr
	 * @return
	 * @see it.univpm.ancyb_diagnosticTool.service#AncybDiagnosticToolServiceImpl#getLatestPostionByMac(String macAddr)
	 * 
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
			j = service.getLatestPositionByMac(macAddr).toJSON();
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
			ArrayList<ANcybFishData> historyFishData = service.getAllResultsByMac(macAddr);
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
			ArrayList<ANcybFishData> historyFishData = service.getAllResultsByMac(macAddr);
			j = service.getFishStats(historyFishData);
			return new ResponseEntity<>( j.toMap() , HttpStatus.OK);
		} catch (VersionMismatch | FilterFailure | JSONException | StatsFailure | InvalidParameter e) {
			System.err.println("Exception: " + e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception: " + e);
		}
	}	

}



