package it.univpm.ancyb_diagnosticTool.service;


import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;

public interface AncybDiagnosticToolService {

	public abstract Forecast getForecast(String macAddr) throws FilterFailure, VersionMismatch;				// riceve i dati e li mette in un'unica stringa, che restituisce

	ANcybFishData getLastPositionByMac(String macAddr) throws VersionMismatch, FilterFailure;

	ArrayList<ANcybFishData> getAllPositionsByMac(String macAddr) throws FilterFailure, VersionMismatch;


}

