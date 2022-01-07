package it.univpm.ancyb_diagnosticTool.filters;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;

public interface FilterInterface {
	public Object getDataToFilter();
	public Object getFilteredData() throws FilterFailure;
	public void computeFilter();	// FAI FILTERFAILURE QUI, NON SU GETFILTEREDDATA

}
