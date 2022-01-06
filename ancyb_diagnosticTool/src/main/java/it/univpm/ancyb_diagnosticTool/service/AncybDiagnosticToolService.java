package it.univpm.ancyb_diagnosticTool.service;


import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;

public interface AncybDiagnosticToolService {

	public abstract ForecastObject getRealTimeForecast(String macAddr) throws FilterFailure, VersionMismatch;				// riceve i dati e li mette in un'unica stringa, che restituisce
	//public abstract String cleanJSONData(String in);							// riceve la stringa pura di dati e la pulise, lasciando il JSONArray che contiene i dati utili sotto forma di stringa (che restituisce)
	//public abstract JSONArray stringToJSONArray(String s);						// riceve la stringa pulita e la converte in JSONArray, che restituisce				
	//public abstract /*ArrayList<ForecastObject>*/ String createForecastList(String s);	// riceve il JSONArray e lo sistema nell'ArrayList (che restituisce)

	public abstract ANcybFishData getRealTimePosition(String macAddr) throws VersionMismatch, FilterFailure;


}

