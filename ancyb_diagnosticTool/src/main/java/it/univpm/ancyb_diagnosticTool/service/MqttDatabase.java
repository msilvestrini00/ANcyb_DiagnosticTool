package it.univpm.ancyb_diagnosticTool.service;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.ElementNotFound;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;

public class MqttDatabase {
	
	//TODO se passo un array list vuoto?
	//TODO se mi passi un macaddress VerG/VerGT come cambia
	ANcybFishData_Ver lastPosition( ArrayList<ANcybFishData> data, String macAddr) throws ElementNotFound {
		
		ANcybFishData_Ver obj = null;
		for(int i=data.size()-1; i>=0; i--) {
			if(data.get(i).getMacAddr().equals(macAddr)) {
					obj = data.get(i);
			}
		}
		if(obj==null) {
			throw new ElementNotFound("Nessun elemento di posizione trovato nel database con questo mac address" + macAddr);
		}
		return obj;
	}
	
	//
	
}
