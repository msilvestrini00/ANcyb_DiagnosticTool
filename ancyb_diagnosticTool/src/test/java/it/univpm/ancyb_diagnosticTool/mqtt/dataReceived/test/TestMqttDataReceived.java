package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager;

class TestMqttDataReceived {
	
	static ANcybFishData data, data1;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception, ArrayIndexOutOfBoundsException {
		
		String str = "a4:cf:12:76:76:95 Ver_GT 16:05:45 4320.1200N 01323.7442E 1 10.5";
		String str1 = "a4:cf:12:76:76:95";
		try {
			ANcybDataManager ancybDataManager = new ANcybDataManager();
			//data = ancybDataManager.createDataObj(str);
			String[] strArr = str1.split("\\s+");
			data1 = new ANcybFishData_VerG(strArr);
		} catch (MqttStringMismatch | ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	void test() {
		assertEquals(data.getTime(), "16:05:45");
		assertEquals(data.getVer(), "Ver_GT");
		assertEquals(43.66667, ((ANcybFishData_VerG) data).getLatitude(), 0.001);
		assertEquals(15.45056,((ANcybFishData_VerG) data).getLongitude(), 0.001);
		assertEquals(10.5, ((ANcybFishData_VerGT) data).getTemp(), 0.1);
		
	}

}
