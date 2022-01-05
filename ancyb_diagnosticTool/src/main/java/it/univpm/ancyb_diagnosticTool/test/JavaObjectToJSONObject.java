package it.univpm.ancyb_diagnosticTool.test;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.model.ForecastObject;

public class JavaObjectToJSONObject {
	public static void main(String[] args){  
		  
	  
		ForecastObject j = new ForecastObject("macAddr", 12, 34, "time", 56, 78);
		
		toJSON(j);
	
	}

	public static JSONObject toJSON(ForecastObject fobj) {

		JSONObject jo = new JSONObject();
    
		jo.put("macAddress", fobj.getMacAddress());
		jo.put("Latitude", fobj.getLatitude());
		jo.put("Longitude", fobj.getLongitude());
		jo.put("Time", fobj.getTime());
		jo.put("WaveHeight", fobj.getWaveHeight());
		jo.put("CurrentDirection", fobj.getCurrentDirection());

		System.out.println(fobj.toJSON().toString());
    
		return jo;
	}
	
}
