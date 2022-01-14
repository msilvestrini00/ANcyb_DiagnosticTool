package it.univpm.ancyb_diagnosticTool.stats;

import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;

public interface StatsInterface {


		public Object getDataForStats();
		public Object getStats() throws StatsFailure;
		public void computeStats() throws StatsFailure, VersionMismatch;	
		
}
