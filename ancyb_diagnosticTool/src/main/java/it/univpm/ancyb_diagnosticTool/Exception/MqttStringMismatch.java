package it.univpm.ancyb_diagnosticTool.Exception;

public class MqttStringMismatch extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MqttStringMismatch() {
		super();
	}
	
	public MqttStringMismatch(String msg) {
		super(msg);
	}

}
