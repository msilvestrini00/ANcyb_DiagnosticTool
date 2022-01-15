package it.univpm.ancyb_diagnosticTool.stats;

import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;

public interface StatsInterface {

	/**
	 * 
	 * @return
	 */
	public Object getDataForStats();
	
	/**
	 * 
	 * @return
	 * @throws StatsFailure
	 */
	public String getStats() throws StatsFailure;
	
	/**
	 * 
	 * @throws StatsFailure
	 * @throws VersionMismatch
	 */
	public void computeStats() throws StatsFailure, VersionMismatch;	
		
}
