package it.univpm.ancyb_diagnosticTool.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;
import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.DataSaved;
import it.univpm.ancyb_diagnosticTool.stats.AverageTemperatureFish;
import it.univpm.ancyb_diagnosticTool.stats.GeodeticDistance;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

/**
 * 
 * @author Giacomo Fiara
 *
 */
class FishDataManagerTest {

	static ANcybDataManager ancybDataManager;
	private static String[] strX;
	@SuppressWarnings("unused")
	static private ANcybFishData fishdata;
	
	private ANcybFishData fishdataObt;
	private ArrayList<ANcybFishData> fishListObt;
	private double statsDoubleObt;
	private AverageTemperatureFish averageTemperature;
	private static AncybDiagnosticToolService service;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		strX = new String[]{
				"a4:cf:12:76:76:95 Ver_G 16:05:45 4334.5102N 01335.2629E 1",
				"b4:cf:12:76:76:95 Ver_GT 16:05:50 4031.3926N 07401.3875W 1 10.5",
				"c4:cf:12:76:76:95 Ver_GT 16:05:55 3400.9756S 15150.7438E 1 5.3",
				"a4:cf:12:76:76:95 Ver_G 16:06:00 4334.5110N 01335.2629E 1",
				"b4:cf:11:76:76:95 Ver_GT 16:06:05 4031.3924N 07401.3876W 1 11.3",
				"c4:cf:12:76:76:95 Ver_GT 16:06:10 3400.9756S 15150.7438E 1 4.8",
				"a4:cf:12:76:76:95 Ver_G 16:06:15 4334.5130N 01335.2628E 1",
				"b4:cf:12:76:76:95 Ver_GT 16:06:20 4031.3922N 07401.3876W 1 10.2",
				"c4:cf:12:76:76:95 Ver_GT 16:06:25 3400.9756S 15150.7438E 1 6.1",
				"a4:cf:12:76:76:95 Ver_G 16:06:30 4334.5120N 01335.2627E 1",
				"b4:cf:11:76:76:95 Ver_GT 16:06:35 4031.3915N 07401.3877W 1 10.5",
				"c4:cf:12:76:76:95 Ver_GT 16:06:40 3400.9756S 15150.7438E 1 4.5",
				"a4:cf:12:76:76:95 Ver_G 16:06:45 4334.5125N 01335.2626E 1",
				"b4:cf:12:76:76:95 Ver_GT 16:06:50 4031.3910N 07401.3880W 1 10.8",
				"c4:cf:12:76:76:95 Ver_GT 16:06:55 3400.9756S 15150.7438E 1 5",
				"a4:cf:12:76:76:95 Ver_G 16:07:00 4334.5136N 01335.2622E 1",
				"b4:cf:12:76:76:95 Ver_GT 16:07:05 4031.3910N 07401.3884W 1 10.5",
				"c4:cf:12:76:76:95 Ver_GT 16:07:10 3400.9756S 15150.7438E 1 5.1",
		};
		
		ancybDataManager = new ANcybDataManager();
		service = new AncybDiagnosticToolServiceImpl();
		
	}
	
	@BeforeEach
	void setUp() throws Exception {
		try {
			for(int i=0; i<strX.length; i++) {
				fishdata = ancybDataManager.createDataObj(strX[i]);
			}
		} catch (MqttStringMismatch | ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.err.println("Exception: " + e);
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		DataSaved.getList().clear();
	}		

	@Test
	@DisplayName("Test n.1 - test FilterObj")
	void testFilterObj() {
	
		try {
			fishdataObt = service.getLatestPositionByMac("a4:cf:12:76:76:95");
		} catch (VersionMismatch | FilterFailure e) {
			e.printStackTrace();
		}
		
		assertEquals(Time.currentDate(), fishdataObt.getDate());
		assertEquals("a4:cf:12:76:76:95", fishdataObt.getMacAddr());
		assertEquals("16:07:00", fishdataObt.getTime());
		assertEquals("Ver_G", fishdataObt.getVer());
		assertEquals(43.575227, ((ANcybFishData_VerG) fishdataObt).getLatitude(), 0.0001);
		assertEquals(13.587703, ((ANcybFishData_VerG) fishdataObt).getLongitude(), 0.0001);
		
	}
	
	@Test
	@DisplayName("Test n.2 - test FilterObj Exception")
	void testFilterObjException() {
	
		Exception exception = assertThrows(FilterFailure.class, () -> {
			fishdataObt = service.getLatestPositionByMac("d4:cf:12:76:76:95");
	    });
		
		String expectedMessage = "No fishData in 'DataSaved' with the following MAC address";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	
	}
	
	@Test
	@DisplayName("Test n.3 - test FilterList")
	void testFilterList() {
	
		try {
			fishListObt = service.getAllResultsByMac("a4:cf:12:76:76:95");
		} catch (FilterFailure e) {
			e.printStackTrace();
		}
		
		assertEquals(Time.currentDate(), fishListObt.get(0).getDate());
		assertEquals("a4:cf:12:76:76:95", fishListObt.get(0).getMacAddr());
		assertEquals("16:05:45", fishListObt.get(0).getTime());
		assertEquals("Ver_G", fishListObt.get(0).getVer());
		assertEquals(43.57517, ((ANcybFishData_VerG) fishListObt.get(0)).getLatitude(), 0.0001);
		assertEquals(13.587715, ((ANcybFishData_VerG) fishListObt.get(0)).getLongitude(), 0.0001);
		assertEquals(Time.currentDate(), fishListObt.get(5).getDate());
		assertEquals("a4:cf:12:76:76:95", fishListObt.get(5).getMacAddr());
		assertEquals("16:07:00", fishListObt.get(5).getTime());
		assertEquals("Ver_G", fishListObt.get(5).getVer());
		assertEquals(43.575227, ((ANcybFishData_VerG) fishListObt.get(5)).getLatitude(), 0.0001);
		assertEquals(13.587703, ((ANcybFishData_VerG) fishListObt.get(5)).getLongitude(), 0.0001);

	}
	
	@Test
	@DisplayName("Test n.4 - test FilterList Exception")
	void testFilterListException() {
	
		Exception exception = assertThrows(FilterFailure.class, () -> {
		fishListObt = service.getAllResultsByMac("d4:cf:12:76:76:95");
	    });
		
		String expectedMessage = "FilterFailure(FilterListByMac) --> No fishData in 'DataSaved' with the following MAC address:";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));

	}
	
	@Test
	@DisplayName("Test n.5 - test Stats")
	void testStats() {
	
		try {
			fishListObt = service.getAllResultsByMac("a4:cf:12:76:76:95");
			GeodeticDistance distance = new GeodeticDistance(fishListObt);
			distance.computeStats();
			statsDoubleObt = distance.getStatsDouble();
		} catch (FilterFailure | StatsFailure e) {
			e.printStackTrace();
		}
		
		assertEquals(0.006407, statsDoubleObt, 0.0001);
	}
	
	@Test
	@DisplayName("Test n.6 - test Stats Exception")
	void testStatsException() {
	
		try {
			fishListObt = service.getAllResultsByMac("a4:cf:12:76:76:95");
			averageTemperature = new AverageTemperatureFish(fishListObt);
		} catch (FilterFailure e) {
			e.printStackTrace();
		}
		
		Exception exception = assertThrows(StatsFailure.class, () -> {
			averageTemperature.computeStats();
		});
		
		String expectedMessage = "StatsFailure(AverageTemperatureFish) --> the data for stats do not contain temperature Infos.";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));

	}

}
