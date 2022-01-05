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


	double lat;
	double lng;
	
	public AncybDiagnosticToolServiceImpl() {}
	
	public AncybDiagnosticToolServiceImpl(double lat, double lng) {
		
		this.lat = lat;
		this.lng = lng;
	}

	
	public ForecastObject getRealTimeForecast() {
		
		AncybDiagnosticToolDataManager dataManager = new AncybDiagnosticToolDataManager(this.lat, this.lng);
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
