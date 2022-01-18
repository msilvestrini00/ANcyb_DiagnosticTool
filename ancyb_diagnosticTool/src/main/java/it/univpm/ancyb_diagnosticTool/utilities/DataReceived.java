package it.univpm.ancyb_diagnosticTool.utilities;

/**
 * <b>Interfaccia</b> che implementa classi di dati utilizzate ricevute dall'applicativo.
 * 
 * @author Giacomo Fiara
 *
 */
public interface DataReceived {
	
	public String getDate();
	public void setDate(String date);
	public String getTime();
	public void setTime(String time);
	public String toString();
	
}
