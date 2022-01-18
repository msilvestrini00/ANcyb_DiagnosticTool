package it.univpm.ancyb_diagnosticTool.stats;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.utilities.CheckVersion;
import it.univpm.ancyb_diagnosticTool.utilities.Coord;

public class GeodeticDistance implements StatsInterface {
	
	private ArrayList<ANcybFishData> dataForStats;
	private String stats;
	private double avgMoving;

	public GeodeticDistance(ArrayList<ANcybFishData> historyFishData) {
		this.dataForStats = historyFishData;
		this.stats = null;
	}

	@Override
	public Object getDataForStats() {
		return dataForStats;
	}

	@Override
	public String getStats() throws StatsFailure {
		if ( stats == null ) throw new StatsFailure("StatsFailure(GeodeticDistance) --> please, first invoke the method 'computeStats()'.");
		return stats;
	}

	@Override
	public void computeStats() throws StatsFailure {
		try {
			CheckVersion.verG(dataForStats);
		} catch (VersionMismatch e) {
			System.err.println("Deep Exception: " + e);
			throw new StatsFailure("StatsFailure(GeodeticDistance) --> the data for stats do not contain position Infos.");
		}
		//TODO da fare la cosa punto a punto dopo aver testato
		avgMoving = Coord.disgeod( ((ANcybFishData_VerG) dataForStats.get(0)).getCoord(), ((ANcybFishData_VerG) dataForStats.get(dataForStats.size()-1)).getCoord());
		this.stats = avgMoving + " m";
	}

	public double getStatsDouble() throws StatsFailure {
		if(stats == null) throw new StatsFailure("StatsFailure(GeodeticDistance) --> please, first invoke the method 'computeStats()'.");
		return avgMoving;
	}
}
