package it.univpm.ancyb_diagnosticTool.Exception;

/**
 * <b>Eccezione</b> utile per segnalare un malfunzionamento generico dei filtri.
 * 
 * @author Giacomo Fiara
 */
public class FilterFailure extends Exception {

	private static final long serialVersionUID = 1L;
	
	public FilterFailure() {
		super();
	}
	
	/*
	 * @param msg Messaggio di errore per specificare il problema verificatosi.
	 */
	public FilterFailure(String msg) {
		super(msg);
	}
}
