package it.univpm.ancyb_diagnosticTool.service;


import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;

public interface AncybDiagnosticToolService {


	/**
	 * Metodo che riceve il mac address, crea l'oggetto forecast e restituisce 
	 * il ForecastObject corrente
	 * @param macAddr
	 * @return
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 */
	public ForecastObject getForecastByRealTime(String macAddr) throws FilterFailure, VersionMismatch;				
	
	/**
	 * Metodo che riceve il mac address, crea l'oggetto forecast e restituisce 
	 * il ForecastObject del tempo selezionato dall'utente
	 * @param macAddr
	 * @return
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 */
	public ForecastObject getForecastBySelectedTime(String macAddr, String date, byte hour) throws FilterFailure, VersionMismatch;				

	/**
	 * Metodo che restituisce un JSONObject contenente le stats
	 * @param macAddr
	 * @return
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 */
	public JSONObject getForecastStats(String macAddr, byte days) throws StatsFailure, VersionMismatch, FilterFailure;				

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

