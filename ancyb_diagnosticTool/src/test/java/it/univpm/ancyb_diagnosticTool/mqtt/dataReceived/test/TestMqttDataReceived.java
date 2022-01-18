package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;
import it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;
import it.univpm.ancyb_diagnosticTool.utilities.Coord;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

/**
 * 
 * @author Giacomo Fiara
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class TestMqttDataReceived {
	
	static ANcybDataManager ancybDataManager;
	private ANcybFishData fishdata;
	int testNum = 0;
	private static String[] strX;
	private float longitude;	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		strX = new String[]{
				"a4:cf:12:76:76:95 Ver_G 16:05:45 4334.5102N 01335.2629E 1",
				"b4:cf:12:76:76:95 Ver_GT 16:05:50 4031.3926N 07401.3875W 1 10.5",
				"c4:cf:12:76:76:95 Ver_GT 16:05:55 3400.9756S 15150.7438E 1 5.3",
				"9734.5110N",
				"d4:cf:12:76:76:95 Ver_G 16:06:00 4334.5110N 18135.e629E 1",
		};
		
		ancybDataManager = new ANcybDataManager();
		
	}

	@BeforeEach
	void setUp() throws Exception {
		
		try {
			if(testNum<3) {
				fishdata = ancybDataManager.createDataObj(strX[testNum]);	
			}
		} catch (MqttStringMismatch | ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.err.println("Exception: " + e);
		}
	}
	
	@AfterEach
	void tearDown()  throws Exception  {
		testNum = testNum + 1;
	}
	
	@Test
	@Order(1)
	@DisplayName("Test n.1 - data generated from Ancona")
	void test1DataFromAncona() {
		assertEquals("16:05:45", fishdata.getTime());
		assertEquals("Ver_G", fishdata.getVer());
		assertEquals(43.57517, ((ANcybFishData_VerG) fishdata).getLatitude(), 0.0001);
		assertEquals(13.587715, ((ANcybFishData_VerG) fishdata).getLongitude(), 0.0001);
		assertEquals(Time.currentDate(), ((ANcybFishData) fishdata).getDate());
	}

	@Test
	@Order(2)
	@DisplayName("Test n.2 - data generated from New York")
	void test2DataFromNewYork() {
		assertEquals("16:05:50", fishdata.getTime());
		assertEquals("Ver_GT", fishdata.getVer());
		assertEquals(40.52321, ((ANcybFishData_VerGT) fishdata).getLatitude(), 0.0001);
		assertEquals(-74.023125,((ANcybFishData_VerGT) fishdata).getLongitude(), 0.0001);
		assertEquals(10.5, ((ANcybFishData_VerGT) fishdata).getTemp(), 0.1);
		assertEquals(Time.currentDate(),((ANcybFishData) fishdata).getDate());
	}
	
	@Test
	@Order(3)
	@DisplayName("Test n.3 - data generated from Sidney")
	void test3DataFromSidney() {
		assertEquals("16:05:55", fishdata.getTime());
		assertEquals("Ver_GT", fishdata.getVer());
		assertEquals(-34.01626, ((ANcybFishData_VerGT) fishdata).getLatitude(), 0.0001);
		assertEquals(151.845730,((ANcybFishData_VerGT) fishdata).getLongitude(), 0.0001);
		assertEquals(5.3, ((ANcybFishData_VerGT) fishdata).getTemp(), 0.1);
		assertEquals(Time.currentDate(),((ANcybFishData) fishdata).getDate());
	}
	
	@Test
	@Order(4)
	@DisplayName("Test n.4 - test exception generated by latitude greater than 90°")
	void test4Exception() {
		Exception exception = assertThrows(WrongCoordFormat.class, () -> {
			longitude = Coord.latDDMstringToDDfloat(strX[testNum]);
			System.out.println(longitude);
		});
		
		String expectedMessage = "angle not included between -90° and 90°";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	@Order(5)
	@DisplayName("Test n.5 - test exception generated by alphanumerical longitude")
	void test3DataException() {
		Exception exception = assertThrows(MqttStringMismatch.class, () -> {
			fishdata = ancybDataManager.createDataObj(strX[testNum]);
		});
		
		String expectedMessage = "MqttStringMismatch(Ver_G constructor) -->";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}


	
}
