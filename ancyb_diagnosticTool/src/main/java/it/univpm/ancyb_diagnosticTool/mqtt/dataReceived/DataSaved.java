package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import java.util.ArrayList;

/**
 * <b>Classe</b> che permette di stoccare automaticamenti i dati ricevuti dai dispositivi sottomarini tramite il protocollo MQTT.
 * 
 * @author Giacomo Fiara
 *
 * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData
 */
public class DataSaved {

	/**
	 * ArrayList che memorizza i dati di tipo {it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}
	 */
	private static ArrayList<ANcybFishData> list = new ArrayList<>();

	/**
	 * <b>Metodo</b> che restituisce la collezione di tutte le istanze {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} generate nella sessione.<br><br>
	 * Utilizzato ampiamente nelle stats e nei filtri.
	 * 
	 * @return l'ArrayList con salvati tutte le istanze {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} della sessione.
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.stats.AverageWaveHeight AverageTemperature
	 * @see it.univpm.ancyb_diagnosticTool.stats.GeodeticDistance GeodeticDistance
	 * @see it.univpm.ancyb_diagnosticTool.filters.FilterObjByMac FilterObjByMac
	 * @see it.univpm.ancyb_diagnosticTool.filters.FilterListByMac FilterListByMac
	 */
	public static ArrayList<ANcybFishData> getList() {
		return list;
	}

}
