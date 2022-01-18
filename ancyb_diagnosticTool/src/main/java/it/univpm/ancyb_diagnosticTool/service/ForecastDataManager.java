package it.univpm.ancyb_diagnosticTool.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.ForecastBuildingFailure;
import it.univpm.ancyb_diagnosticTool.Exception.URLIsNull;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.filters.FilterObjByMac;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.utilities.CheckVersion;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

/*
 * <b>Classe</b> che si occupa di gestire le operazioni ad un livello più basso rispetto alla classe dei servizi, 
 * con riguardo alla chiamata all'API esterna. 
 * 
 * @author Manuele Silvestrini
 */
public class ForecastDataManager {

	/*
	 * Definizione degli attributi del costruttore della classe, utili per l'invocazione dei metodi relativi.
	 */
	private String macAddr;
	private float lat;
	private float lng;
	
	/*
	 * Definizione delle variabili legate alla chiamata all'API esterna.
	 */
	private String url = null;
	private String data;
	private String apiKey = "4380b6f80amshdae4ed371f74652p1857c6jsn3c4a8bf96244";
	private String forecastLink = "https://stormglass.p.rapidapi.com/forecast?rapidapi-key=";

	/*
	 * <b>Costruttore</b> della classe, che riceve l'indirizzo mac e in base ad esso setta le coordinate da utilizzare per la chiamata all'API esterna.
	 * @param macAddr Indirizzo mac del dispositivo di cui interessano le coordinate.
	 */
	public ForecastDataManager(String macAddr) throws FilterFailure, VersionMismatch {	

		FilterObjByMac filterFishData = new FilterObjByMac(macAddr);
		filterFishData.computeFilter();
		ANcybFishData fishData = filterFishData.getFilteredData();
		CheckVersion.verG(fishData);
		this.macAddr = macAddr;

		this.lat = ((ANcybFishData_VerG) fishData).getLatitude(); 
		this.lng = ((ANcybFishData_VerG) fishData).getLongitude(); 
	}

	/*
	 * <b>Metodo</b> complesso che implementa i metodi principali della classe.
	 * @return L'oggetto {@link it.univpm.ancyb_diagnosticTool.model.Forecast#Forecast(ArrayList) Forecast}
	 * con all'interno i dati ricevuti dalla chiamata all'API esterna.
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 * @throws ForecastBuildingFailure
	 */
	public Forecast getForecast() throws FilterFailure, VersionMismatch, ForecastBuildingFailure {
		
		Forecast forecast;
		
		this.buildUrl();		
		this.downloadJSONData();
		forecast = this.buildForecast(this.data);

		return forecast;
	}
	
	/*
	 * @return L'URL creato a seguito dell'invocazione del metodo {@link #getUrl()}.
	 * @throws URLIsNull
	 */
	public String getUrl() throws URLIsNull {	
		
		if(this.url == null) throw new URLIsNull("URLIsNull(getUrl) --> URL not yet created. Please, build the URL with method 'buildURL()' before claiming it.");
		return this.url;
	}
	
	/*
	 * <b>Metodo</b> che compone le stringhe necessarie per generare l'URL.
	 * 
	 * @see #getForecast()
	 */
	public void buildUrl() {
		this.url = forecastLink + apiKey + "&lat=" + this.lat + "&lng=" + this.lng;		
	}
	
	/*
	 * <b>Metodo</b> che si occupa di effettuare la chiamata all'API esterna per riceverne i dati e restituirli sotto forma di stringa.
	 * @return La stringa che contiene i dati ricevuti dalla chiamata all'API esterna.
	 * 
	 * @see #getForecast()
	 */
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

	/*
	 * <b>Metodo</b> che:</br>
	 * - riceve la stringa dei dati ricevuti dall'API esterna e la elabora</br>
	 * - ricava i dati di previsione utili</br>
	 * - inserisce i dati in dei 
	 * {@link it.univpm.ancyb_diagnosticTool.model.ForecastObject#ForecastObject(String, float, float, String, float, float) ForecastObject}
	 * che crea</br>
	 * - inserisce questi oggetti nell'ArrayList di un oggetto 
	 * {@link it.univpm.ancyb_diagnosticTool.model.Forecast#Forecast(ArrayList) Forecast}
	 * che crea.</br>
	 * 
	 * @param input Stringa contenente i dati ricevuti dall'API esterna.
	 * @return L'oggetto {@link it.univpm.ancyb_diagnosticTool.model.Forecast#Forecast(ArrayList) Forecast} contenente i dati utili.
	 * @throws ForecastBuildingFailure
	 * 
	 * @see #getForecast() 
	 */
	public Forecast buildForecast(String input) throws ForecastBuildingFailure{
				  
		  //Defining the ArrayList used by the object 'Forecast'
		  ArrayList<ForecastObject> forecastList = new ArrayList<ForecastObject>();
		  
		  //Defining the object 'Forecast' that contains the ArrayList with all data
		  Forecast f = new Forecast(forecastList);
		
	      //Converting jsonData string into JSON object  
	      JSONObject jsnobject1 = new JSONObject(input);	
		
	      //Getting hours JSON array from the JSON object  
	      JSONArray hoursArray = null;
	      
	      try { 
	      hoursArray= jsnobject1.getJSONArray("hours");
	      }
	      catch(JSONException e) {
	      throw new ForecastBuildingFailure("ForecastBuildingFailure(buildForecast) --> 'hours' JSONArray not found. Please retry.");
	      }
		
	      //Extracting waveHeight and currentDirection JSON arrays and time JSON object from hours JSON array 
	      for(Object hoursElement : hoursArray) {
	      	    
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
	          
	          if(waveHeight == 0.00 && currentDirection == 0.00) throw new ForecastBuildingFailure("ForecastBuildingFailure(buildForecast) --> the extraction of the 'sg' source's data has gone wrong. Please retry.");

	          //Getting the time JSON object as string
	          String time = jsnobject2.getString("time"); 

	          //Defining a ForecastObject in which put all the data
	          ForecastObject fobj = new ForecastObject(this.macAddr, this.lat, this.lng, time, waveHeight, currentDirection);
	          
	          //Adding the created object in the ArrayList of the 'Forecast' object
	          f.addToForecast(fobj);
	      }
	      
	      return f;
	}
	
	/*
	 * <b>Metodo</b> specifico usato all'interno di {@link #buildForecast(String)}, il quale ricava i dati dalla sorgente nominata 'sg'
	 * @param array L'array JSON contenente i dati provenienti da più sorgenti.
	 * @return Il valore relativo alla sorgente 'sg'.
	 * 
	 * @see #buildForecast(String)
	 */
  	private float extractSgSourceFromJSONArray(JSONArray array) {	
  	  
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
	
  	/*
  	 * <b>Metodo</b> Specifico utilizzato in {@link AncybDiagnosticToolServiceImpl#getForecastStats(String, byte)}
  	 * che si occupa di creare un oggetto JSON riepilogativo delle informazioni inserite, al momento della richiesta delle statistiche.
  	 * Questo oggetto sarà integrato in quello da fornire in uscita.
  	 * 
  	 * @param forecast L'oggetto {@link it.univpm.ancyb_diagnosticTool.model.Forecast#Forecast(ArrayList) Forecast} di cui si vogliono le informazioni.
  	 * @param days Il numero di giorni per cui si estende la statistica.
  	 * @return L'oggetto JSON riepilogativo.
  	 */
	public JSONObject createForecastStatsDataJSONObject (Forecast forecast, byte days) {
		
        JSONObject jo = new JSONObject();
        JSONObject out = new JSONObject();
        
        jo.put("macAddress", this.macAddr);
        jo.put("Latitude", this.lat);
        jo.put("Longitude", this.lng);
        jo.put("Time", Time.currentDateTime2());
        jo.put("End",  Time.currentDateTime2().substring(0, 8) + 
        			   String.format("%02d", Integer.parseInt(Time.currentDay())+days) + 
        			   Time.currentDateTime2().substring(10));

        out.put("Stats data", jo);
        
        return out;
    }

}

  	

