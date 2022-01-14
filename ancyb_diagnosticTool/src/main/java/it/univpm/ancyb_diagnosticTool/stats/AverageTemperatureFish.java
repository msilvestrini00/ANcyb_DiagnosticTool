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
		if ( stats == null) throw new StatsFailure("Nessuna statistica elaborata, invocare prima la funzione computeStats()");
		return stats;
	}

	@Override
	public void computeStats() throws StatsFailure, VersionMismatch {
		CheckVersion.verGT(dataForStats);
		float sum = 0;
		for(ANcybFishData fishData: dataForStats) {
			sum += ((ANcybFishData_VerGT) fishData).getTemp();
		}
		float avgTemp = sum/dataForStats.size();
		stats = avgTemp + " °C";
	}

}
