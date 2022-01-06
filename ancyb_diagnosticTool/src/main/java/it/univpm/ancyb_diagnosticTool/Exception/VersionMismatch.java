package it.univpm.ancyb_diagnosticTool.Exception;

public class VersionMismatch extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VersionMismatch() {
		super();
	}
	
	public VersionMismatch(String msg) {
		super(msg);
	}
}
