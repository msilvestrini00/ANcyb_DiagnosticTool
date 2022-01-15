package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import java.util.ArrayList;

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
	//TODO lascia se non implementi DataSaved altrimenti togli pure
	public static ArrayList<ANcybFishData> list = new ArrayList<ANcybFishData>();

	
	/**
	 * Costruttore di servizio
 	 * @param date
	 * @param time
	 * @param macAddr
	 * @param ver
	 */
	//TODO per ora mi servono public, ma alla fine li metto protected in modo che non si possa accedere ai costruttori se non tramite ancybdatamanager
	public ANcybFishData(String date, String time, String macAddr, String ver) {
		this.date=date;
		this.time=time;
		this.ver=ver;
		this.macAddr=macAddr;
		list.add(this);
	}
	
	/**
	 * 
	 * @param strArr
	 * @throws MqttStringMismatch
	 */
	ANcybFishData(String[] strArr) throws MqttStringMismatch {
		
		this.date = Time.currentDate();
		
		this.macAddr = strArr[0];
		try {
			CheckInputParameters.CheckMacAddr(this.macAddr);
		} catch (InvalidParameter e) {
			throw new MqttStringMismatch("Stringa ricevuta non idonea. Causa -> Mac Address");
		}
		
		this.ver = strArr[1];
		
		this.time = strArr[2];
		try {
			CheckInputParameters.CheckMacAddr(time);
		} catch (InvalidParameter e1) {
			throw new MqttStringMismatch("Stringa ricevuta non idonea. Causa -> Orario");
		}
		
		list.add(this);
		
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
