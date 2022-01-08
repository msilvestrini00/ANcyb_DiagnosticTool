package it.univpm.ancyb_diagnosticTool.service;


import java.util.ArrayList;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;

public interface AncybDiagnosticToolService {

	/**
	 * // riceve i dati e li mette in un'unica stringa, che restituisce
	 * @param macAddr
	 * @return
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 */
	public abstract Forecast getForecast(String macAddr) throws FilterFailure, VersionMismatch;				
	
	/**
	 * 
	 * @param macAddr
	 * @return
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 */
	ArrayList<ANcybFishData> getAllPositionsByMac(String macAddr) throws FilterFailure, VersionMismatch;

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
	 */
	public abstract JSONObject getFishStats(ArrayList<ANcybFishData> historyFishData);


}

