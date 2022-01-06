package it.univpm.ancyb_diagnosticTool.utilities;

import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;

/**
 * 
 * @author Giacomo Fiara
 *
 */
public class checkVersion {
	
	public static void verG(ANcybFishData fishData) throws VersionMismatch {
		if (!((fishData instanceof ANcybFishData_VerG)||(fishData instanceof ANcybFishData_VerG)))
			throw new VersionMismatch("L'oggetto non è dotato della Versione Software 'G' o 'GX'.");
	}
	
	public static void verGT(ANcybFishData fishData) throws VersionMismatch {
		if (!(fishData instanceof ANcybFishData_VerGT))
			throw new VersionMismatch("L'oggetto non è dotato della Versione Software 'GT'.");
	}
}
