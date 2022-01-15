package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;

public class ANcybFishData_VerGT extends ANcybFishData_VerG {

	private float temp;
	
	/**
	 * Costruttore di servizio
	 * @param date
	 * @param time
	 * @param macAddr
	 * @param ver
	 * @param latitude
	 * @param longitude
	 * @param qualPos
	 * @param temp
	 */
	//TODO per ora mi servono public, ma alla fine li metto protected in modo che non si possa accedere ai costruttori se non tramite ancybdatamanager
	public ANcybFishData_VerGT(String date, String time, String macAddr, String ver, float latitude, float longitude,
			String qualPos, float temp) {
		super(date, time, macAddr, ver, latitude, longitude, qualPos);
		
		this.temp = temp;
	}
	
	/**
	 * 
	 * @param strArr
	 * @throws MqttStringMismatch
	 */
	ANcybFishData_VerGT(String[] strArr) throws MqttStringMismatch {
		
		super(strArr);
		
		String temperatureStr = strArr[6];
		try {
			this.temp = Float.parseFloat(temperatureStr);
		} catch (NullPointerException|NumberFormatException e){
			throw new MqttStringMismatch("Stringa ricevuta non idonea. Causa -> temperatura");
		}
		
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
