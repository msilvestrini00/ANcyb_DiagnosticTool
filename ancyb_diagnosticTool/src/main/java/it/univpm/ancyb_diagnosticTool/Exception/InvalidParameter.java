package it.univpm.ancyb_diagnosticTool.Exception;

/**
 * Eccezione che segnala l'invalidità di eventuali parametri in ingresso.
 * 
 * @author Giacomo Fiara
 * @author Manuele Silvestrini
 */
public class InvalidParameter extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidParameter() {
		super();
	}
	
	/*
	 * @param msg Messaggio di errore per specificare il problema verificatosi.
	 */
	public InvalidParameter(String msg) {
		super(msg);
	}

}
