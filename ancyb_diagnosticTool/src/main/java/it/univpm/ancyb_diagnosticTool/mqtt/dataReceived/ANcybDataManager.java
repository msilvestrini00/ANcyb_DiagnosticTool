package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import it.univpm.ancyb_diagnosticTool.Exception.InvalidParameter;
import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;
import it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat;

/**
 *<b>Classe</b> che gestisce le stringhe ricevute dal grazie al subscribe() 
 * @author Giacomo Fiara
 *
 */
public class ANcybDataManager {

	/**
	 * <b>Metodo</b> che restituiscie un oggetto ANcybFishData
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
				throw new MqttStringMismatch("MqttStringMismatch(Ver_G case) --> Stringa corrispondente alla Ver_G ma presenta un numero di elementi incompatibile");
			
			try {
				ancybData = new ANcybFishData_VerG(strArr);
			} catch (InvalidParameter|WrongCoordFormat|NullPointerException|NumberFormatException e) {
				System.err.println("Deep Exception: " + e);
				throw new MqttStringMismatch("MqttStringMismatch(Ver_G constructor) --> " + e.getMessage());
			}
			DataSaved.getList().add(ancybData);
			break;
		
		case "Ver_GT":
			
			if(strArr.length!=7)
				throw new MqttStringMismatch("MqttStringMismatch(Ver_GT case) --> Stringa corrispondente alla Ver_GT ma presenta un numero di elementi incompatibile");
			
			try {
				ancybData = new ANcybFishData_VerGT(strArr);
			} catch (InvalidParameter|WrongCoordFormat|NullPointerException|NumberFormatException e) {
				System.err.println("Deep Exception: " + e);
				throw new MqttStringMismatch("MqttStringMismatch(Ver_GT constructor) --> " + e.getMessage());
			}
			DataSaved.getList().add(ancybData);
			break;
			
		//nel caso avessi una versione con sensore di pressione ("P"), o altre ...
		/*
		case "Ver_P":
			
			break;
		*/
		default:
			throw new MqttStringMismatch("MqttStringMismatch() --> Dato ricevuto non corrisponde a nessuna versione ''ANcybFishData'' software rilasciata");
		}
		
		return ancybData;
		
	}
	
}
