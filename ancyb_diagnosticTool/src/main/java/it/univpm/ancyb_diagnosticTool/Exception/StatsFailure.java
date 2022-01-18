package it.univpm.ancyb_diagnosticTool.Exception;

/**
 * <b>Eccezione</b> utile per segnalare un malfunzionamento generico delle statistiche.
 * 
 * @author Giacomo Fiara
 */
public class StatsFailure extends Exception {

	private static final long serialVersionUID = 1L;
	
	public StatsFailure() {
		super();
	}
	
	/*
	 * @param msg Messaggio di errore per specificare il problema verificatosi.
	 */
	public StatsFailure(String msg) {
		super(msg);
	}
}
