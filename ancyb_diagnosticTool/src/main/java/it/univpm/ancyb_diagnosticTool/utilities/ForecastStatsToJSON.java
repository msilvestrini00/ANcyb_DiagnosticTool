package it.univpm.ancyb_diagnosticTool.utilities;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.model.Forecast;

public class ForecastStatsToJSON {

	public JSONObject createForecastStatsJSONObject (Forecast forecast, int days) {
		
		
	        JSONObject jo = new JSONObject();
	        
	        jo.put("macAddress", forecast.getForecastMacAddr());
	        jo.put("Latitude", forecast.getForecastList(). ;
	        jo.put("Longitude", this.getLongitude());
	        jo.put("Time", this.getTime());
	        //jo.put("End", );

	        jo.put("WaveHeight", this.getWaveHeight());
	        jo.put("CurrentDirection", this.getCurrentDirection());
	        
	        return jo;
	    }

	}
}
