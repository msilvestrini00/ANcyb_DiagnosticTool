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
import it.univpm.ancyb_diagnosticTool.utilities.Time;
import it.univpm.ancyb_diagnosticTool.datasim.AncybFishDataSim;

public class AncybDiagnosticToolDataManager {

	
	private String macAddr;
	private double lat;
	private double lng;
	
	private String url;
	private String data;
	private String apiKey = "4380b6f80amshdae4ed371f74652p1857c6jsn3c4a8bf96244";
	private String uri = "https://stormglass.p.rapidapi.com/forecast?rapidapi-key=";

	public AncybDiagnosticToolDataManager(String macAddr) {
		
		AncybFishDataSim dataSim = new AncybFishDataSim();	// da levare dopo i test

		this.macAddr = macAddr;
		this.lat = dataSim.getDataSim(macAddr, Time.currentDateTime2()).getLat();
		this.lng = dataSim.getDataSim(macAddr, Time.currentDateTime2()).getLng();
	}

	public String getUrl() {
		return this.url;
	}
	
	public void buildUrl() {
		this.url = uri + apiKey + "&lat=" + this.lat + "&lng=" + this.lng;		//TODO in questi casi devo mettere il get o posso fare così?
	}
	
	
	public void downloadJSONData() {

		String jsonData = null;

		try {
			URLConnection openConnection = new URL(getUrl()).openConnection();
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
		this.data = jsonData;
	}

	
	public Forecast buildForecast() {
				  
		  //Defining the ArrayList used by the object 'Forecast'
		  ArrayList<ForecastObject> forecastList = new ArrayList<ForecastObject>();
		  
		  //Defining the object 'Forecast' that contains the ArrayList with all data
		  Forecast f = new Forecast(forecastList);
		
	      //Converting jsonData string into JSON object  
	      JSONObject jsnobject1 = new JSONObject(this.data);	
		
	      //Getting hours JSON array from the JSON object  
	      JSONArray hoursArray = jsnobject1.getJSONArray("hours"); 
		
	      //Estracting waveHeight and currentDirection JSON arrays and time JSON object from hours JSON array 
	      for(Object hoursElement : hoursArray) {
	      
	      //for (int i=0;i<hoursArray.length();i++){ 
	    
	    	  //Converting the hours JSON array to string
	    	  String hoursArrayElementString = hoursElement.toString();
	    	  
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
	          ForecastObject fobj = new ForecastObject(this.macAddr, this.lat, this.lng, 
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
