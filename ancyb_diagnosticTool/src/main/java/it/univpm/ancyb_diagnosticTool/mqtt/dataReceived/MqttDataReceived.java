package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import it.univpm.ancyb_diagnosticTool.utilities.CurrentDateTime;

public class MqttDataReceived {
	
	public static ANcybFishData createDataObj(String str) {
		
		String mac, time, latitude, longitude, qualPos, ver, temp;
		ANcybFishData ancybData = null;
		
		String[] strArr = str.split("\\s+");
		
		switch (strArr[1]) {
		case "Ver_G":
			
			try {
				mac = strArr[0];
				ver = strArr[1];
				time = strArr[2];
				latitude = strArr[3];
				longitude = strArr[4];
				qualPos = strArr[5];
				
				String date = CurrentDateTime.currentDateTime();
				
				ancybData = new ANcybFishData_VerG(date, time, mac, ver, latitude, longitude, qualPos);
			}
			catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Errore nella creazione di un oggetto Ver_G");
				e.printStackTrace();
				ancybData = null;
			}
			
			break;
		case "Ver_GT":
			
			try {
				mac = strArr[0];
				ver = strArr[1];
				time = strArr[2];
				latitude = strArr[3];
				longitude = strArr[4];
				qualPos = strArr[5];
				temp = strArr[6];
				
				String date = CurrentDateTime.currentDateTime();
				
				ancybData = new ANcybFishData_VerGT(date, time, mac, ver, latitude, longitude, qualPos, temp);
			}
			catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Errore nella creazione di un oggetto Ver_GT (passaggio dell'array di stringhe)");
				e.printStackTrace();
				return ancybData = null;
			}
			
			break;
		/*case "Ver_GPS":
			
			break;*/

		default:
			System.out.println("Dato ricevuto non corrisponde a nessuna versione rilasciata");
			break;
		}
		
		return ancybData;
		
	}
	
}
