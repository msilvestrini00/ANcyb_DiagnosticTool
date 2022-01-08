package it.univpm.ancyb_diagnosticTool.stats;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;

public interface StatsInterface {


		public Object getDataForStats();
		public JSONObject getStats() throws StatsFailure;
		public void computeStats() throws StatsFailure;	
		
}
