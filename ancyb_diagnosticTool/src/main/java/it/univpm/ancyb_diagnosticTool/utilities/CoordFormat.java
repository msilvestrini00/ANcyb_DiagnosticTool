package it.univpm.ancyb_diagnosticTool.utilities;

import it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat;

public class CoordFormat {
	
	/**
	 * questa fuzione controlla che la coordinata sia una latitudine GMS
	 * ovvero gradi, decimi e secondi
	 */
	
	static public boolean checkIsLat(String str) throws WrongCoordFormat {
		boolean flag = false;
		if(str.length()==10) {
			if(str.charAt(5)=='.') {
				if( str.charAt(9) =='N'|| str.charAt(9) =='S' ) {
					flag = true;
				}else throw new WrongCoordFormat("Non è presente il riferimento N/S");
			} else throw new WrongCoordFormat("Nessun punto trovato");
		} else throw new WrongCoordFormat("Numero caratteri non corrispondente");
		return flag;
	}
	
	/**
	 * questa fuzione controlla che la coordinata sia una latitudine GMS
	 * ovvero gradi, decimi e secondi
	 */
	
	static public boolean checkIsLong(String str) throws WrongCoordFormat {
		boolean flag = false;
		if(str.length()==11) {
			if(str.charAt(5)=='.') {
				if( str.charAt(10) =='E'|| str.charAt(10) =='W' ) {
					flag = true;
				}else throw new WrongCoordFormat("Non è presente il riferimento E/W");
			} else throw new WrongCoordFormat("Nessun punto trovato");
		} else throw new WrongCoordFormat("Numero caratteri non corrispondente");
		return flag;
	}
	
	
	
	/**
	 * questa funzione converte la latitudine in GMS
	 * (in gradi, minuti e secondi) in GD (gradi decimali)
	 */
	
	static public float latGMSstringtoGDfloat(String str) throws WrongCoordFormat {
		
		String g = str.substring(0, 1);
		String m = str.substring(2, 3);
		String s = str.substring(5, 8);
		float gg,mm,ssss;
		int sign=1;
		
		gg = convStrToFloat(g);
		mm = convStrToFloat(m);
		ssss = convStrToFloat(s);
		
		if (gg<0 || mm<0 || ssss<0) {
			throw new WrongCoordFormat("Stringa non conforme alla conversione in latitudine");
		}
				
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
	
	static public float lonGMSstringtoGDfloat(String str) throws WrongCoordFormat {
		
		String g = str.substring(0, 2);
		String m = str.substring(3, 4);
		String s = str.substring(6, 9);
		float ggg,mm,ssss;
		int sign = 1;
		
		ggg = convStrToFloat(g);
		mm = convStrToFloat(m);
		ssss = convStrToFloat(s);
		
		if (ggg<0 || mm<0 || ssss<0) {
			throw new WrongCoordFormat("Stringa non conforme alla conversione in longitudine");
		}
		
		float longi = ggg+mm/3600+ssss/3600;
		
		if(longi >= 0 && longi <= 90) {
			if(str.charAt(10)=='W') sign = -1;
			longi = sign*longi;
			return longi;
		}
		else {
			throw new WrongCoordFormat("Longitudine non compresa tra i -180° e 180°");
		}
		
	}

	public static float convStrToFloat(String str) {
		
		float num = -1;
		
		try {
			num = Float.parseFloat(str);
		}catch(NullPointerException e){
			System.out.println("Exception: " + e);
		}
		catch(NumberFormatException e){
			System.out.println("Exception: " + e);	
		}
		return num;
	}
	
}
