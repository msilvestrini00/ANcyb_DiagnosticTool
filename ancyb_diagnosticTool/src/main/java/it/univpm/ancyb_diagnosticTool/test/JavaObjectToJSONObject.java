package it.univpm.ancyb_diagnosticTool.test;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.model.ForecastObject;

public class JavaObjectToJSONObject {

	ForecastObject fobj = new ForecastObject("macAddr", 12, 34, "time", 56, 78);
	
	
    public JSONObject toJSON() {

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
