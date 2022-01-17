package it.univpm.ancyb_diagnosticTool.utilities;

import it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat;
/**
 * 
 * @author Giacomo Fiara
 *
 */
public class Coord {
	
	/**
	 * questa fuzione controlla che la coordinata sia una latitudine DMM:
	 * ovvero gradi e minuti e decimi di minuti
	 * @param str
	 * @throws WrongCoordFormat
	 */
	static public void checkIsLat(String str) throws WrongCoordFormat {
		int i;
		if(str.length()!=10)
			throw new WrongCoordFormat("Latitude -> number of characters not matching");
		if(str.charAt(4)!='.')
			throw new WrongCoordFormat("Latitude -> there is no point between minutes and tenths of minutes");
		if( str.charAt(9) !='N'&& str.charAt(9) !='S' )
			throw new WrongCoordFormat("Latitude -> there isn't the reference N/S");
		for(i = 0; i<4; i++) {
			if(!Character.isDigit(str.charAt(i)))
				throw new WrongCoordFormat("Latitude -> there are non-numeric characters that are not allowed");
		}
		for(i = 5; i < 9; i++) {
			if(!Character.isDigit(str.charAt(i))) 
				throw new WrongCoordFormat("Latitude -> there are non-numeric characters that are not allowed");
		}
	}
	
	/**
	 * questa fuzione controlla che la coordinata sia una longitudine DMM:
	 * ovvero gradi e minuti decimali
	 * @param str
	 * @throws WrongCoordFormat
	 */
	static public void checkIsLon(String str) throws WrongCoordFormat {
		int i;
		if(str.length()!=11)
			throw new WrongCoordFormat("Longitude -> number of characters not matching");
		if(str.charAt(5)!='.')
			throw new WrongCoordFormat("Longitude -> there is no point between minutes and tenths of minutes");
		if( str.charAt(10) !='W'&& str.charAt(10) !='E' )
			throw new WrongCoordFormat("Longitude -> there isn't the reference E/W");
		for(i = 0; i<5; i++) {
			if(!Character.isDigit(str.charAt(i)))
				throw new WrongCoordFormat("Longitude -> there are non-numeric characters that are not allowed");
		}
		for(i = 6; i < 10; i++) {
			if(!Character.isDigit(str.charAt(i)))
				throw new WrongCoordFormat("Longitudine -> there are non-numeric characters that are not allowed");
		}
	}
	
	/**
	 * questa funzione converte la latitudine in DMM (in gradi e minuti decimali)
	 * in DD (gradi decimali)
	 * @param str
	 * @return
	 * @throws WrongCoordFormat
	 */
	static public float latDMMstringToDDfloat(String str) throws WrongCoordFormat {
		String gg = str.substring(0, 2);
		String mm = str.substring(2, 4);
		String dddd = str.substring(5, 9);
		
		float g,m,d;
		int sign=1;
		
		g = Float.parseFloat(gg);
		m = Float.parseFloat(mm);
		d = Float.parseFloat(dddd);

		float lat = g + m/60 + (d/60)/10000;
		
		if(lat >= 0 && lat <= 90) {
			if(str.charAt(9)=='S') sign = -1;
			lat = sign*lat;
			return lat;
		}
		else {
			throw new WrongCoordFormat("Latitude -> angle not included between -90° and 90°");
		}
	}

	
	/**
	 * questa funzione converte la longitudine in DMM (in gradi e minuti decimali)
	 * in DD (gradi decimali)
	 * @param str
	 * @return
	 * @throws WrongCoordFormat
	 */
	static public float lonDMMstringToDDfloat(String str) throws WrongCoordFormat {
		String ggg = str.substring(0, 3);
		String mm = str.substring(3, 5);
		String dddd = str.substring(6, 10);
		
		float g,m,d;
		int sign = 1;
		
		g = Float.parseFloat(ggg);
		m = Float.parseFloat(mm);
		d = Float.parseFloat(dddd);
		
		float lon = g + m/60 + (d/60)/10000;
		
		if(lon >= 0 && lon <= 180) {
			if(str.charAt(10)=='W') sign = -1;
			lon = sign*lon;
			return lon;
		}
		else {
			throw new WrongCoordFormat("Longitude -> angle not included between -180° and 180°");
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
		
		// Definisce le costanti e le variabili
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
