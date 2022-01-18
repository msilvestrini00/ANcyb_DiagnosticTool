package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;

/**
 * <b>Classe</b> di test per admin.<br>
 * @author Giacomo Fiara
 */
public class Admin {

	/**
	 * istanza di tipo {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}
	 */
	private static ANcybFishData fishdata;

	/**
	 * <b>Metodo</b> che genera 18 istanze note di cui si conoscono le reali posizioni, i risultati dei filstri e le statistiche ottenibili.<br>.<br>
	 * Gli stessi dati sono riportati nel file di testo MqttStringsFromM5.
	 * 
	 */
	public static void simulateDataReceived() throws MqttStringMismatch {
		
		String[] strX = new String[]{
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
		
		ANcybDataManager ancybDataManager = new ANcybDataManager();
		
		for(int i=0; i<strX.length; i++) {
			fishdata = ancybDataManager.createDataObj(strX[i]);
			fishdata.toString();
		}
				
	}
}
