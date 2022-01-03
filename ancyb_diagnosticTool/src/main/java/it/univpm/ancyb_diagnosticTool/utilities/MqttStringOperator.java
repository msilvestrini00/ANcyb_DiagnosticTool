package it.univpm.ancyb_diagnosticTool.utilities;

public class MqttStringOperator {
	
	static public String[] split (String str) {
		
		String[] strArr = str.split("\\s+");
		return strArr;
		
	}
}
