package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import org.json.JSONObject;

/**
 * 
 * @author Giacomo Fiara
 *
 */
public class ANcybFishData_VerG extends ANcybFishData {

	private float latitude;
	private float longitude;
	private String qualPos;
	
	//TODO per ora mi servono public, ma alla fine li metto protected in modo che non si possa accedere ai costruttori se non tramite ancybdatamanager
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
	
	public float[] getCoord() {
		float[] coord = {this.latitude, this.longitude};
		return coord;
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
				   "Quality position " + getQualPos() + "\n";
		
		return s;
		
	}
	
	public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        
        jo.put("Date", this.getDate());
        jo.put("Time", this.getTime());
        jo.put("Mac address", this.getMacAddr());
        jo.put("Version", this.getVer());
        jo.put("Latitude", this.getLatitude());
        jo.put("Longitude", this.getLongitude());
        jo.put("Quality position", this.getQualPos());
        
        return jo;
	}
}
