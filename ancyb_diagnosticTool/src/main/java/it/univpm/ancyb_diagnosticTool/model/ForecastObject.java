package it.univpm.ancyb_diagnosticTool.model;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;

public class ForecastObject implements DataReceived{

	private String forecastTime;
	private String macAddress;
	private float waveHeight;
	private float currentDirection;
	private float latitude;
	private float longitude;
	
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

	public void setForecastTime(String forecastTime) {	// formato yyyy-MM-dd.HH:00:00+00:00
		this.forecastTime = forecastTime;
	}
	
	public String getTime() { // formato hh:mm:ss
		return forecastTime.substring(10, 18);
	}

	public void setTime(String time) {
		
		String firstHalf = forecastTime.substring(0, 10);
		String secondHalf = forecastTime.substring(19, 25);

		this.forecastTime = firstHalf + time + secondHalf;
	}

	public String getDate() { // formato yyyy.mm.dd
		return forecastTime.substring(0,10).replace('-', '.');
	}

	public void setDate(String date) {
		
		String date2 = date.replace('.', '-');
		
		String firstHalf = forecastTime.substring(0, 10);
		String secondHalf = forecastTime.substring(19, 25);

		this.forecastTime = firstHalf + date2 + secondHalf;
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
			   "time: " 		  	  + this.getForecastTime() 		+ "\n" + 
			   "waveHeight: " 	  	  + this.getWaveHeight() 		+ "\n" + 
			   "currentDirection: "   + this.getCurrentDirection();
	}

}
