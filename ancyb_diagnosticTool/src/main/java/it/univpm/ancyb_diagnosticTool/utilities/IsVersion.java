package it.univpm.ancyb_diagnosticTool.utilities;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;

public class IsVersion {
	
	public static boolean verG(ArrayList<ANcybFishData> fishDataList)  {
		for(ANcybFishData fishData : fishDataList) {
			if (!((fishData instanceof ANcybFishData_VerG)||(fishData instanceof ANcybFishData_VerG)))
				return false;
		}
		return true;
	}
	
	public static boolean verGT(ArrayList<ANcybFishData> fishDataList) {
		for(ANcybFishData fishData : fishDataList) {
			if (!((fishData instanceof ANcybFishData_VerGT)||(fishData instanceof ANcybFishData_VerGT)))
				return false;
		}
		return true;
	}
}
