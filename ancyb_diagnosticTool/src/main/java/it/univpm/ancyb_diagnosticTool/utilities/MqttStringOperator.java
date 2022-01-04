package it.univpm.ancyb_diagnosticTool.utilities;

public class MqttStringOperator {
	
	static public String[] split (String str) {
		
		String[] strArr = str.split("\\s+");
		return strArr;
		
	}
	
	static public float strToFloat(String str) throws NullPointerException,  NumberFormatException{
		float num;
		num = Float.parseFloat(str);
		return num;
	}
}
