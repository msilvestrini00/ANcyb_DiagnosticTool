package it.univpm.ancyb_diagnosticTool.stats;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;
import it.univpm.ancyb_diagnosticTool.utilities.CheckVersion;

public class AverageTemperatureFish implements StatsInterface {

	private ArrayList<ANcybFishData> dataForStats;
	private String stats;
	private float avgTemp;

	public AverageTemperatureFish(ArrayList<ANcybFishData> dataForStats) {
		this.dataForStats = dataForStats;
		this.stats = null;
	}
	
	@Override
	public Object getDataForStats() {
		return dataForStats;
	}

	@Override
	public String getStats() throws StatsFailure {
		if ( stats == null) throw new StatsFailure("No stat computed --> before 'getStats()' invoke the method 'computeStats()'");
		return stats;
	}

	@Override
	public void computeStats() throws StatsFailure {
		try {
			CheckVersion.verGT(dataForStats);
		} catch (VersionMismatch e) {
			System.err.println("Deep Exception: " + e);
			throw new StatsFailure("Impossible to compute stats --> this collection doesn't contain position data");
		}
		
		float sum = 0;
		for(ANcybFishData fishData: dataForStats) {
			sum += ((ANcybFishData_VerGT) fishData).getTemp();
		}
		avgTemp = sum/dataForStats.size();
		stats = avgTemp + " °C";
	}

	public float getStatsFloat() throws StatsFailure {
		if(stats == null) throw new StatsFailure("No stat computed --> before 'getStatsFloat()' invoke the method 'computeStats()'");
		return avgTemp;
	}
}
