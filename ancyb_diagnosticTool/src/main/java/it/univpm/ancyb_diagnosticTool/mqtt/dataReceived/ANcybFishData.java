package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.InvalidParameter;
import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;
import it.univpm.ancyb_diagnosticTool.utilities.CheckInputParameters;
import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

/**
 * 
 * @author Giacomo Fiara
 *
 */
public class ANcybFishData implements DataReceived {

	private String time;
	private String date;
	private String ver;
	private String macAddr;
	
	/**
	 * Costruttore di servizio
 	 * @param date
	 * @param time
	 * @param macAddr
	 * @param ver
	 */
	protected ANcybFishData(String date, String time, String macAddr, String ver) {
		this.date=date;
		this.time=time;
		this.ver=ver;
		this.macAddr=macAddr;
	}
	
	/**
	 * 
	 * @param strArr
	 * @throws MqttStringMismatch
	 * @throws InvalidParameter 
	 */
	protected ANcybFishData(String[] strArr) throws InvalidParameter {
		
		this.date = Time.currentDate();
		
		this.macAddr = strArr[0];
		CheckInputParameters.CheckMacAddr(this.macAddr);
		
		this.ver = strArr[1];
		
		this.time = strArr[2];
		CheckInputParameters.CheckTime(time);
		
	}
	
	@Override
	public String getDate() {
		return date;
	}
	
	@Override
	public void setDate(String date) {	
		this.date = date;
	}

	@Override
	public String getTime() {
		return time;
	}
	
	@Override
	public void setTime(String time) {
		this.time = time;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}
	
	public String toString() {
		
		String s = "Date " + getDate() + " " +
				   "Time" + getTime() + " " +
			       "Mac address " + getMacAddr() + " " +
				   "Version " + getVer() + "\n";
		
		return s;
		
	}

	public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        
        jo.put("Date", this.getDate());
        jo.put("Time", this.getTime());
        jo.put("Mac address", this.getMacAddr());
        jo.put("Version", this.getVer());
        
        return jo;
	}
}
