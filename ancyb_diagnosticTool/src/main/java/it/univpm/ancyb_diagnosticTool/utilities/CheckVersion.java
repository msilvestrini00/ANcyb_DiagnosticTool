package it.univpm.ancyb_diagnosticTool.utilities;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;

/**
 * <b>Classe</b> che effettua dei controlli sulle versioni di istanze o ArrayList di tipo
 * {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}.
 * 
 * @author Giacomo Fiara
 */
public class CheckVersion {
	
	/**
	 * <b>Metodo</b> che controlla se la versione associata all'istanza
	 *  corrisponde alla "Ver_G" o versioni che ereditano da questa.
	 * @param fishData dato di cui si vuole testare la versione.
	 * @throws VersionMismatch
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG ANcybFishData_VerG
	 */
	public static void verG(ANcybFishData fishData) throws VersionMismatch {
		if (!((fishData instanceof ANcybFishData_VerG)||(fishData instanceof ANcybFishData_VerGT)))
			throw new VersionMismatch("VersionMismatch(VerG single data) --> the device isn't 'G' o 'GX' version software.");
	}
	
	/**
	 * <b>Metodo</b> che controlla se la versione associata a ciascun elemento dell'ArrayList
	 *  corrisponde alla "Ver_G" o versioni che ereditano da questa.
	 * @param fishDataList ArrayList contenente le istanze di cui si vogliono controllare le versioni.
	 * @throws VersionMismatch
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG ANcybFishData_VerG
	 */
	public static void verG(ArrayList<ANcybFishData> fishDataList) throws VersionMismatch {
		for(ANcybFishData fishData : fishDataList) {
			if (!((fishData instanceof ANcybFishData_VerG)||(fishData instanceof ANcybFishData_VerGT)))
				throw new VersionMismatch("VersionMismatch(VerG ArrayList) --> the device isn't 'G' o 'GX' version software.");

		}
	}
	
	/**
	 * <b>Metodo</b> che controlla se la versione associata all'istanza
	 *  corrisponde alla "Ver_GT" o versioni che ereditano da questa.
	 * @param fishData dato di cui si vuole testare la versione.
	 * @throws VersionMismatch
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT ANcybFishData_VerGT
	 */
	public static void verGT(ANcybFishData fishData) throws VersionMismatch {
		if (!(fishData instanceof ANcybFishData_VerGT))
			throw new VersionMismatch("VersionMismatch(VerGT single data) --> the device isn't 'GT' version software.");
	}
	
	/**
	 * <b>Metodo</b> che controlla se la versione associata a ciascun elemento dell'ArrayList
	 *  corrisponde alla "Ver_GT" o versioni che ereditano da questa.
	 * @param fishDataList ArrayList contenente le istanze di cui si vogliono controllare le versioni.
	 * @throws VersionMismatch
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT ANcybFishData_VerGT
	 */
	public static void verGT(ArrayList<ANcybFishData> fishDataList) throws VersionMismatch {
		for(ANcybFishData fishData : fishDataList) {
			if (!(fishData instanceof ANcybFishData_VerGT))
				throw new VersionMismatch("VersionMismatch(VerGT ArrayList) --> the device isn't 'GT' version software.");

		}
	}
}
