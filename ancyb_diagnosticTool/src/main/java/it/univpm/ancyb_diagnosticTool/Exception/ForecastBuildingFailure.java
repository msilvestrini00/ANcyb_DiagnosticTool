package it.univpm.ancyb_diagnosticTool.Exception;

/**
 * <b>Eccezione</b> che segnala un errore durante la costruzione dell'oggetto 'Forecast'.
 * 
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


