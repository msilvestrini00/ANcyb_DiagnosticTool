package it.univpm.ancyb_diagnosticTool.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
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
import it.univpm.ancyb_diagnosticTool.stats.StatsGPSData;
import it.univpm.ancyb_diagnosticTool.stats.StatsTempData;
import it.univpm.ancyb_diagnosticTool.utilities.CheckVersion;
import it.univpm.ancyb_diagnosticTool.utilities.IsVersion;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

/**
 * 
 * @author Giacomo Fiara, Manuele Silvestrini
 *
 */
@Service
public class AncybDiagnosticToolServiceImpl implements AncybDiagnosticToolService {

	

	private JSONObject statsResults;

	/*
	 * METODO NUOVO (senza filtro e eccezione relativa, esportati in ancybRestController)
	 */

	//TODO i concetti di filtri e stats costruiti in questo modo vanno bene?

	@Override
	public ForecastObject getForecastByRealTime(String macAddr) throws FilterFailure, VersionMismatch {
		
		ForecastDataManager dataManager = new ForecastDataManager(macAddr);
		Forecast f = dataManager.getForecast();

	    FilterForecastByTime forecastFilter = new FilterForecastByTime(f, Time.currentDateTime2());
	    forecastFilter.computeFilter();
		return forecastFilter.getFilteredData();
	}




	@Override
	public ForecastObject getForecastBySelectedTime(String macAddr, String date, int hour)
			throws FilterFailure, VersionMismatch {
		
		ForecastDataManager dataManager = new ForecastDataManager(macAddr);
		Forecast f = dataManager.getForecast();

	    FilterForecastByTime forecastFilter = new FilterForecastByTime(f, date + "T" + String.format("%02d" , hour) + ":00:00+00:00");
	    forecastFilter.computeFilter();
		return forecastFilter.getFilteredData();
	}
	




	@Override
	public JSONObject getForecastStats(String macAddr, int days) throws StatsFailure, VersionMismatch, FilterFailure {
		
		//inizializzo gli elementi che mi servono
		JSONArray statsValueArray = new JSONArray();
		JSONObject statsValueObject = new JSONObject();
		JSONArray statsResultsArray = new JSONArray();
		JSONObject output = new JSONObject();	//TODO capire perchè se uso statsResults per far ritornare l'oggetto mi da che è null

		
		//creo l'oggetto coi dati e lo metto nell'array finale
		ForecastDataManager dataManager = new ForecastDataManager(macAddr);
		Forecast f = dataManager.getForecast();
		
		statsResultsArray.put(dataManager.createForecastStatsDataJSONObject(f, days));
		
		// prendo i valori delle stts e li metto in un rray che me li raggruppa
	    AverageWaveHeight avgWaveHeight = new AverageWaveHeight(f, days);
	    avgWaveHeight.computeStats();
	    statsValueArray.put(avgWaveHeight.getStats());
		
	    AverageCurrentDirection avgCurrentDirection = new AverageCurrentDirection(f, days);
	    avgCurrentDirection.computeStats();
	    statsValueArray.put(avgCurrentDirection.getStats());
		
	    //metto l'array dei valori in un oggetto per poterlo nominare
	    statsValueObject.put("Stats values", statsValueArray);
	    
	    statsResultsArray.put(statsValueObject);

	    //metto quest'ultimo oggetto nell'array finale
	    output.put("Stats", statsResultsArray);

		if(output == null) throw new StatsFailure("Nessuna statistica computabile per questo dispositivo.");
		
		return output;
	}




	/**
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
	public ArrayList<ANcybFishData> getAllPositionsByMac(String macAddr) throws FilterFailure, VersionMismatch {
		FilterListByMac filterFishData = new FilterListByMac(macAddr);
		filterFishData.computeFilter();
		ArrayList<ANcybFishData> fishData = filterFishData.getFilteredData();
		CheckVersion.verG(fishData);
		return fishData;
	}

	/**
	 * 
	 */
	@Override
	public JSONObject getFishStats(ArrayList<ANcybFishData> historyFishData) throws JSONException, StatsFailure, VersionMismatch {
		statsResults = new JSONObject();
		if(IsVersion.verG(historyFishData)) {
			StatsGPSData statsGPSData = new StatsGPSData(historyFishData);
			statsGPSData.computeStats();
			statsResults.put("GPS stats", statsGPSData.getStats());
		}
		if(IsVersion.verGT(historyFishData)) {
			StatsTempData statsTempData = new StatsTempData(historyFishData);
			statsTempData.computeStats();
			statsResults.put("Temperature stats", statsTempData.getStats());
		}
		if(statsResults == null) throw new StatsFailure("Nessuna statistica computabile per questo dispositivo.");
		return statsResults;
	}




}



