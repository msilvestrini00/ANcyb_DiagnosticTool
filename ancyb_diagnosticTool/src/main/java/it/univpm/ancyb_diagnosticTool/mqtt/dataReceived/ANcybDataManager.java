package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;

/**
 * 
 * @author Giacomo Fiara
 *
 */
public class ANcybDataManager {

	/**
	 * 
	 * @param str
	 * @return
	 * @throws MqttStringMismatch
	 */
	public ANcybFishData createDataObj(String str) throws MqttStringMismatch {
		
		ANcybFishData ancybData;
		
		//Separa la stringa dove trova spazi
		String[] strArr = str.split("\\s+");
		
		switch (strArr[1]) {
		case "Ver_G":
			
			if(strArr.length!=6)
				throw new MqttStringMismatch("Stringa corrispondente alla Ver_G ma presenta un numero di elementi incompatibile");
			
			ancybData = new ANcybFishData_VerG(strArr);
			break;
		
		case "Ver_GT":
			
			if(strArr.length!=7)
				throw new MqttStringMismatch("Stringa corrispondente alla Ver_GT ma presenta un numero di elementi incompatibile");
			
			ancybData = new ANcybFishData_VerGT(strArr);			
			break;
			
		//nel caso avessi una versione con sensore di pressione ("P"), o altre ...
		/*
		case "Ver_P":
			
			break;
		*/
		default:
			throw new MqttStringMismatch("Dato ricevuto non corrisponde a nessuna versione ''ANcybFishData'' software rilasciata");
		}
		
		return ancybData;
		
	}
	
}
