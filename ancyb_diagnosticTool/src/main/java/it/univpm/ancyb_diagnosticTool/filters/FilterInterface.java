package it.univpm.ancyb_diagnosticTool.filters;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;

public interface FilterInterface {
	public Object getDataToFilter();
	public Object getDataFiltered() throws FilterFailure;
	
}
