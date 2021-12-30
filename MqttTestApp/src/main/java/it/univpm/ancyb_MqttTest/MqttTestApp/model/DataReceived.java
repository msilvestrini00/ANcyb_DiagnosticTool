package it.univpm.ancyb_MqttTest.MqttTestApp.model;

public class DataReceived {

	private String mac;
	private String date;
	private String latitude;
	private String longitude;
	private String qualPos;

	public DataReceived(String mac, String date, String latitude, String longitude, String qualPos) {
	
		this.mac = mac;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
		this.qualPos = qualPos;
		
	}
	
	public DataReceived(String[] str) {
		
		try {
			this.mac = str[0];
			this.date = str[1];
			this.latitude = str[2];
			this.longitude = str[3];
			this.qualPos = str[4];
		}
		//TODO gestisci meglio questa eccezione
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("ERRRRRROOOOOOOOOOOORE!!");
			e.printStackTrace();
		}
		
	}
	
	
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
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
}
