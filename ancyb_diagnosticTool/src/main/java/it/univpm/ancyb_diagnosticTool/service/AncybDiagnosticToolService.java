package it.univpm.ancyb_diagnosticTool.service;

import org.json.simple.JSONObject;

public interface AncybDiagnosticToolService {

	public abstract JSONObject getJSONForecast(String time, float lat, float lng);
	
	
}

//TODO inserisci i nomi dei metodi di service