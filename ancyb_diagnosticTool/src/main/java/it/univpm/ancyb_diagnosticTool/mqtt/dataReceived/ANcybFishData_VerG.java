package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat;
import it.univpm.ancyb_diagnosticTool.utilities.CoordFormat;

public class ANcybFishData_VerG extends ANcybFishData {

	private String latitude;
	private String longitude;
	private String qualPos;
	
	public ANcybFishData_VerG(String date, String time, String macAddr, String ver, String latitude, String longitude, String qualPos) {
		super(date, time, macAddr, ver);
		this.latitude = latitude;
		this.longitude = longitude;
		this.qualPos = qualPos;
	}

	public String getLatitude() {
		return latitude;
	}
	
	/**
	 * TODO voglio fare che nella classe MqttDataReceived
	 * faccio checkIsLat con tutto il costrutto try catch e provo a creare
	 * la classe direttamente con il float
	 */
	public float getLatitudeFloat() {
		float lat;
		try {
			lat = CoordFormat.latGMSstringtoGDfloat(this.latitude);
			return lat;
		} catch (WrongCoordFormat e) {
			System.out.println("Errore nella latitudine");
			e.printStackTrace();
		}
		return 999999;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	/**
	 * TODO voglio fare che nella classe MqttDataReceived
	 * faccio checkIsLon con tutto il costrutto try catch e provo a creare
	 * la classe direttamente con il float
	 */
	public float getLongitudeFloat() {
		try {
			float lon = CoordFormat.lonGMSstringtoGDfloat(this.longitude);
			return lon;
		} catch (WrongCoordFormat e) {
			System.out.println("Errore nella longitudine");
			e.printStackTrace();
		}
		return 666666;
	}

	public void setLongitude(String longitude) {
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
				   "Time" + getTime() + " " +
			       "Mac address " + getMacAddr() + " " +
				   "Version " + getVer() +  " " +
				   "Latitude " + getLatitude() + " " +
				   "Longitude " + getLongitude() + " " +
				   "Longitude " + getQualPos();
		
		return s;
		
	}

}
