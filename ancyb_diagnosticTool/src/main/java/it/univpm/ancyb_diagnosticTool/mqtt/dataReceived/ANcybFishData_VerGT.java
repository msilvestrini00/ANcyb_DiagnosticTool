package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import org.json.JSONObject;

public class ANcybFishData_VerGT extends ANcybFishData_VerG {

	private float temp;
	
	//TODO per ora mi servono public, ma alla fine li metto protected in modo che non si possa accedere ai costruttori se non tramite ancybdatamanager
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
				   "Temperature " + getTemp() + "\n";
		
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
        jo.put("Temperature", this.getTemp());
        
        return jo;
	}
}
