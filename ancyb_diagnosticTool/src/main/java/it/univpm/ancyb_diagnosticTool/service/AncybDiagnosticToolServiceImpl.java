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
import it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolDataManager;


@Service
public class AncybDiagnosticToolServiceImpl implements AncybDiagnosticToolService {


	public ForecastObject getRealTimeForecast() {
		
		AncybDiagnosticToolDataManager dataManager = new AncybDiagnosticToolDataManager();
		DataSim dataSim = new DataSim();

		String url = dataManager.buildURL(dataSim.getLat(), dataSim.getLng());
		String data = dataManager.downloadJSONData(url);
		Forecast f = dataManager.buildForecast(data);
		
		// TODO CAPIRE SE C'E' UN MDO PIU' CONVENIENTE PER FARLO
	}
	
	

}


//TODO implementa i metodi (es di stoccaggio dati)
