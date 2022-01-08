package it.univpm.ancyb_diagnosticTool.utilities;

import it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat;
/**
 * 
 * @author Giacomo Fiara
 *
 */
public class Coord {
	
	/**
	 * questa fuzione controlla che la coordinata sia una latitudine GMS:
	 * ovvero gradi, decimi e secondi
	 * @param str
	 * @throws WrongCoordFormat
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
	 * questa fuzione controlla che la coordinata sia una longitudine GMS:
	 * ovvero gradi, decimi e secondi 
	 * @param str
	 * @throws WrongCoordFormat
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
	 * questa funzione converte la latitudine in GMS (in gradi, minuti e secondi)
	 * in GD (gradi decimali)
	 * @param str
	 * @return
	 * @throws WrongCoordFormat
	 */
	static public float latGMSstringToGDfloat(String str) throws WrongCoordFormat {
		
		String g = str.substring(0, 2);
		String m = str.substring(2, 4);
		String s = str.substring(5, 9);
		float gg,mm,ssss;
		int sign=1;
		
		gg = Float.parseFloat(g);
		mm = Float.parseFloat(m);
		ssss = Float.parseFloat(s);

		float lat = gg+mm/60+ssss/3600;
		
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
	 * questa funzione converte la longitudine in GMS (in gradi, minuti e secondi)
	 * in GD (gradi decimali)
	 * @param str
	 * @return
	 * @throws WrongCoordFormat
	 */
	static public float lonGMSstringToGDfloat(String str) throws WrongCoordFormat {
		
		String g = str.substring(0, 3);
		String m = str.substring(3, 5);
		String s = str.substring(6, 10);
		float ggg,mm,ssss;
		int sign = 1;
		
		ggg = Float.parseFloat(g);
		mm = Float.parseFloat(m);
		ssss = Float.parseFloat(s);
		
		float lon = ggg+mm/60+ssss/3600;
		
		if(lon >= 0 && lon <= 90) {
			if(str.charAt(10)=='W') sign = -1;
			lon = sign*lon;
			return lon;
		}
		else {
			throw new WrongCoordFormat("Longitudine non compresa tra i -180° e 180°");
		}
		
	}	
	
	/**
	 * Calcolo della distanza geodetica (in km) tra due punti (A e B) della superficie terrestre.
	 * Lavora esclusivamente con coordinate in gradi decimali
	 * @param f1 coppia di coordinate del punto A (latitudine e longitudine)
	 * @param f2 coppia di coordinate del punto B (latitudine e longitudine)
	 * @return
	 */
	static public double disgeod(float[] f1, float[] f2) {
		
		//Definisce le costanti e le variabili
		double R = 6371;
		double pi = 3.1415927;
		double latA_rad, latB_rad;
		double lonA_rad, lonB_rad;
		double fi;
		double p, d;
		
		// Converte i gradi in radianti
		latA_rad = ( pi * f1[0] ) / 180;
		latB_rad = ( pi * f2[0] ) / 180;
		lonA_rad = ( pi * f1[1] ) / 180;
		lonB_rad = ( pi * f2[1] ) / 180;
		
		// Calcola l'angolo compreso fi
		fi = Math.abs(lonA_rad - lonB_rad);
		
		// Calcola il terzo lato del triangolo sferico
		p = Math.acos(Math.sin(latB_rad) * Math.sin(latA_rad) + 
			Math.cos(latB_rad) * Math.cos(latA_rad) * Math.cos(fi));
		
		// Calcola la distanza sulla superficie terrestre R = ~6371 km
		d = p * R;
		return(d);
	}
	
}
