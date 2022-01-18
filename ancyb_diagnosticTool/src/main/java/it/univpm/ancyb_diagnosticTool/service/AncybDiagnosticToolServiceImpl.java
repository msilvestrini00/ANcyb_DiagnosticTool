package it.univpm.ancyb_diagnosticTool.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.ForecastBuildingFailure;
import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.filters.FilterForecastByTime;
import it.univpm.ancyb_diagnosticTool.filters.FilterListByMac;
import it.univpm.ancyb_diagnosticTool.filters.FilterObjByMac;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.stats.AverageCurrentDirection;
import it.univpm.ancyb_diagnosticTool.stats.AverageTemperatureFish;
import it.univpm.ancyb_diagnosticTool.stats.AverageWaveHeight;
import it.univpm.ancyb_diagnosticTool.stats.GeodeticDistance;
import it.univpm.ancyb_diagnosticTool.utilities.IsVersion;
import it.univpm.ancyb_diagnosticTool.utilities.Time;
 
/**
 * <b>Classe</b> di servizio per gestire le operazioni sulle previsioni orarie e i dati ricevuti dal dispositivo.
 * Viene implementata l'apposita interfaccia {@link it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolService}.
 * 
 * @implements AncybDiagnosticToolService
 * @see it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolService AncybDiagnosticToolService
 * 
 * @author Giacomo Fiara
 * @author Manuele Silvestrini
 */
@Service
public class AncybDiagnosticToolServiceImpl implements AncybDiagnosticToolService {

	/**
	 * Oggetto JSON utilizzato per veicolare in uscita le statistiche create, senza creare più istanze inutilmente.
	 */
	private JSONObject statsResults;

	/**
	 * <b>Metodo</b> che:</br>
	 * - riceve l'indirizzo mac</br>
	 * - chiama L'API esterna</br>
	 * - crea l'oggetto {@link it.univpm.ancyb_diagnosticTool.model.Forecast#Forecast(ArrayList) Forecast} coi dati ricevuti</br>
	 * - filtra i ForecastObject in base alla data e l'ora correnti</br>
	 * - restituisce il {@link it.univpm.ancyb_diagnosticTool.model.ForecastObject#ForecastObject(String, float, float, String, float, float) ForecastObject} trovato.
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.controller.ANcybRestController#getRealTimeForecast(String macAddr)
	 * 
	 * @author Manuele Silvestrini
	 */
	@Override
	public ForecastObject getForecastByRealTime(String macAddr) throws FilterFailure, VersionMismatch, ForecastBuildingFailure {
		
		ForecastDataManager dataManager = new ForecastDataManager(macAddr);
		Forecast f = dataManager.getForecast();

	    FilterForecastByTime forecastFilter = new FilterForecastByTime(f, Time.currentDateTime2());
	    forecastFilter.computeFilter();
		return forecastFilter.getFilteredData();
	}

	/**
	 * <b>Metodo</b> che:</br>
	 * - riceve l'indirizzo mac</br>
	 * - chiama L'API esterna</br>
	 * - crea l'oggetto {@link it.univpm.ancyb_diagnosticTool.model.Forecast#Forecast(ArrayList) Forecast} coi dati ricevuti</br>
	 * - filtra i ForecastObject in base alla data e l'ora inserite</br>
	 * - restituisce il {@link it.univpm.ancyb_diagnosticTool.model.ForecastObject#ForecastObject(String, float, float, String, float, float) ForecastObject} trovato.
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.controller.ANcybRestController#getSelectedTimeForecast(String macAddr, String date, byte hour)
	 * 
	 * @author Manuele Silvestrini
	 */
	@Override
	public ForecastObject getForecastBySelectedTime(String macAddr, String date, byte hour) throws FilterFailure, VersionMismatch, ForecastBuildingFailure {
		
		ForecastDataManager dataManager = new ForecastDataManager(macAddr);
		Forecast f = dataManager.getForecast();

	    FilterForecastByTime forecastFilter = new FilterForecastByTime(f, date + "T" + String.format("%02d" , hour) + ":00:00+00:00");
	    forecastFilter.computeFilter();
		return forecastFilter.getFilteredData();
	}
	
	/**
	 * <b>Metodo</b> che:</br>
	 * - riceve l'indirizzo mac e i giorni per cui si estendono le statistiche</br>
	 * - chiama L'API esterna</br>
	 * - crea l'oggetto {@link it.univpm.ancyb_diagnosticTool.model.Forecast#Forecast(ArrayList) Forecast} coi dati ricevuti</br>
	 * - esegue le statistiche sulle previsioni</br>
	 * - crea un oggetto JSON completo di tutte le informazioni</br>
	 * - restituisce l'oggetto JSON.
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.controller.ANcybRestController#getSelectedTimeForecast(String macAddr, String date, byte hour)
	 * 
	 * @author Manuele Silvestrini
	 */
	@Override
	public JSONObject getForecastStats(String macAddr, byte days) throws StatsFailure, VersionMismatch, FilterFailure, ForecastBuildingFailure {
		
		//inizializzo gli elementi che mi servono
		JSONObject statsValueObject = new JSONObject();
		JSONObject statsValueObject2 = new JSONObject();
		JSONArray statsResultsArray = new JSONArray();
		JSONObject output = new JSONObject();	

		//creo l'oggetto coi dati e lo metto nell'array finale
		ForecastDataManager dataManager = new ForecastDataManager(macAddr);
		Forecast f = dataManager.getForecast();
		statsResultsArray.put(dataManager.createForecastStatsDataJSONObject(f, days));
		
		// prendo i valori delle stats e li metto in un JSONObject che me li raggruppa
	    AverageWaveHeight avgWaveHeight = new AverageWaveHeight(f, days);
	    avgWaveHeight.computeStats();
	    statsValueObject.put("Wave Height", avgWaveHeight.getStats());
		
	    AverageCurrentDirection avgCurrentDirection = new AverageCurrentDirection(f, days);
	    avgCurrentDirection.computeStats();
	    statsValueObject.put("Current Direction", avgCurrentDirection.getStats());
		
	    //metto l'oggetto coi dati delle stats in un oggetto
	    statsValueObject2.put("Stats values", statsValueObject);
	    
	    //metto l'oggetto creato nell'array finale
	    statsResultsArray.put(statsValueObject2);

	    //metto l'array in un oggetto per darlo in uscita
	    output.put("Stats", statsResultsArray);
	    		
		return output;
	}

	/**
	 * <b>Metodo</b> che:</br>
	 * - riceve l'indirizzo Mac associato al dispositivo di cui si vuol ottenere l'ultima istanza tra tutti i dati inviati durante la sessione;</br>
	 * - viene filtrato il Mac address e il risultato restituito rappresenta l'istanza desiderata.</br>
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.filters.FilterObjByMac FilterObjByMac
	 * 
	 * @author Giacomo Fiara
	 */
	@Override
	public ANcybFishData getLatestResultByMac(String macAddr) throws FilterFailure {
		FilterObjByMac filterFishData = new FilterObjByMac(macAddr);
		filterFishData.computeFilter();
		ANcybFishData fishData = filterFishData.getFilteredData();
		return fishData;
	}

	/**
	 * <b>Metodo</b> che:</br>
	 * - riceve l'indirizzo Mac associato al dispositivo di cui si vuol ottenere tutte le istanze dei dati inviati durante la sessione;</br>
	 * - viene filtrato il Mac address e il risultato restituito è l'ArrayList delle istanze desiderate.</br>
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.filters.FilterListByMac FilterListByMac
	 * 
	 * @author Giacomo Fiara
	 */
	@Override
	public ArrayList<ANcybFishData> getAllResultsByMac(String macAddr) throws FilterFailure {
		FilterListByMac filterFishData = new FilterListByMac(macAddr);
		filterFishData.computeFilter();
		ArrayList<ANcybFishData> fishData = filterFishData.getFilteredData();
		return fishData;
	}

	/**
	 * <b>Metodo</b> che:</br>
	 * - riceve l'indirizzo Mac associato al dispositivo di cui si vuol ottenere le statiche basate sui relativi dati inviati durante la sessione;</br>
	 * - viene evocato il metodo {@link it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolServiceImpl#getAllResultsByMac(String) getAllResultsByMac(String macAddr)}
	 * che restituisce l'Arraylist di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} relativo al Mac address desiderato;</br>
	 * - vengono eseguite le statistiche in base alla versione del device;
	 * - viene restituito il metadato contenente le varie statistiche.</br>
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolServiceImpl#getAllResultsByMac(String) getAllResultsByMac(String)
	 * @see it.univpm.ancyb_diagnosticTool.stats.GeodeticsDistance GeodeticsDistance
	 * @see it.univpm.ancyb_diagnosticTool.stats.AverageTemperatureFish AverageTemperatureFish
	 *
	 * @author Giacomo Fiara
	 */
	@Override
	public JSONObject getFishStats(String macAddr) throws JSONException, StatsFailure, FilterFailure {
		
		ArrayList<ANcybFishData> historyFishData  = new ArrayList<>();
		historyFishData = getAllResultsByMac(macAddr);
		JSONObject metadata = new JSONObject();
		metadata.put("Mac address", macAddr);
		statsResults = new JSONObject();
		if(IsVersion.verG(historyFishData)) {
			GeodeticDistance geodeticDistance = new GeodeticDistance(historyFishData);
			geodeticDistance.computeStats();
			statsResults.put("Geodetic distance", geodeticDistance.getStats());
		}
		if(IsVersion.verGT(historyFishData)) {
			AverageTemperatureFish averageTemperatureFish = new AverageTemperatureFish(historyFishData);
			averageTemperatureFish.computeStats();
			statsResults.put("Average temperature", averageTemperatureFish.getStats());
		}
		if(statsResults == null) throw new StatsFailure("StatsFaiure(getFishStats) --> No stats to compute for this device.");
		metadata.put("Stats results", statsResults);
		return metadata;
	}

}



