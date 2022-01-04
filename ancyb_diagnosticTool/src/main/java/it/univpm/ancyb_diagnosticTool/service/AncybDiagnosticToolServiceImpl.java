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

import it.univpm.ancyb_diagnosticTool.model.ForecastObject;

@Service
public class AncybDiagnosticToolServiceImpl implements AncybDiagnosticToolService {

	private String apiKey = "4380b6f80amshdae4ed371f74652p1857c6jsn3c4a8bf96244";
	private String uri = "https://stormglass.p.rapidapi.com/forecast?rapidapi-key=";
	private String jsonData;
	

	@Override
	public String receiveJSONData(double lat, double lng) {

		String jsonData = null;

		try {
			URLConnection openConnection = new URL(uri + apiKey + "&lat=" + lat + "&lng=" + lng).openConnection();
			InputStream in = openConnection.getInputStream();
			
			String data = "";
			String line = "";
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
			
				while ((line = buf.readLine()) != null){	//TODO vedi se farlo meglio (in teoria è ricevuta una sola riga)
					
					data += line;
				}
		}	
		finally {
			in.close();
		}
		// TODO PARTE DI ELABORAZIONE E STOCCAGGIO DATI
		

			jsonData = data;
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return jsonData;
	}

	
	
	
	/*CONTROLLA*/
	/*
	public String createForecastList(String s) {

		return null;
	}
	*/	
}


//TODO implementa i metodi (es di stoccaggio dati)
