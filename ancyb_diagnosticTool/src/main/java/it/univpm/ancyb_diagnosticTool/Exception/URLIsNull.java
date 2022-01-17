package it.univpm.ancyb_diagnosticTool.Exception;

/**
 * Eccezione utilizzata per segnalare che l'URL non è stato generato, quindi è nullo.
 * 
 * @author Giacomo Fiara
 * @author Manuele Silvestrini
 */
public class URLIsNull extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public URLIsNull() {
		super();
	}

	/*
	 * @param msg Messaggio di errore per specificare il problema verificatosi.
	 */
	public URLIsNull(String msg) {
		super(msg);
	}
}
