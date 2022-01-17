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
	public ForecastObject getForecastByRealTime(String macAddr) throws FilterFailure, VersionMismatch, ForecastBuildingFailure {
		
		ForecastDataManager dataManager = new ForecastDataManager(macAddr);
		Forecast f = dataManager.getForecast();

	    FilterForecastByTime forecastFilter = new FilterForecastByTime(f, Time.currentDateTime2());
	    forecastFilter.computeFilter();
		return forecastFilter.getFilteredData();
	}

	@Override
	public ForecastObject getForecastBySelectedTime(String macAddr, String date, byte hour) throws FilterFailure, VersionMismatch, ForecastBuildingFailure {
		
		ForecastDataManager dataManager = new ForecastDataManager(macAddr);
		Forecast f = dataManager.getForecast();

	    FilterForecastByTime forecastFilter = new FilterForecastByTime(f, date + "T" + String.format("%02d" , hour) + ":00:00+00:00");
	    forecastFilter.computeFilter();
		return forecastFilter.getFilteredData();
	}
	
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
		
		// prendo i valori delle stts e li metto in un JSONObject che me li raggruppa
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
		if(statsResults == null) throw new StatsFailure("Nessuna statistica computabile per questo dispositivo.");
		return statsResults;
	}

}



