package it.univpm.ancyb_diagnosticTool.Exception;

/**
 * <b>Eccezione</b> utile per segnalare situazioni in cui si verificano errori di ricezione o conversione di coordinate GPS (Latitudine e GPS).
 * Vedi in {@link it.univpm.ancyb_diagnosticTool.utilities.Coord Coord}.
 * @author Giacomo Fiara
 */
public class WrongCoordFormat extends Exception {

	/**
	 * serialVersionUID di WrongCoordFormat
	 */
	private static final long serialVersionUID = 1L;
	
	public WrongCoordFormat() {
		super();
	}
	
	/**
	 * @param msg Messaggio di errore per specificare il problema verificatosi.
	 */
	public WrongCoordFormat(String msg) {
		super(msg);
	}
	
}
