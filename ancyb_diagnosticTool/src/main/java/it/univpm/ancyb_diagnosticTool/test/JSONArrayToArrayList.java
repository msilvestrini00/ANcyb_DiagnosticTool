package it.univpm.ancyb_diagnosticTool.test;

import java.util.ArrayList;  
import org.json.JSONArray;  
import org.json.JSONObject;  

//Creating JSONArrayToArrayList class  
public class JSONArrayToArrayList {  
  public static void main(String[] args){  
      //Creating string of JSON data   
	  
      String jsonData = "{\"hours\":[{\"airTemperature\":[{\"source\":\"sg\",\"value\":9.74},{\"source\":\"noaa\",\"value\":7.32},{\"source\":\"dwd\",\"value\":9.74}],\"cloudCover\":[{\"source\":\"sg\",\"value\":2.09},{\"source\":\"noaa\",\"value\":17.8},{\"source\":\"dwd\",\"value\":2.09}],\"currentDirection\":[{\"source\":\"sg\",\"value\":171.87},{\"source\":\"meto\",\"value\":171.87}],\"currentSpeed\":[{\"source\":\"sg\",\"value\":0.01},{\"source\":\"meto\",\"value\":0.01}],\"gust\":[{\"source\":\"sg\",\"value\":2.85},{\"source\":\"noaa\",\"value\":1.72},{\"source\":\"dwd\",\"value\":2.85}],\"humidity\":[{\"source\":\"sg\",\"value\":82.26},{\"source\":\"noaa\",\"value\":88.4},{\"source\":\"dwd\",\"value\":82.26}],\"precipitation\":[{\"source\":\"sg\",\"value\":0.0},{\"source\":\"noaa\",\"value\":0.0},{\"source\":\"dwd\",\"value\":0.0}],\"pressure\":[{\"source\":\"sg\",\"value\":1023.17},{\"source\":\"noaa\",\"value\":1022.71},{\"source\":\"dwd\",\"value\":1023.17}],\"seaLevel\":[{\"source\":\"sg\",\"value\":0.28},{\"source\":\"meto\",\"value\":-0.4}],\"swellDirection\":[{\"source\":\"sg\",\"value\":323.29},{\"source\":\"noaa\",\"value\":329.53},{\"source\":\"dwd\",\"value\":321.56},{\"source\":\"icon\",\"value\":320.88},{\"source\":\"meteo\",\"value\":323.29}],\"swellHeight\":[{\"source\":\"sg\",\"value\":0.1},{\"source\":\"noaa\",\"value\":0.06},{\"source\":\"dwd\",\"value\":0.02},{\"source\":\"icon\",\"value\":0.05},{\"source\":\"meteo\",\"value\":0.1}],\"swellPeriod\":[{\"source\":\"sg\",\"value\":2.48},{\"source\":\"noaa\",\"value\":2.43},{\"source\":\"dwd\",\"value\":1.69},{\"source\":\"icon\",\"value\":1.89},{\"source\":\"meteo\",\"value\":2.48}],\"time\":\"2022-01-03T00:00:00+00:00\",\"visibility\":[{\"source\":\"sg\",\"value\":24.13},{\"source\":\"noaa\",\"value\":24.13}],\"waterTemperature\":[{\"source\":\"sg\",\"value\":13.51},{\"source\":\"noaa\",\"value\":5.32},{\"source\":\"meto\",\"value\":13.51}],\"waveDirection\":[{\"source\":\"sg\",\"value\":322.18},{\"source\":\"noaa\",\"value\":273.45},{\"source\":\"icon\",\"value\":320.88},{\"source\":\"meteo\",\"value\":322.18}],\"waveHeight\":[{\"source\":\"sg\",\"value\":0.1},{\"source\":\"noaa\",\"value\":0.11},{\"source\":\"dwd\",\"value\":0.02},{\"source\":\"icon\",\"value\":0.05},{\"source\":\"meteo\",\"value\":0.1}],\"wavePeriod\":[{\"source\":\"sg\",\"value\":2.43},{\"source\":\"noaa\",\"value\":2.02},{\"source\":\"icon\",\"value\":1.89},{\"source\":\"meteo\",\"value\":2.43}],\"windDirection\":[{\"source\":\"sg\",\"value\":180.0},{\"source\":\"noaa\",\"value\":206.76},{\"source\":\"icon\",\"value\":180.0}],\"windSpeed\":[{\"source\":\"sg\",\"value\":1.9},{\"source\":\"noaa\",\"value\":1.86},{\"source\":\"icon\",\"value\":1.9}],\"windWaveDirection\":[{\"source\":\"sg\",\"value\":223.67},{\"source\":\"noaa\",\"value\":214.04},{\"source\":\"dwd\",\"value\":180.0},{\"source\":\"icon\",\"value\":180.0},{\"source\":\"meteo\",\"value\":223.67}],\"windWaveHeight\":[{\"source\":\"sg\",\"value\":0.02},{\"source\":\"noaa\",\"value\":0.14},{\"source\":\"dwd\",\"value\":0.0},{\"source\":\"icon\",\"value\":0.0},{\"source\":\"meteo\",\"value\":0.02}],\"windWavePeriod\":[{\"source\":\"sg\",\"value\":1.45},{\"source\":\"noaa\",\"value\":1.7},{\"source\":\"dwd\",\"value\":1.0},{\"source\":\"icon\",\"value\":1.0},{\"source\":\"meteo\",\"value\":1.45}]},"
    		  					  + "{\"airTemperature\":[{\"source\":\"sg\",\"value\":9.98},{\"source\":\"noaa\",\"value\":7.11},{\"source\":\"dwd\",\"value\":9.98}],\"cloudCover\":[{\"source\":\"sg\",\"value\":2.98},{\"source\":\"noaa\",\"value\":42.03},{\"source\":\"dwd\",\"value\":2.98}],\"currentDirection\":[{\"source\":\"sg\",\"value\":171.87},{\"source\":\"meto\",\"value\":171.87}],\"currentSpeed\":[{\"source\":\"sg\",\"value\":0.01},{\"source\":\"meto\",\"value\":0.01}],\"gust\":[{\"source\":\"sg\",\"value\":2.84},{\"source\":\"noaa\",\"value\":1.68},{\"source\":\"dwd\",\"value\":2.84}],\"humidity\":[{\"source\":\"sg\",\"value\":81.99},{\"source\":\"noaa\",\"value\":88.23},{\"source\":\"dwd\",\"value\":81.99}],\"precipitation\":[{\"source\":\"sg\",\"value\":0.0},{\"source\":\"noaa\",\"value\":0.0},{\"source\":\"dwd\",\"value\":0.0}],\"pressure\":[{\"source\":\"sg\",\"value\":1022.75},{\"source\":\"noaa\",\"value\":1022.3},{\"source\":\"dwd\",\"value\":1022.75}],\"seaLevel\":[{\"source\":\"sg\",\"value\":0.3},{\"source\":\"meto\",\"value\":-0.4}],\"swellDirection\":[{\"source\":\"sg\",\"value\":323.6},{\"source\":\"noaa\",\"value\":331.5},{\"source\":\"dwd\",\"value\":321.12},{\"source\":\"icon\",\"value\":320.63},{\"source\":\"meteo\",\"value\":323.6}],\"swellHeight\":[{\"source\":\"sg\",\"value\":0.11},{\"source\":\"noaa\",\"value\":0.06},{\"source\":\"dwd\",\"value\":0.02},{\"source\":\"icon\",\"value\":0.05},{\"source\":\"meteo\",\"value\":0.11}],\"swellPeriod\":[{\"source\":\"sg\",\"value\":2.51},{\"source\":\"noaa\",\"value\":2.38},{\"source\":\"dwd\",\"value\":1.7},{\"source\":\"icon\",\"value\":1.92},{\"source\":\"meteo\",\"value\":2.51}],\"time\":\"2022-01-03T01:00:00+00:00\",\"visibility\":[{\"source\":\"sg\",\"value\":24.13},{\"source\":\"noaa\",\"value\":24.13}],\"waterTemperature\":[{\"source\":\"sg\",\"value\":13.51},{\"source\":\"noaa\",\"value\":5.1},{\"source\":\"meto\",\"value\":13.51}],\"waveDirection\":[{\"source\":\"sg\",\"value\":295.2},{\"source\":\"noaa\",\"value\":248.14},{\"source\":\"icon\",\"value\":320.63},{\"source\":\"meteo\",\"value\":295.2}],\"waveHeight\":[{\"source\":\"sg\",\"value\":0.16},{\"source\":\"noaa\",\"value\":0.11},{\"source\":\"dwd\",\"value\":0.02},{\"source\":\"icon\",\"value\":0.05},{\"source\":\"meteo\",\"value\":0.16}],\"wavePeriod\":[{\"source\":\"sg\",\"value\":2.26},{\"source\":\"noaa\",\"value\":1.9},{\"source\":\"icon\",\"value\":1.92},{\"source\":\"meteo\",\"value\":2.26}],\"windDirection\":[{\"source\":\"sg\",\"value\":180.0},{\"source\":\"noaa\",\"value\":209.61},{\"source\":\"icon\",\"value\":180.0}],\"windSpeed\":[{\"source\":\"sg\",\"value\":1.67},{\"source\":\"noaa\",\"value\":1.81},{\"source\":\"icon\",\"value\":1.67}],\"windWaveDirection\":[{\"source\":\"sg\",\"value\":221.86},{\"source\":\"noaa\",\"value\":207.84},{\"source\":\"dwd\",\"value\":180.0},{\"source\":\"icon\",\"value\":180.0},{\"source\":\"meteo\",\"value\":221.86}],\"windWaveHeight\":[{\"source\":\"sg\",\"value\":0.08},{\"source\":\"noaa\",\"value\":0.13},{\"source\":\"dwd\",\"value\":0.0},{\"source\":\"icon\",\"value\":0.0},{\"source\":\"meteo\",\"value\":0.08}],\"windWavePeriod\":[{\"source\":\"sg\",\"value\":1.59},{\"source\":\"noaa\",\"value\":1.69},{\"source\":\"dwd\",\"value\":1.0},{\"source\":\"icon\",\"value\":1.0},{\"source\":\"meteo\",\"value\":1.59}]}],"
      				   + "\"meta\":{\"end\":\"2022-01-13 00:00\",\"lat\":43.574998,\"lng\":13.492686,"
      				   + "\"params\":[\"waterTemperature\",\"wavePeriod\",\"waveDirection\",\"waveDirection\",\"waveHeight\",\"windWaveDirection\",\"windWaveHeight\",\"windWavePeriod\",\"swellPeriod\",\"swellDirection\",\"swellHeight\",\"windSpeed\",\"windDirection\",\"airTemperature\",\"precipitation\",\"gust\",\"cloudCover\",\"humidity\",\"pressure\",\"visibility\",\"seaLevel\",\"currentSpeed\",\"currentDirection\"],"
      				   + "\"start\":\"2022-01-03 00:00\"}}";  
      				   
      
      //Converting jsonData string into JSON object  
      JSONObject jsnobject1 = new JSONObject(jsonData);  
      //Printing JSON object  
      System.out.println("JSON Object 1");  
      System.out.println(jsnobject1);  
      //Getting languages JSON array from the JSON object  
      JSONArray hoursArray = jsnobject1.getJSONArray("hours"); 
      
      System.out.println("");

      //Printing hoursArray  
      System.out.println("hoursArray");  
      System.out.println(hoursArray);
        
      System.out.println("");
      System.out.println("");

      
      for (int i=0;i<hoursArray.length();i++){ 
    	
    	  String hoursArrayElementsString = hoursArray.get(i).toString();
    	  
          //Converting jsonData string into JSON object  
          JSONObject jsnobject2 = new JSONObject(hoursArrayElementsString);  
          //Printing JSON object  
          System.out.println("JSON Object 2." + i);  
          System.out.println(jsnobject2);  
          //Getting languages JSON array from the JSON object  
          JSONArray waveHeightArray = jsnobject2.getJSONArray("waveHeight"); 
          JSONArray currentDirectionArray = jsnobject2.getJSONArray("currentDirection"); 

          System.out.println("");
          
          //Printing hoursArray  
          System.out.println("waveHeightArray");  
          System.out.println(waveHeightArray);
          
          System.out.println("currentDirectionArray");  
          System.out.println(currentDirectionArray);
          
          System.out.println("");
          System.out.println("");

          
          /*FILTRAGGIO SOURCE PER WAVEHEIGHTARRAY*/
          
          float waveHeight = 0;
          
          for(int j=0; j<waveHeightArray.length(); j++) {
          JSONObject waveHeightObject = new JSONObject();

          waveHeightObject = waveHeightArray.getJSONObject(j);
              
              String s = waveHeightObject.getString("source");
              
              if(s.equals("sg")) {
            	  waveHeight = waveHeightObject.getFloat("value");
              }
              else break;
              
          }
          System.out.println("waveHeight: " + waveHeight);
          
          
          
          /*FILTRAGGIO SOURCE PER CURRENTDIRECTIONARRAY*/
          
          float currentDirection = 0;
          
          for(int k=0; k<currentDirectionArray.length(); k++) {
          JSONObject currentDirectionObject = new JSONObject();

          currentDirectionObject = currentDirectionArray.getJSONObject(k);
              
              String s = currentDirectionObject.getString("source");
              
              if(s.equals("sg")) {
            	  currentDirection = currentDirectionObject.getFloat("value");
              }
              else break;
              
          }
          System.out.println("currentDirection: " + currentDirection);
          
          
      }
      
      //TODO fai tutti i metodi necessari per snellire il processo
      //TODO implementa il tutto nell'applicazione
  
  	}
}
