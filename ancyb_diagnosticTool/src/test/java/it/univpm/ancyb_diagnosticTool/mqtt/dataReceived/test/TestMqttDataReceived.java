package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

/**
 * 
 * @author Giacomo Fiara
 *
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(Lifecycle.PER_CLASS)
class TestMqttDataReceived {
	
	ANcybDataManager ancybDataManager;
	private ANcybFishData fishdata;
	int testNum;
	
	String[] strX = new String[]{
			"a4:cf:12:76:76:95 Ver_G 16:05:45 4334.5102N 01335.2629E 1",
			"b4:cf:12:76:76:95 Ver_GT 16:05:50 4031.3926N 07401.3875W 1 10.5",
			"c4:cf:12:76:76:95 Ver_GT 16:05:55 3400.9756S 15150.7438E 1 5.3"
	};
	
	/*
	String str4 = "a4:cf:12:76:76:95 Ver_G 16:06:00 4334.5110N 01335.2629E 1";
	String str5 = "b4:cf:11:76:76:95 Ver_GT 16:06:05 4031.3924N 07401.3876W 1 11.3";
	String str6 = "c4:cf:12:76:76:95 Ver_GT 16:06:10 3400.9756S 15150.7438E 1 4.8";
	String str7 = "a4:cf:12:76:76:95 Ver_G 16:06:15 4334.5130N 01335.2628E 1";
	String str8 = "b4:cf:12:76:76:95 Ver_GT 16:06:20 4031.3922N 07401.3876W 1 10.2";
	String str9 = "c4:cf:12:76:76:95 Ver_GT 16:06:25 3400.9756S 15150.7438E 1 6.1"
	String str10 = "a4:cf:12:76:76:95 Ver_G 16:06:30 4334.5120N 01335.2627E 1";
	String str11 = "b4:cf:11:76:76:95 Ver_GT 16:06:35 4031.3915N 07401.3877W 1 10.5";
	String str12 = "c4:cf:12:76:76:95 Ver_GT 16:06:40 3400.9756S 15150.7438E 1 4.5";
	String str13 = "a4:cf:12:76:76:95 Ver_G 16:06:45 4334.5125N 01335.2626E 1";
	String str14 = "b4:cf:12:76:76:95 Ver_GT 16:06:50 4031.3910N 07401.3880W 1 10.8";
	String str15 = "c4:cf:12:76:76:95 Ver_GT 16:06:55 3400.9756S 15150.7438E 1 5";
	String str16 = "a4:cf:12:76:76:95 Ver_G 16:07:00 4334.5136N 01335.2622E 1";
	String str17 = "b4:cf:12:76:76:95 Ver_GT 16:07:05 4031.3910N 07401.3884W 1 10.5";
	String str18 = "c4:cf:12:76:76:95 Ver_GT 16:07:10 3400.9756S 15150.7438E 1 5.1";
	*/
	
	/*
	private ANcybFishData fishdata1;
	private ANcybFishData fishdata2;
	private ANcybFishData fishdata3;
	private ANcybFishData fishdata4;
	private ANcybFishData fishdata5;
	private ANcybFishData fishdata6;
	private ANcybFishData fishdata7;
	private ANcybFishData fishdata8;
	private ANcybFishData fishdata9;
	private ANcybFishData fishdata10;
	private ANcybFishData fishdata11;
	private ANcybFishData fishdata12;
	private ANcybFishData fishdata13;
	private ANcybFishData fishdata14;
	private ANcybFishData fishdata15;
	private ANcybFishData fishdata16;
	private ANcybFishData fishdata17;
	private ANcybFishData fishdata18;
	*/
	
	/*
	try {
		fishdata1 = ancybDataManager.createDataObj(str1);
		fishdata2 = ancybDataManager.createDataObj(str2);
		fishdata3 = ancybDataManager.createDataObj(str3);
		fishdata4 = ancybDataManager.createDataObj(str4);
		fishdata5 = ancybDataManager.createDataObj(str5);
		fishdata6 = ancybDataManager.createDataObj(str6);
		fishdata7 = ancybDataManager.createDataObj(str7);
		fishdata8 = ancybDataManager.createDataObj(str8);
		fishdata9 = ancybDataManager.createDataObj(str9);
		fishdata10 = ancybDataManager.createDataObj(str10);
		fishdata11 = ancybDataManager.createDataObj(str11);
		fishdata12 = ancybDataManager.createDataObj(str12);
		fishdata13 = ancybDataManager.createDataObj(str13);
		fishdata14 = ancybDataManager.createDataObj(str14);
		fishdata15 = ancybDataManager.createDataObj(str15);
		fishdata16 = ancybDataManager.createDataObj(str16);
		fishdata17 = ancybDataManager.createDataObj(str17);
		fishdata18 = ancybDataManager.createDataObj(str18);			
	} catch (MqttStringMismatch | ArrayIndexOutOfBoundsException e) {
		e.printStackTrace();
		System.err.println("Exception: " + e);
	}
	*/
	
	@BeforeAll
	public void setUpDataReceived() {
		
		ancybDataManager = new ANcybDataManager();
		testNum = 0;
		
	}

	@BeforeEach
	public void setUpDataEach() {
		try {	
			fishdata = ancybDataManager.createDataObj(strX[testNum]);
		} catch (MqttStringMismatch | ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.err.println("Exception: " + e);
		}
	}
	
	@AfterEach
	public void increment() {
		testNum = testNum + 1;
	}
	
	
	
	@Test
	@DisplayName("01Test - data generated from Ancona")
	void testDataFromAncona() {
		assertEquals("16:05:45", fishdata.getTime());
		assertEquals("Ver_G", fishdata.getVer());
		assertEquals(43.57517, ((ANcybFishData_VerG) fishdata).getLatitude(), 0.0001);
		assertEquals(13.587715,((ANcybFishData_VerG) fishdata).getLongitude(), 0.0001);
		assertEquals(Time.currentDate(),((ANcybFishData) fishdata).getDate());
	}

	@Test
	@DisplayName("02Test - data generated from New York")
	void testDataFromNewYork() {
		assertEquals("16:05:50", fishdata.getTime());
		assertEquals("Ver_GT", fishdata.getVer());
		assertEquals(40.52321, ((ANcybFishData_VerGT) fishdata).getLatitude(), 0.0001);
		assertEquals(-74.023125,((ANcybFishData_VerGT) fishdata).getLongitude(), 0.0001);
		assertEquals(10.5, ((ANcybFishData_VerGT) fishdata).getTemp(), 0.1);
		assertEquals(Time.currentDate(),((ANcybFishData) fishdata).getDate());
	}
	
	@Test
	@DisplayName("03Test - data generated from Sidney")
	void testDataFromSidney() {
		assertEquals("16:05:55", fishdata.getTime());
		assertEquals("Ver_GT", fishdata.getVer());
		assertEquals(-34.01626, ((ANcybFishData_VerGT) fishdata).getLatitude(), 0.0001);
		assertEquals(151.845730,((ANcybFishData_VerGT) fishdata).getLongitude(), 0.0001);
		assertEquals(5.3, ((ANcybFishData_VerGT) fishdata).getTemp(), 0.1);
		assertEquals(Time.currentDate(),((ANcybFishData) fishdata).getDate());
	}

}
