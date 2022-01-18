package it.univpm.ancyb_diagnosticTool.Exception;

/**
 * <b>Eccezione</b> utilizzata per segnalare che l'URL non è stato generato, quindi è nullo.
 * 
 * @author Manuele Silvestrini
 */
public class URLIsNull extends Exception {

	private static final long serialVersionUID = 1L;
	
	public URLIsNull() {
		super();
	}

	/**
	 * @param msg Messaggio di errore per specificare il problema verificatosi.
	 */
	public URLIsNull(String msg) {
		super(msg);
	}
}
