package it.univpm.ancyb_diagnosticTool.utilities;

/**
 * <b>Interfaccia</b> che implementa classi di dati utilizzate ricevute dall'applicativo.
 * 
 * @author Giacomo Fiara
 *
 */
public interface DataReceived {
	
	/**
	 *<b>Metodo</b> che restituisce la data attribuita all'oggetto. 
	 * @return Stringa che rappresenta la data.
	 */
	public String getDate();
	
	/**
	 * <b>Metodo</b> che imposta la data attribuita all'oggetto. 
	 * @param date Data che si vuole impostare.
	 */
	public void setDate(String date);
	
	/**
	 * <b>Metodo</b> che restituisce l'orario attribuito all'oggetto. 
	 * @return Stringa che rappresenta l'orario.
	 */
	public String getTime();
	
	/**
	 * <b>Metodo</b> che imposta l'orario attribuito all'oggetto.
	 * @param time Orario che si vuole impostare.
	 */
	public void setTime(String time);
	
}
