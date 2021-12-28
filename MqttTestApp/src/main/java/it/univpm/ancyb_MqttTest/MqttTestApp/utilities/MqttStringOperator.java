package it.univpm.ancyb_MqttTest.MqttTestApp.utilities;

public class MqttStringOperator {
	
	static public String[] split (String str) {
		
		String[] strArr = str.split("\\s+");
		return strArr;
		
	}
	
}
