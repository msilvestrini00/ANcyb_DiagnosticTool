package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import it.univpm.ancyb_diagnosticTool.utilities.Time;
import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;

class ANcybFishData implements DataReceived {

	protected String time;
	private String date;
	private String ver;
	private String macAddr;

	public ANcybFishData(String date, String time, String macAddr, String ver) {
		this.date=date;
		this.time=time;
		this.ver=ver;
		this.macAddr=macAddr;
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
				   "Version " + getVer();
		
		return s;
		
	}
}
