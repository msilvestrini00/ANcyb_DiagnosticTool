package it.univpm.ancyb_diagnosticTool.utilities;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;

/**
 * <b>Classe</b> che effettua dei controlli sulle versioni di ArrayList di tipo
 * {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}.
 * 
 * @author Giacomo Fiara
 */
public class IsVersion {
	
	/**
	 * <b>Metodo</b> che controlla se la versione associata a ciascun elemento dell'ArrayList
	 *  corrisponde alla "Ver_G" o versioni che ereditano da questa.
	 * @param fishDataList ArrayList contenente le istanze di cui si vogliono controllare le versioni.
	 * @return booleano:<br>
	 * - Falso se almeno un elemento presenta una versione diversa dalla "Ver_G". <br>
	 * - Vero se tutti gli elementi sono di versione "Ver_G".
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG ANcybFishData_VerG
	 */
	public static boolean verG(ArrayList<ANcybFishData> fishDataList)  {
		for(ANcybFishData fishData : fishDataList) {
			if (!((fishData instanceof ANcybFishData_VerG)||(fishData instanceof ANcybFishData_VerG)))
				return false;
		}
		return true;
	}
	
	/**
	 * <b>Metodo</b> che controlla se la versione associata a ciascun elemento dell'ArrayList
	 *  corrisponde alla "Ver_GT" o versioni che ereditano da questa.
	 * @param fishDataList ArrayList contenente le istanze di cui si vogliono controllare le versioni.
	 * @return booleano:<br>
	 * - Falso se almeno un elemento presenta una versione diversa dalla "Ver_GT". <br>
	 * - Vero se tutti gli elementi sono di versione "Ver_GT".
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG ANcybFishData_VerG
	 */
	public static boolean verGT(ArrayList<ANcybFishData> fishDataList) {
		for(ANcybFishData fishData : fishDataList) {
			if (!((fishData instanceof ANcybFishData_VerGT)||(fishData instanceof ANcybFishData_VerGT)))
				return false;
		}
		return true;
	}
}
