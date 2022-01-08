package it.univpm.ancyb_diagnosticTool.model;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;

public class ForecastObject implements DataReceived{

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
	
	public String getTime() { // formato hh:mm:ss
		return time.substring(10, 18);
	}

	public void setTime(String time) {
		
		String firsthalf = time.substring(0, 10);
		String secondhalf = time.substring(19, 25);

		this.time = firsthalf + time + secondhalf;
	}

	public String getDate() { // formato yyyy.mm.dd
		return time.substring(0,10).replace('-', '.');
	}

	public void setDate(String date) {
		
		String date2 = date.replace('.', '-');
		
		String firsthalf = time.substring(0, 10);
		String secondhalf = time.substring(19, 25);

		this.time = firsthalf + date2 + secondhalf;
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
