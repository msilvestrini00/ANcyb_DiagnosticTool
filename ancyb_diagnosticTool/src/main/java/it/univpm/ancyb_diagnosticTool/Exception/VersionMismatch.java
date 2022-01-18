package it.univpm.ancyb_diagnosticTool.Exception;

/**
 * <b>Eccezione</b> utile per segnalare situazioni in cui la versione di un'istanza {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} 
 * non coincide con quella attesa.
 * @author Giacomo Fiara
 */
public class VersionMismatch extends Exception {
	
	/**
	 * serialVersionUID di VersionMismatch
	 */
	private static final long serialVersionUID = 1L;

	public VersionMismatch() {
		super();
	}
	
	/**
	 * @param msg Messaggio di errore per specificare il problema verificatosi.
	 */
	public VersionMismatch(String msg) {
		super(msg);
	}
}
