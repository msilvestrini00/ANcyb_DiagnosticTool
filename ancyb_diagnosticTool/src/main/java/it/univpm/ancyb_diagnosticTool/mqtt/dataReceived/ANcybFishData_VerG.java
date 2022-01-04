package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

public class ANcybFishData_VerG extends ANcybFishData {

	private float latitude;
	private float longitude;
	private String qualPos;
	
	public ANcybFishData_VerG(String date, String time, String macAddr, String ver, float latitude, float longitude, String qualPos) {
		super(date, time, macAddr, ver);
		this.latitude = latitude;
		this.longitude = longitude;
		this.qualPos = qualPos;
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

	public String getQualPos() {
		return qualPos;
	}

	public void setQualPos(String qualPos) {
		this.qualPos = qualPos;
	}
	
	public String toString() {
		
		String s = "Date " + getDate() + " " +
				   "Time " + getTime() + " " +
			       "Mac address " + getMacAddr() + " " +
				   "Version " + getVer() +  " " +
				   "Latitude " + getLatitude() + " " +
				   "Longitude " + getLongitude() + " " +
				   "Quality position " + getQualPos();
		
		return s;
		
	}

}
