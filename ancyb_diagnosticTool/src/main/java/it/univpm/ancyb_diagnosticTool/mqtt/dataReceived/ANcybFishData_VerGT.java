package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

public class ANcybFishData_VerGT extends ANcybFishData_VerG {

	private float temp;

	public ANcybFishData_VerGT(String date, String time, String macAddr, String ver, float latitude, float longitude,
			String qualPos, float temp) {
		super(date, time, macAddr, ver, latitude, longitude, qualPos);
		
		this.temp = temp;
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}	
	
	public String toString() {
		
		String s = "Date " + getDate() + " " +
				   "Time " + getTime() + " " +
			       "Mac address " + getMacAddr() + " " +
				   "Version " + getVer() +  " " +
				   "Latitude " + getLatitude() + " " +
				   "Longitude " + getLongitude() + " " +
				   "Quality position " + getQualPos() + " " + 
				   "Temperature " + getTemp();
		
		return s;
		
	}
}
