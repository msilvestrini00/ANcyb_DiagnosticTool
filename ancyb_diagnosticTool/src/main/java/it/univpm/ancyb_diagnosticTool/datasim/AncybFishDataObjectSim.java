package it.univpm.ancyb_diagnosticTool.datasim;

public class AncybFishDataObjectSim {

	
	String macAddr; //= "a4:cf:12:76:76:95";
	String time; // = "2022-01-05T00:00:00+00:00";	//TODO fai una stringa di conversione del tempo tra me e jack? (in caso da mettere in "utils")
	double lat; //= 43.574998;
	double lng; // = 13.492686;	
	int temp;	// = 15,6;
	
	public AncybFishDataObjectSim(String macAddr, String time, double lat, double lng, int temp) {
		
		this.macAddr = macAddr;
		this.time = time;
		this.lat = lat;
		this.lng = lng;
		this.temp = temp;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}
	

	


}
