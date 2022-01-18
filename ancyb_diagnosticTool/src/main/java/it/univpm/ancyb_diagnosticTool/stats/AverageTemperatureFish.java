package it.univpm.ancyb_diagnosticTool.stats;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;
import it.univpm.ancyb_diagnosticTool.utilities.CheckVersion;

/**
 * <b>Statistica</b> che implementa l'interfaccia 
 * {@link it.univpm.ancyb_diagnosticTool.stats.StatsInterface StatsInterface},
 * usata per calcolare la temperatura media su tutti i dati {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}
 * passati come parametro.
 * 
 * @author Giacomo Fiara
 */
public class AverageTemperatureFish implements StatsInterface {

	/**
	 * ArrayList di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} da elaborare per le statistiche.
	 */
	private ArrayList<ANcybFishData> dataForStats;
	
	/**
	 * Stinga restituita come risultato della statistica.
	 */
	private String stats;
	
	/**
	 * Risultato della statistica
	 */
	private float avgTemp;

	/**
	 * <b>Costruttore</b> della statistica.
	 * @param dataForStats ArrayList di dati {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} per i quali si vuole effettuare la statistica.
	 */
	public AverageTemperatureFish(ArrayList<ANcybFishData> dataForStats) {
		this.dataForStats = dataForStats;
		this.stats = null;
	}
	
	/**
	 * <b>Metodo</b> che restituisce l'oggetto su cui effettuare la statistica.
	 */
	@Override
	public Object getDataForStats() {
		return dataForStats;
	}

	/**
	 * <b>Metodo</b> che restituisce la statistica effettuata in formato 'String'.
	 * @return la  temperatura media su tutti i dati {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}
	 * passati come parametro.
	 * @throws StatsFailure
	 */
	@Override
	public String getStats() throws StatsFailure {
		if ( stats == null) throw new StatsFailure("StatsFailure(AverageTemperatureFish) --> please, first invoke the method 'computeStats()'.");
		return stats;
	}

	/**
	 * <b>Metodo</b> che scorre tutti gli elementi dell'ArrayList contenuti nell'oggetto, controlla
	 * la loro versione ({@link it.univpm.ancyb_diagnosticTool.utilities.CheckVersion CheckVersion}) e infine 
	 * effettua la media aritmetica delle temperature memorizzate in 'dataForStats'.
	 */
	@Override
	public void computeStats() throws StatsFailure {
		try {
			CheckVersion.verGT(dataForStats);
		} catch (VersionMismatch e) {
			System.err.println("Deep Exception: " + e);
			throw new StatsFailure("StatsFailure(AverageTemperatureFish) --> the data for stats do not contain temperature Infos.");
		}
		
		float sum = 0;
		for(ANcybFishData fishData: dataForStats) {
			sum += ((ANcybFishData_VerGT) fishData).getTemp();
		}
		avgTemp = sum/dataForStats.size();
		stats = String.format("%.2f", avgTemp) + " °C";
	}

	/**
	 * <b>Metodo</b> che restituisce la statistica effettuata in formato 'float'.
	 * @return la  temperatura media su tutti i dati {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}
	 * passati come parametro.
	 * @throws StatsFailure
	 */
	public float getStatsFloat() throws StatsFailure {
		if(stats == null) throw new StatsFailure("StatsFailure(AverageTemperatureFish) --> please, first invoke the method 'computeStats()'.");
		return avgTemp;
	}
}
