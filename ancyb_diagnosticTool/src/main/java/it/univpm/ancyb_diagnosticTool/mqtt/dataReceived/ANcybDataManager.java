package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import java.util.regex.PatternSyntaxException;

import it.univpm.ancyb_diagnosticTool.Exception.InvalidParameter;
import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;
import it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat;

/**
 * <b>Classe</b> che gestisce le stringhe ricevute dal {@link it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient MQTTclient} grazie al metodo {@link it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient#subscribe(String) subscribe()}.
 * @author Giacomo Fiara
 */
public class ANcybDataManager {

	/**
	 * <b>Metodo</b> che restituisce un oggetto {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}.<br>
	 * La stringa viene splittata, viene così generato un array di stringhe. Un primo controllo viene effettuato sul secondo elemento, che dovrebbe contenere la specifica sulla versione.<br>
	 * Successivamente, viene invocato il metodo costruttore adatto a ciascuna versione.
	 * In caso di corretta creazione dell'istanza, questa viene subito salvata tramite {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.DataSaved#getList() DataSaved.getList().add()}
	 * 
	 * @param strReceived è la stringa che si vuole venga elaborata al fine di istanziare nuovi {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}.
	 * 
	 * @return l'istanza {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} appena creata.
	 *
	 * @throws MqttStringMismatch
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.DataSaved DataSaved
	 */
	public ANcybFishData createDataObj(String strReceived) throws MqttStringMismatch {
		

		/**
		 * array di stringhe ricavato dopo lo split
		 */
		String[] strArr;
		
		/**
		 * istanza restituita dal metodo
		 */
		ANcybFishData ancybData;
		
		//Separa la stringa dove trova spazi
		try {
			strArr = strReceived.split("\\s+");
		} catch (PatternSyntaxException e) {
			throw new MqttStringMismatch("MqttStringMismatch(split String received)\n" + "Deep Exception: " + e);
		}
		
		try {
			switch (strArr[1]) {
			case "Ver_G":
				
				if(strArr.length!=6)
					throw new MqttStringMismatch("MqttStringMismatch(Ver_G case) --> String contains 'Ver_G' but has an incompatible number of elements");
				
				try {
					ancybData = new ANcybFishData_VerG(strArr);
				} catch (InvalidParameter|WrongCoordFormat|NullPointerException|NumberFormatException|ArrayIndexOutOfBoundsException e) {
					throw new MqttStringMismatch("MqttStringMismatch(Ver_G constructor)\n" + "Deep Exception: " + e);
				}
				DataSaved.getList().add(ancybData);
				break;
			
			case "Ver_GT":
				
				if(strArr.length!=7)
					throw new MqttStringMismatch("MqttStringMismatch(Ver_GT case) --> String contains 'Ver_GT' but has an incompatible number of elements");
				
				try {
					ancybData = new ANcybFishData_VerGT(strArr);
				} catch (InvalidParameter|WrongCoordFormat|NullPointerException|NumberFormatException|ArrayIndexOutOfBoundsException e) {
					throw new MqttStringMismatch("MqttStringMismatch(Ver_GT constructor)\n" + "Deep Exception: " + e);
				}
				DataSaved.getList().add(ancybData);
				break;
				
			//nel caso avessi una versione con sensore di pressione ("P"), o altre ...
			/*
			case "Ver_P":
				
				break;
			*/
			default:
				throw new MqttStringMismatch("MqttStringMismatch() --> Data received does not correspond to any 'ANcybFishData' software release.");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new MqttStringMismatch("MqttStringMismatch(String generated an Array too small.)\n" + "Deep Exception: " + e);
		}
		
		return ancybData;
		
	}
	
}
