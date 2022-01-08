package it.univpm.ancyb_diagnosticTool.stats;

import java.util.ArrayList;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT;
import it.univpm.ancyb_diagnosticTool.utilities.IsVersion;

public class StatsFishData implements StatsInterface {
	
	private ArrayList<ANcybFishData> dataForStats;
	private JSONObject stats;

	public StatsFishData(ArrayList<ANcybFishData> dataForStats) {
		this.dataForStats = dataForStats;
		this.stats = null;
	}

	@Override
	public Object getDataForStats() {
		// TODO Auto-generated method stub
		return this.dataForStats;
	}

	@Override
	public JSONObject getStats() throws StatsFailure {
		if ( stats == null) throw new StatsFailure("Nessuna statistica elaborata, invocare prima la funzione computeStats()");
		return this.stats;
	}

	@Override
	public void computeStats() {
		
		//TODO guarda il preferito sul browser
		double avgMoving = disgeod( this.dataForStats.get(0).getCoord(), this.dataForStats.get(this.dataForStats.size()-1).getCoord());
		
		if(IsVersion.verGT(this.dataForStats)) {
			float sum = 0;
			for(ANcybFishData fishData: dataForStats) {
				sum += ((ANcybFishData_VerGT) fishData).getTemp();
			}
			//TODO float su int dà un float?
			float avgTemp = sum/dataForStats.size();
		}	
		
	}

}
