package it.univpm.ancyb_diagnosticTool.Exception;

/**
 * <b>Eccezione</b> utile per segnalare un malfunzionamento generico nella gestione delle stringhe ricevute via MQTT.
 * 
 * @author Giacomo Fiara
 */
public class MqttStringMismatch extends Exception {

	/**
	 * serialVersionUID di MqttStringMismatch
	 */
	private static final long serialVersionUID = 1L;
	
	public MqttStringMismatch() {
		super();
	}
	
	/**
	 * @param msg Messaggio di errore per specificare il problema verificatosi.
	 */
	public MqttStringMismatch(String msg) {
		super(msg);
	}

}
