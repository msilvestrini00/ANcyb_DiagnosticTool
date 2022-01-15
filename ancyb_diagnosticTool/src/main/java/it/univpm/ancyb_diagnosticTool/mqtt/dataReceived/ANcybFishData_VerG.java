package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;
import it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat;
import it.univpm.ancyb_diagnosticTool.utilities.Coord;

/**
 * 
 * @author Giacomo Fiara
 *
 */
public class ANcybFishData_VerG extends ANcybFishData {

	private float latitude;
	private float longitude;
	private String qualPos;
	
	/**
	 * Costruttore di servizio
	 * @param date
	 * @param time
	 * @param macAddr
	 * @param ver
	 * @param latitude
	 * @param longitude
	 * @param qualPos
	 */
	//TODO per ora mi servono public, ma alla fine li metto protected in modo che non si possa accedere ai costruttori se non tramite ancybdatamanager
	public ANcybFishData_VerG(String date, String time, String macAddr, String ver, float latitude, float longitude, String qualPos) {
		super(date, time, macAddr, ver);
		this.latitude = latitude;
		this.longitude = longitude;
		this.qualPos = qualPos;
	}
	
	/**
	 * Costruttore utilizzato dalla classe "ANcybDataManager".
	 * @param strArr
	 * @throws MqttStringMismatch
	 */
	ANcybFishData_VerG(String[] strArr) throws MqttStringMismatch {
		
		super(strArr);
		
		String latitudeStr = strArr[3];
		try {
			Coord.checkIsLat(latitudeStr);
			this.latitude = Coord.latGMSstringToGDfloat(latitudeStr);
		} catch (WrongCoordFormat|NullPointerException|NumberFormatException e){
			throw new MqttStringMismatch("Stringa ricevuta non idonea. Causa -> latitudine");
		}
	
		String longitudeStr = strArr[4];
		try {
			Coord.checkIsLon(longitudeStr);
			this.longitude = Coord.lonGMSstringToGDfloat(longitudeStr);
		} catch (WrongCoordFormat|NullPointerException|NumberFormatException e){
			throw new MqttStringMismatch("Stringa ricevuta non idonea. Causa -> longitudine");
		}
		
		qualPos = strArr[5];
		
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
