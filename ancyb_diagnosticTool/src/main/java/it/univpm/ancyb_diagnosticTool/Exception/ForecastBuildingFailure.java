package it.univpm.ancyb_diagnosticTool.Exception;

/**
 * <b>Eccezione</b> che segnala un errore durante la costruzione dell'oggetto 
 * {@link it.univpm.ancyb_diagnosticTool.model.Forecast#Forecast(ArrayList) Forecast}.
 * @author Manuele Silvestrini
 */
public class ForecastBuildingFailure extends Exception {

	/**
	 * serialVersionUID di ForecastBuildingFailure
	 */
	private static final long serialVersionUID = 1L;

	public ForecastBuildingFailure() {
		super();
	}
	
	/**
	 * @param msg Messaggio di errore per specificare il problema verificatosi.
	 */
	public ForecastBuildingFailure(String msg) {
		super(msg);
	}
}


