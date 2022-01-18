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
import it.univpm.ancyb_diagnosticTool.stats.AverageWaveHeight;
import it.univpm.ancyb_diagnosticTool.stats.GeodeticDistance;
import it.univpm.ancyb_diagnosticTool.stats.AverageTemperatureFish;
import it.univpm.ancyb_diagnosticTool.utilities.CheckVersion;
import it.univpm.ancyb_diagnosticTool.utilities.IsVersion;
import it.univpm.ancyb_diagnosticTool.utilities.Time;
 
/*
 * <b>Classe</b> di servizio per gestire le operazioni sulle previsioni orarie e i dati ricevuti dal dispositivo.
 * Viene implementata l'apposita interfaccia {@link it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolService}.
 * 
 * @implements AncybDiagnosticToolService
 * @see it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolService AncybDiagnosticToolService
 * 
 * @author Fiara Giacomo
 * @author Silvestrini Manuele
 */
@Service
public class AncybDiagnosticToolServiceImpl implements AncybDiagnosticToolService {

	/*
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
	 * @param macAddr Indirizzo mac dal quale ricavo le coordinate per l'API esterna.
	 * @return Il ForecastObject relativo alla data e l'ora correnti.
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 * @throws ForecastBuildingFailure
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
	 * @param macAddr Indirizzo mac dal quale ricavo le coordinate per l'API esterna.
	 * @param date La data di cui si vuole ottenere la previsione.
	 * @param hour L'ora di cui si vuole ottenere la previsione.
	 * @return Il ForecastObject riguardante la data e l'ora inserite.
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 * @throws ForecastBuildingFailure
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
	 * @param macAddr Indirizzo mac dal quale ricavo le coordinate per l'API esterna.
	 * @param days Numero di giorni per cui si vuole estendere le statistiche (a partire dal giorno corrente).
	 * @return L'oggetto JSON che contiene le statistiche.
	 * @throws StatsFailure
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 * @throws ForecastBuildingFailure
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
	 * <b>Metodo</b> che
	 * 
	 * 
	 */
	@Override
	public ANcybFishData getLatestPositionByMac(String macAddr) throws VersionMismatch, FilterFailure {
		FilterObjByMac filterFishData = new FilterObjByMac(macAddr);
		filterFishData.computeFilter();
		ANcybFishData fishData = filterFishData.getFilteredData();
		CheckVersion.verG(fishData);
		return fishData;
	}

	/**
	 * 
	 */
	@Override
	public ArrayList<ANcybFishData> getAllResultsByMac(String macAddr) throws FilterFailure {
		FilterListByMac filterFishData = new FilterListByMac(macAddr);
		filterFishData.computeFilter();
		ArrayList<ANcybFishData> fishData = filterFishData.getFilteredData();
		return fishData;
	}

	/**
	 * 
	 */
	@Override
	public JSONObject getFishStats(ArrayList<ANcybFishData> historyFishData) throws JSONException, StatsFailure, VersionMismatch {
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
		return statsResults;
	}

}



