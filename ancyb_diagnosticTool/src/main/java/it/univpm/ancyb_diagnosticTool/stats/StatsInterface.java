package it.univpm.ancyb_diagnosticTool.stats;

import org.json.JSONObject;

public interface StatsInterface {


		public Object getDataForStats();
		public JSONObject getStats();
		public void computeStats();	// questa trowerà le eccezioni (vedi quelle dei filtri)

}
