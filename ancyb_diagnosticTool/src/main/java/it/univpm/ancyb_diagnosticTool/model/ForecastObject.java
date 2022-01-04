package it.univpm.ancyb_diagnosticTool.model;

public class ForecastObject {

	String time;
	String macAddress;
	float waveHeight;
	float currentDirection;
	double latitude;
	double longitude;
	
	public ForecastObject(String macAddress, double latitude, double longitude, String time, float waveHeight, float currentDirection) {
		
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
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
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



	

	// TODO definire l'overriding di tostring ForecastObject
	public String toString() {
		return "macAddr: " 		  	  + this.getMacAddress() 		+ "\n" + 
			   "Latitude: " 		  + this.getLatitude() 	 		+ "\n" + 
			   "Longitude: " 		  + this.getLongitude()  		+ "\n" + 
			   "time: " 		  	  + this.getTime() 		 		+ "\n" + 
			   "waveHeight: " 	  	  + this.getWaveHeight() 		+ "\n" + 
			   "currentDirection: "   + this.getCurrentDirection();
	}
	

	
}
