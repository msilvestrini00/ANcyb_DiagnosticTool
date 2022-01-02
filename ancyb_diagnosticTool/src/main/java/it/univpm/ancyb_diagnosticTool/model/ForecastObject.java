package it.univpm.ancyb_diagnosticTool.model;

public class ForecastObject {

	String time;
	float waveHeight, currentDirection, latitude, longitude;
	
	public ForecastObject(String time, float waveHeight, float currentDirection, float latitude, float longitude) {
		
		this.time = time;
		this.waveHeight = waveHeight;
		this.currentDirection = currentDirection;
		this.latitude = latitude;
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
	
	// TODO definire l'overriding di tostring ForecastObject
	public String toString() {
		return "";
	}
	

	
}
