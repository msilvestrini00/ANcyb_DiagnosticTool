package it.univpm.ancyb_diagnosticTool.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;

class TestANcybFishDataManaging {

	private static ANcybFishData ancybDataObtainedA;
	private static ANcybFishData ancybDataObtainedB;
	private static ANcybFishData ancybDataExpectedA;
	private static ANcybFishData ancybDataExpectedB;
	private static AncybDiagnosticToolServiceImpl a;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		ANcybFishData ancybData1 = new ANcybFishData_VerGT("2022.01.06", "18:25:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "3", 10.5f);
		ANcybFishData.list.add(ancybData1);
		ANcybFishData ancybData2 = new ANcybFishData_VerG("2022.01.06", "18:25:52", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
		ANcybFishData.list.add(ancybData2);
		ANcybFishData ancybData3 = new ANcybFishData_VerGT("2022.01.06", "18:26:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "NO_signal", 10.5f);
		ANcybFishData.list.add(ancybData3);
		ANcybFishData ancybData4 = new ANcybFishData_VerG("2022.01.06", "18:27:38", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
		ANcybFishData.list.add(ancybData4);
		ANcybFishData ancybData5 = new ANcybFishData_VerGT("2022.01.06", "18:28:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "5l", 10.5f);
		ANcybFishData.list.add(ancybData5);
		ANcybFishData ancybData6 = new ANcybFishData_VerG("2022.01.06", "18:29:38", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
		ANcybFishData.list.add(ancybData6);
		ANcybFishData ancybData7 = new ANcybFishData_VerGT("2022.01.06", "18:30:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "NO_signal", 10.5f);
		ANcybFishData.list.add(ancybData7);
		a = new AncybDiagnosticToolServiceImpl();
		ancybDataObtainedA = a.getRealTimePosition("A4:cf:12:76:76:95");
		ancybDataExpectedA = new ANcybFishData_VerGT("2022.01.06", "18:30:38", "A4:cf:12:76:76:95", "Ver_GT", 44.915f, 15.25f, "NO_signal", 10.5f);
		ancybDataObtainedB = a.getRealTimePosition("B4:cf:12:76:76:95");
		ancybDataExpectedB = new ANcybFishData_VerG("2022.01.06", "18:29:38", "B4:cf:12:76:76:95", "Ver_G", 44.915f, 15.25f, "NO_signal");
	}

	@Test
	void test() {
		assertEquals(ancybDataExpectedA, ancybDataObtainedA);
		assertEquals(ancybDataExpectedB, ancybDataObtainedB);
	}

}
