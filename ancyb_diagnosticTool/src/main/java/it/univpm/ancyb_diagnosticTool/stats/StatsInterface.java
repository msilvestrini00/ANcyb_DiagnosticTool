package it.univpm.ancyb_diagnosticTool.stats;

import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;

/**
 * <b>Interfaccia</b> che modella la statistica generica, esplicandone i metodi.
 * 
 * @author Giacomo Fiara
 * @author Manuele Silvestrini
 */
public interface StatsInterface {

	/**
	 * <b>Intestazione</b> del metodo che restituisce l'oggetto su cui effettuare la statistica.
	 */
	public Object getDataForStats();
	
	/**
	 * <b>Intestazione</b> del metodo che restituisce la statistica effettuata.
	 * @throws StatsFailure
	 */
	public String getStats() throws StatsFailure;
	
	/**
	 * <b>Intestazione</b> del metodo che contiene il codice di elaborazione della statistica.
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 */
	public void computeStats() throws StatsFailure, VersionMismatch;	
		
}
