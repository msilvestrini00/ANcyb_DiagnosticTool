package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;
import it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat;
import it.univpm.ancyb_diagnosticTool.utilities.CoordFormat;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

/**
 * 
 * @author Giacomo Fiara
 *
 */
public class ANcybDataManager {
	
	public static ANcybFishData createDataObj(String str) throws MqttStringMismatch {
		
		String mac, date, time, latitude, longitude, qualPos, ver, temperature;
		float lat, lon, temp;
		ANcybFishData ancybData = null;
		
		//Separa la stringa dove trova spazi
		String[] strArr = str.split("\\s+");
		
		switch (strArr[1]) {
		case "Ver_G":
			
			if(strArr.length!=6)
				throw new MqttStringMismatch("Stringa corrispondente alla Ver_G ma presenta un numero di elementi incompatibile");
		
			mac = strArr[0];
			if(mac.length()!=17)
				throw new MqttStringMismatch("Stringa ricevuta non idonea. Causa -> Mac Address");
			
			ver = strArr[1];
			
			time = strArr[2];
			
			latitude = strArr[3];
			try {
				CoordFormat.checkIsLat(latitude);
				lat = CoordFormat.latGMSstringToGDfloat(latitude);
			} catch (WrongCoordFormat|NullPointerException|NumberFormatException e){
				//TODO chiedere al prof o a chi di dovere se sta cosa ha senso
				throw new MqttStringMismatch("Stringa ricevuta non idonea. Causa -> latitudine");
			}
		
			longitude = strArr[4];
			try {
				CoordFormat.checkIsLon(longitude);
				lon = CoordFormat.lonGMSstringToGDfloat(longitude);
			} catch (WrongCoordFormat|NullPointerException|NumberFormatException e){
				//TODO chiedere al prof o a chi di dovere se sta cosa ha senso
				throw new MqttStringMismatch("Stringa ricevuta non idonea. Causa -> longitudine");
			}
			
			qualPos = strArr[5];
			
			date = Time.currentDate();
			
			ancybData = new ANcybFishData_VerG(date, time, mac, ver, lat, lon, qualPos);
			break;
		
		case "Ver_GT":
			
			if(strArr.length!=7)
				throw new MqttStringMismatch("Stringa corrispondente alla Ver_GT ma presenta un numero di elementi incompatibile");
			
			mac = strArr[0];
			if(mac.length()!=17)
				throw new MqttStringMismatch("Stringa ricevuta non idonea. Causa -> Mac Address");
			
			ver = strArr[1];
			
			time = strArr[2];
			
			latitude = strArr[3];
			try {
				CoordFormat.checkIsLat(latitude);
				lat = CoordFormat.latGMSstringToGDfloat(latitude);
			} catch (WrongCoordFormat|NullPointerException|NumberFormatException e){
				//TODO chiedere al prof o a chi di dovere se sta cosa ha senso
				throw new MqttStringMismatch("Stringa ricevuta non idonea. Causa -> latitudine");
			}
		
			longitude = strArr[4];
			try {
				CoordFormat.checkIsLon(longitude);
				lon = CoordFormat.lonGMSstringToGDfloat(longitude);
			} catch (WrongCoordFormat|NullPointerException|NumberFormatException e){
				//TODO chiedere al prof o a chi di dovere se sta cosa ha senso
				throw new MqttStringMismatch("Stringa ricevuta non idonea. Causa -> longitudine");
			}
			
			qualPos = strArr[5];
			
			temperature = strArr[6];
			try {
				temp = Float.parseFloat(temperature);
			} catch (NullPointerException|NumberFormatException e){
				//TODO chiedere al prof o a chi di dovere se sta cosa ha senso
				throw new MqttStringMismatch("Stringa ricevuta non idonea. Causa -> temperatura");
			}
					
			date = Time.currentDate();
			
			ancybData = new ANcybFishData_VerGT(date, time, mac, ver, lat, lon, qualPos, temp);			
			break;
			
			//nel caso avessi una versione pressione ("P"), o altre ...
		    /*case "Ver_P":
			
			break;*/

		default:
			throw new MqttStringMismatch("Dato ricevuto non corrisponde a nessuna versione ''ANcybFishData'' software rilasciata");
		}
		
		return ancybData;
		
	}
	
}
