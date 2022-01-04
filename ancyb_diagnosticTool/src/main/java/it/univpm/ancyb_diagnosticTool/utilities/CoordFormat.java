package it.univpm.ancyb_diagnosticTool.utilities;

import it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat;

public class CoordFormat {
	
	/**
	 * questa fuzione controlla che la coordinata sia una latitudine GMS
	 * ovvero gradi, decimi e secondi
	 */
	
	static public void checkIsLat(String str) throws WrongCoordFormat {
		int i;
		if(str.length()!=10)
			throw new WrongCoordFormat("Latitudine: numero caratteri non corrispondente");
		if(str.charAt(4)!='.')
			throw new WrongCoordFormat("Latitudine: non presente la virgola tra primi e secondi");
		if( str.charAt(9) !='N'&& str.charAt(9) !='S' )
			throw new WrongCoordFormat("Latitudine: non presente il riferimento N/S");
		for(i = 0; i<4; i++) {
			if(!Character.isDigit(str.charAt(i)))
				throw new WrongCoordFormat("Latitudine: presenti dei caratteri non ammessi");
		}
		for(i = 5; i < 9; i++) {
			if(!Character.isDigit(str.charAt(i))) 
				throw new WrongCoordFormat("Latitudine: presenti dei caratteri non ammessi");
		}
	}
	
	/**
	 * questa fuzione controlla che la coordinata sia una latitudine GMS
	 * ovvero gradi, decimi e secondi
	 */
	
	static public void checkIsLon(String str) throws WrongCoordFormat {
		int i;
		if(str.length()!=11)
			throw new WrongCoordFormat("Longitudine: numero caratteri non corrispondente");
		if(str.charAt(5)!='.')
			throw new WrongCoordFormat("Longitudine: non presente la virgola tra primi e secondi");
		if( str.charAt(10) !='W'&& str.charAt(10) !='E' )
			throw new WrongCoordFormat("Longitudine: non presente il riferimento E/W");
		for(i = 0; i<5; i++) {
			if(!Character.isDigit(str.charAt(i)))
				throw new WrongCoordFormat("Longitudine: presenti dei caratteri non ammessi");
		}
		for(i = 6; i < 10; i++) {
			if(!Character.isDigit(str.charAt(i)))
				throw new WrongCoordFormat("Longitudine: presenti dei caratteri non ammessi");
		}
	}
	
	/**
	 * questa funzione converte la latitudine in GMS
	 * (in gradi, minuti e secondi) in GD (gradi decimali)
	 */
	
	static public float latGMSstringToGDfloat(String str) throws WrongCoordFormat {
		
		String g = str.substring(0, 2);
		String m = str.substring(2, 4);
		String s = str.substring(5, 9);
		float gg,mm,ssss;
		int sign=1;
		
		gg = MqttStringOperator.strToFloat(g);
		mm = MqttStringOperator.strToFloat(m);
		ssss = MqttStringOperator.strToFloat(s);

		float lat = gg+mm/3600+ssss/3600;
		
		if(lat >= 0 && lat <= 90) {
			if(str.charAt(9)=='S') sign = -1;
			lat = sign*lat;
			return lat;
		}
		else {
			throw new WrongCoordFormat("Latitudine non compresa tra i -90° e 90°");
		}
		
	}

	/**
	 * questa funzione converte la longitudine in GMS
	 * (in gradi, minuti e secondi) in GD (gradi decimali)
	 */
	
	static public float lonGMSstringToGDfloat(String str) throws WrongCoordFormat {
		
		String g = str.substring(0, 3);
		String m = str.substring(3, 5);
		String s = str.substring(6, 10);
		float ggg,mm,ssss;
		int sign = 1;
		
		ggg = MqttStringOperator.strToFloat(g);
		mm = MqttStringOperator.strToFloat(m);
		ssss = MqttStringOperator.strToFloat(s);
		
		float lon = ggg+mm/3600+ssss/3600;
		
		if(lon >= 0 && lon <= 90) {
			if(str.charAt(10)=='W') sign = -1;
			lon = sign*lon;
			return lon;
		}
		else {
			throw new WrongCoordFormat("Longitudine non compresa tra i -180° e 180°");
		}
		
	}
	
}
