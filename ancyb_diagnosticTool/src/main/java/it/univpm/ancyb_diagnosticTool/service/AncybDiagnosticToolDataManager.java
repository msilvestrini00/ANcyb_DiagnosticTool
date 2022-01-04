package it.univpm.ancyb_diagnosticTool.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.datasim.DataSim;

public class AncybDiagnosticToolDataManager {

	private String apiKey = "4380b6f80amshdae4ed371f74652p1857c6jsn3c4a8bf96244";
	private String uri = "https://stormglass.p.rapidapi.com/forecast?rapidapi-key=";
	
	private double lat;
	private double lng;

	
	public AncybDiagnosticToolDataManager(double lat, double lng) {
		
		
		// TODO DEFINIRE UN OGGETTO?
		
	}
	
	
	
	public String buildURL(double lat, double lng) {

	return uri + apiKey + "&lat=" + lat + "&lng=" + lng;
	}
	
	
	public String downloadJSONData(String url) {

		String jsonData = null;

		try {
			URLConnection openConnection = new URL(url).openConnection();
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

	
	
	public Forecast buildForecast(String jsonData) {
		
		  //INIZIALIZZAZIONE DELL'OGGETTO SIMULAZIONE DATI
		  DataSim dataSim = new DataSim();
		  
		  //Defining the ArrayList used by the object 'Forecast'
		  ArrayList<ForecastObject> forecastList = new ArrayList<ForecastObject>();
		  
		  //Defining the object 'Forecast' that contains the ArrayList with all data
		  Forecast f = new Forecast(forecastList);
		
	      //Converting jsonData string into JSON object  
	      JSONObject jsnobject1 = new JSONObject(jsonData);	
		
	      //Getting hours JSON array from the JSON object  
	      JSONArray hoursArray = jsnobject1.getJSONArray("hours"); 
		
	      //Estracting waveHeight and currentDirection JSON arrays and time JSON object from hours JSON array 
	      for (int i=0;i<hoursArray.length();i++){ 
	      
	    	  //Converting the hours JSON array to string
	    	  String hoursArrayElementString = hoursArray.get(i).toString();
	    	  
	          //Converting the string to JSON object 
	          JSONObject jsnobject2 = new JSONObject(hoursArrayElementString); 
	          
	          //Getting the arrays
	          JSONArray waveHeightArray = jsnobject2.getJSONArray("waveHeight"); 
	          JSONArray currentDirectionArray = jsnobject2.getJSONArray("currentDirection"); 
	          
	          //Filtering the data source and getting the values
	          float waveHeight = extractSgSourceFromJSONArray(waveHeightArray);
	          float currentDirection = extractSgSourceFromJSONArray(currentDirectionArray);
	          
	          //Getting the time JSON object as string
	          String time = jsnobject2.getString("time"); 

	          //Defining a ForecastObject in which put all the data
	          ForecastObject fobj = new ForecastObject(dataSim.getMacAddr(), dataSim.getLat(), dataSim.getLng(), 
						   time, waveHeight, currentDirection);
	          
	          //Adding the created object in the ArrayList of the 'Forecast' object
	          f.addToForecast(fobj);
	          
	      }
	      return f;
	      
	}
	
  	public static float extractSgSourceFromJSONArray(JSONArray array) {	//TODO lasciarlo static?
  	  
  		float data = 0;
  
  		for(int j=0; j<array.length(); j++) {
  			JSONObject dataObject = new JSONObject();

  			dataObject = array.getJSONObject(j);
      
  			String s = dataObject.getString("source");
      
  			if(s.equals("sg")) {
  				data = dataObject.getFloat("value");
  			}
  			else continue;
      
  	}
  			
  	return data;		
  }
	
	
}
