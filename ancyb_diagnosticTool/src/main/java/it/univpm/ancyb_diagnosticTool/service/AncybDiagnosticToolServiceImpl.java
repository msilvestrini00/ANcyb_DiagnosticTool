package it.univpm.ancyb_diagnosticTool.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.datasim.DataSim;
import it.univpm.ancyb_diagnosticTool.filters.FilterByTime;
import it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolDataManager;
import it.univpm.ancyb_diagnosticTool.utilities.Time;


@Service
public class AncybDiagnosticToolServiceImpl implements AncybDiagnosticToolService {


	public ForecastObject getRealTimeForecast(double lat, double lng) {
		
		AncybDiagnosticToolDataManager dataManager = new AncybDiagnosticToolDataManager(lat, lng);
		DataSim dataSim = new DataSim();
		
		ArrayList<ForecastObject> forecastList = new ArrayList<ForecastObject>();
		Forecast forecast = new Forecast(forecastList);
		
		dataManager.buildURL();		
		dataManager.downloadJSONData();
		forecast = dataManager.buildForecast();

	    FilterByTime filter = new FilterByTime(forecast, Time.currentDateTime2());

		return filter.getFilteredForecastObject();
	}


	

}


//TODO implementa i metodi (es di stoccaggio dati)
