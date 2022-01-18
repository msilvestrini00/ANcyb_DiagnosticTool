package it.univpm.ancyb_diagnosticTool.utilities;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;

/**
 * 
 * @author Giacomo Fiara
 *
 */
public class CheckVersion {
	
	public static void verG(ANcybFishData fishData) throws VersionMismatch {
		if (!((fishData instanceof ANcybFishData_VerG)||(fishData instanceof ANcybFishData_VerGT)))
			throw new VersionMismatch("VersionMismatch(VerG single data) --> the device isn't 'G' o 'GX' version software.");
	}
	
	public static void verG(ArrayList<ANcybFishData> fishDataList) throws VersionMismatch {
		for(ANcybFishData fishData : fishDataList) {
			if (!((fishData instanceof ANcybFishData_VerG)||(fishData instanceof ANcybFishData_VerGT)))
				throw new VersionMismatch("VersionMismatch(VerG ArrayList) --> the device isn't 'G' o 'GX' version software.");

		}
	}
	
	public static void verGT(ANcybFishData fishData) throws VersionMismatch {
		if (!(fishData instanceof ANcybFishData_VerGT))
			throw new VersionMismatch("VersionMismatch(VerGT single data) --> the device isn't 'GT' version software.");
	}
	
	public static void verGT(ArrayList<ANcybFishData> fishDataList) throws VersionMismatch {
		for(ANcybFishData fishData : fishDataList) {
			if (!(fishData instanceof ANcybFishData_VerGT))
				throw new VersionMismatch("VersionMismatch(VerGT ArrayList) --> the device isn't 'GT' version software.");

		}
	}
}
