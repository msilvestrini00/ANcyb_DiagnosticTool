package it.univpm.ancyb_diagnosticTool.test;

public class SimpleJavaObject {

	private String time;
	private double waveHeight;
	private double currentDirection;

	
	public SimpleJavaObject(String time, double waveHeight, double currentDirection) {
	
		this.time = time;
		this.waveHeight = waveHeight;
		this.currentDirection = currentDirection;
	}

	   public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public double getWaveHeight() {
		return waveHeight;
	}



	public void setWaveHeight(double waveHeight) {
		this.waveHeight = waveHeight;
	}



	public double getCurrentDirection() {
		return currentDirection;
	}



	public void setCurrentDirection(double currentDirection) {
		this.currentDirection = currentDirection;
	}


/*
	public String printArrayList() {
	        return "number: " + this.getNumber() + "\n" +
	               "name: " + this.getName();
	   }
	*/
	
}
