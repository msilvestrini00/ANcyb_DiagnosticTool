package it.univpm.ancyb_diagnosticTool.model;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;

public class ForecastObject implements DataReceived{

	String forecastTime;
	String macAddress;
	float waveHeight;
	float currentDirection;
	float latitude;
	float longitude;
	
	public ForecastObject(String macAddress, float latitude, float longitude, String forecastTime, float waveHeight, float currentDirection) {
		
		this.forecastTime = forecastTime;
		this.macAddress = macAddress;
		this.latitude = latitude;
		this.longitude = longitude;
		this.waveHeight = waveHeight;
		this.currentDirection = currentDirection;
		
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public String getForecastTime() {
		return forecastTime;
	}

	public void setForecastTime(String forecastTime) {
		this.forecastTime = forecastTime;
	}
	
	public String getTime() { // formato hh:mm:ss
		return forecastTime.substring(10, 18);
	}

	public void setTime(String time) {
		
		String firsthalf = time.substring(0, 10);
		String secondhalf = time.substring(19, 25);

		this.forecastTime = firsthalf + time + secondhalf;
	}

	public String getDate() { // formato yyyy.mm.dd
		return forecastTime.substring(0,10).replace('-', '.');
	}

	public void setDate(String date) {
		
		String date2 = date.replace('.', '-');
		
		String firsthalf = forecastTime.substring(0, 10);
		String secondhalf = forecastTime.substring(19, 25);

		this.forecastTime = firsthalf + date2 + secondhalf;
	}
	
	public float getWaveHeight() {
		return waveHeight;
	}

	public void setWaveHeight(float waveHeight) {
		this.waveHeight = waveHeight;
	}

	public float getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(float currentDirection) {
		this.currentDirection = currentDirection;
	}

	
    public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        
        jo.put("macAddress", this.getMacAddress());
        jo.put("Latitude", this.getLatitude());
        jo.put("Longitude", this.getLongitude());
        jo.put("Time", this.getForecastTime());
        jo.put("WaveHeight", this.getWaveHeight());
        jo.put("CurrentDirection", this.getCurrentDirection());
        
        return jo;
    }

	
    public String toString() {
    	
		return "macAddr: " 		  	  + this.getMacAddress() 		+ "\n" + 
			   "Latitude: " 		  + this.getLatitude() 	 		+ "\n" + 
			   "Longitude: " 		  + this.getLongitude()  		+ "\n" + 
			   "time: " 		  	  + this.getForecastTime() 		 		+ "\n" + 
			   "waveHeight: " 	  	  + this.getWaveHeight() 		+ "\n" + 
			   "currentDirection: "   + this.getCurrentDirection();
	}



	
}
