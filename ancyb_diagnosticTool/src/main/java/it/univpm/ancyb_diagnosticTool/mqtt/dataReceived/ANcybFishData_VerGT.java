package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

public class ANcybFishData_VerGT extends ANcybFishData_VerG {

	private String temp;

	public ANcybFishData_VerGT(String date, String time, String macAddr, String ver, String latitude, String longitude,
			String qualPos, String temp) {
		super(date, time, macAddr, ver, latitude, longitude, qualPos);
		
		this.temp = temp;
	}

	public String getTemp() {
		return temp;
	}
	
	/**
	 * TODO voglio fare che nella classe MqttDataReceived
	 * faccio checkIsTemp con tutto il costrutto try catch e provo a creare
	 * la classe direttamente con il float
	 */
	public float getTempFloat() {
		float temp;
		try {
			temp = Integer.parseInt(this.temp);
			return temp;
		}
		catch (NumberFormatException e1){
			System.out.println("Errore nella conversione in float della temperatura");
			System.out.println("Exception: " + e1);
			e1.printStackTrace();
        }catch(NullPointerException e2){
        	System.out.println("Errore nella conversione in float della temperatura");
			System.out.println("Exception: " + e2);
			e2.printStackTrace();
		}
		return 999999;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}	
	
	public String toString() {
		
		String s = "Date " + getDate() + " " +
				   "Time" + getTime() + " " +
			       "Mac address " + getMacAddr() + " " +
				   "Version " + getVer() +  " " +
				   "Latitude " + getLatitude() + " " +
				   "Longitude " + getLongitude() + " " +
				   "Longitude " + getQualPos() + " " + 
				   "Temperature" + getTemp();
		
		return s;
		
	}
}
