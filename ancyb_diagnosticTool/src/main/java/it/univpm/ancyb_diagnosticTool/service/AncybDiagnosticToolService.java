package it.univpm.ancyb_diagnosticTool.service;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.univpm.ancyb_diagnosticTool.model.ForecastObject;

public interface AncybDiagnosticToolService {

	public abstract String receiveJSONData(double lat, double lng);				// riceve i dati e li mette in un'unica stringa, che restituisce
	//public abstract String cleanJSONData(String in);							// riceve la stringa pura di dati e la pulise, lasciando il JSONArray che contiene i dati utili sotto forma di stringa (che restituisce)
	//public abstract JSONArray stringToJSONArray(String s);						// riceve la stringa pulita e la converte in JSONArray, che restituisce				
	public abstract /*ArrayList<ForecastObject>*/ String createForecastList(String s);	// riceve il JSONArray e lo sistema nell'ArrayList (che restituisce)


}

