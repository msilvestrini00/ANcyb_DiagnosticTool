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

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.URLIsNull;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.filters.FilterObjByMac;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.utilities.CheckVersion;

public class ForecastDataManager {

	
	private String macAddr;
	private float lat;
	private float lng;
	
	private String url = null;
	private String data;
	private String apiKey = "4380b6f80amshdae4ed371f74652p1857c6jsn3c4a8bf96244";
	private String uri = "https://stormglass.p.rapidapi.com/forecast?rapidapi-key=";

	public ForecastDataManager(String macAddr) throws FilterFailure, VersionMismatch {
		
		/**
		 * ROBA VECCHIA
		 */
		/*AncybFishDataSim dataSim = new AncybFishDataSim();	// da levare dopo i test
		this.macAddr = macAddr;
		this.lat = dataSim.getDataSim(macAddr, Time.currentDateTime2()).getLat();
		this.lng = dataSim.getDataSim(macAddr, Time.currentDateTime2()).getLng();
		*/
		
		
		/**
		 * ROBA NUOVA
		 */
		
		//TEST
		//AncybFishDataSim dataSim = new AncybFishDataSim();	

		
		FilterObjByMac filterFishData = new FilterObjByMac(macAddr);
		filterFishData.computeFilter();
		ANcybFishData fishData = filterFishData.getFilteredData();
		CheckVersion.verG(fishData);
		this.macAddr = macAddr;

		//TEST
		this.lat = ((ANcybFishData_VerG) fishData).getLatitude(); //dataSim.getDataSim(macAddr, Time.currentDateTime2()).getLat();
		this.lng = ((ANcybFishData_VerG) fishData).getLongitude(); //dataSim.getDataSim(macAddr, Time.currentDateTime2()).getLng();
	}

	public String getUrl() throws URLIsNull {	
		
		if(this.url == null) throw new URLIsNull("L'URL non è ancora stato creato. Costruire un URL prima di richiederlo.");
		
		return this.url;
	}
	
	public void buildUrl() {
		this.url = uri + apiKey + "&lat=" + this.lat + "&lng=" + this.lng;		
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
			
				while ((line = buf.readLine()) != null){	
					
					data += line;
				}
		}	
		finally {
			in.close();
		}
			jsonData = data;
		}
		catch(URLIsNull e) {
			e.printStackTrace();
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
	
  	public static float extractSgSourceFromJSONArray(JSONArray array) {	
  	  
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
