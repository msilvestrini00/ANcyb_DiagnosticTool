package it.univpm.ancyb_diagnosticTool.service;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;

public interface AncybDiagnosticToolService {

	/**
	 * Metodo che riceve il mac address, fa la chiamata all'API esterna
	 * relativa alla posizione e mette tutto in un oggetto Forecast, che restituisce
	 * @param macAddr
	 * @return
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 */
	//public abstract Forecast getForecast(String macAddr) throws FilterFailure, VersionMismatch;				
	
	/**
	 * Metodo che riceve il mac address, crea l'oggetto forecast e restituisce 
	 * il ForecastObject corrente
	 * @param macAddr
	 * @return
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 */
	public abstract ForecastObject getForecastByRealTime(String macAddr) throws FilterFailure, VersionMismatch;				
	
	/**
	 * Metodo che riceve il mac address, crea l'oggetto forecast e restituisce 
	 * il ForecastObject del tempo selezionato dall'utente
	 * @param macAddr
	 * @return
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 */
	public abstract ForecastObject getForecastBySelectedTime(String macAddr, String date, int hour) throws FilterFailure, VersionMismatch;				

	/**
	 * Metodo che restituisce un JSONObject contenente le stats
	 * @param macAddr
	 * @return
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 */
	public abstract JSONObject getForecastStats(String macAddr, int days) throws StatsFailure, VersionMismatch, FilterFailure;				

	/**
	 * 
	 * @param macAddr
	 * @return
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 */
	ArrayList<ANcybFishData> getAllResultsByMac(String macAddr) throws FilterFailure;

	/**
	 * 
	 * @param macAddr
	 * @return
	 * @throws VersionMismatch
	 * @throws FilterFailure
	 */
	ANcybFishData getLatestPositionByMac(String macAddr) throws VersionMismatch, FilterFailure;

	/**
	 * 
	 * @param historyFishData
	 * @return
	 * @throws StatsFailure 
	 * @throws JSONException 
	 * @throws VersionMismatch 
	 */
	public abstract JSONObject getFishStats(ArrayList<ANcybFishData> historyFishData) throws JSONException, StatsFailure, VersionMismatch;

}

