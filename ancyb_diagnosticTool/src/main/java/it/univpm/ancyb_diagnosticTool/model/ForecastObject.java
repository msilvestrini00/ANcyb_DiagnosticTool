package it.univpm.ancyb_diagnosticTool.model;

import org.json.JSONObject;

public class ForecastObject {

	String time;
	String macAddress;
	float waveHeight;
	float currentDirection;
	float latitude;
	float longitude;
	
	public ForecastObject(String macAddress, float latitude, float longitude, String time, float waveHeight, float currentDirection) {
		
		this.macAddress = macAddress;
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
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
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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
        jo.put("Time", this.getTime());
        jo.put("WaveHeight", this.getWaveHeight());
        jo.put("CurrentDirection", this.getCurrentDirection());
        
        return jo;
    }

	
    public String toString() {
    	
		return "macAddr: " 		  	  + this.getMacAddress() 		+ "\n" + 
			   "Latitude: " 		  + this.getLatitude() 	 		+ "\n" + 
			   "Longitude: " 		  + this.getLongitude()  		+ "\n" + 
			   "time: " 		  	  + this.getTime() 		 		+ "\n" + 
			   "waveHeight: " 	  	  + this.getWaveHeight() 		+ "\n" + 
			   "currentDirection: "   + this.getCurrentDirection();
	}
	

	
}
