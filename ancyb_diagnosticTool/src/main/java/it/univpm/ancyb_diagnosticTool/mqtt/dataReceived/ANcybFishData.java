package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

/**
 * 
 * @author giaco
 *
 */
public class ANcybFishData implements DataReceived {

	protected String time;
	private String date;
	private String ver;
	private String macAddr;
	public static ArrayList<ANcybFishData> list = new ArrayList<ANcybFishData>();

	
	/**
	 * 
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
	
	@Override
	public String getDate() {
		return date;
	}
	
	@Override
	public void setDate() {	
		this.date = Time.currentDate();
	}

	@Override
	public String getTime() {
		return time;
	}
	
	@Override
	public void setTime() {
		this.date = Time.currentTime();
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
