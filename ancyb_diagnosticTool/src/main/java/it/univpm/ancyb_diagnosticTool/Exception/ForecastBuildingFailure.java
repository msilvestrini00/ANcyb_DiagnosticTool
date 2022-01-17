package it.univpm.ancyb_diagnosticTool.Exception;

/**
 * Eccezione che segnala un errore durante la costruzione dell'oggetto 'Forecast'.
 * 
 * @author Giacomo Fiara
 * @author Manuele Silvestrini
 */
public class ForecastBuildingFailure extends Exception {

	private static final long serialVersionUID = 1L;

	public ForecastBuildingFailure() {
		super();
	}
	
	/*
	 * @param msg Messaggio di errore per specificare il problema verificatosi.
	 */
	public ForecastBuildingFailure(String msg) {
		super(msg);
	}
}


