package it.univpm.ancyb_diagnosticTool.stats;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.utilities.CheckVersion;
import it.univpm.ancyb_diagnosticTool.utilities.Coord;

/**
 * <b>Statistica</b> che implementa l'interfaccia 
 * {@link it.univpm.ancyb_diagnosticTool.stats.StatsInterface StatsInterface},
 * usata per calcolare la distanza geodetica sommata per ciascun dato di posizione inviato dal dispositivo.
 * 
 * @author Giacomo Fiara
 * 
 * @see {@link it.univpm.ancyb_diagnosticTool.utilities.Coord#disgeod(float[], float[]) Coord.disgeod(float[], float[])
 */
public class GeodeticDistance implements StatsInterface {
	
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
	private double distanceTraveled;

	/**
	 * <b>Costruttore</b> della statistica.
	 * @param dataForStats ArrayList di dati {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} per i quali si vuole effettuare la statistica.
	 */
	public GeodeticDistance(ArrayList<ANcybFishData> dataForStats) {
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
	 * @return la distanza geodetica sommata per ciascun dato di posizione inviato dal dispositivo.
	 * @throws StatsFailure
	 */
	@Override
	public String getStats() throws StatsFailure {
		if ( stats == null ) throw new StatsFailure("StatsFailure(GeodeticDistance) --> please, first invoke the method 'computeStats()'.");
		return stats;
	}

	/**
	 * <b>Metodo</b> che scorre tutti gli elementi dell'ArrayList contenuti nell'oggetto, controlla
	 * la loro versione ({@link it.univpm.ancyb_diagnosticTool.utilities.CheckVersion CheckVersion}) e infine 
	 * effettua la somma punto per punto della distanza geodetica tra punti adiacenti ({@link it.univpm.ancyb_diagnosticTool.utilities.Coord#disgeod(float[], float[]) Coord.disgeod(float[], float[])}).
	 */
	@Override
	public void computeStats() throws StatsFailure {
		try {
			CheckVersion.verG(dataForStats);
		} catch (VersionMismatch e) {
			throw new StatsFailure("StatsFailure(GeodeticDistance) --> the data for stats do not contain position Infos.\n" + "Deep Exception: " + e);
		}
		//TODO da fare la cosa punto a punto dopo aver testato
		distanceTraveled = Coord.disgeod( ((ANcybFishData_VerG) dataForStats.get(0)).getCoord(), ((ANcybFishData_VerG) dataForStats.get(dataForStats.size()-1)).getCoord());
		/*
		for(int i=0; i<dataForStats.size()-1; i++) {
			distanceTraveled = distanceTraveled + Coord.disgeod( ((ANcybFishData_VerG) dataForStats.get(i)).getCoord(), ((ANcybFishData_VerG) dataForStats.get(i+1)).getCoord());
		}
		*/
		this.stats = String.format("%.2f", distanceTraveled) + " m";
	}

	/**
	 * <b>Metodo</b> che restituisce la statistica effettuata in formato 'double'.
	 * @return la distanza geodetica sommata per ciascun dato di posizione inviato dal dispositivo.
	 * @throws StatsFailure
	 */
	public double getStatsDouble() throws StatsFailure {
		if(stats == null) throw new StatsFailure("StatsFailure(GeodeticDistance) --> please, first invoke the method 'computeStats()'.");
		return distanceTraveled;
	}
}
