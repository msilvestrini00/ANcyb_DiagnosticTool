package it.univpm.ancyb_diagnosticTool.filters;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;

/*
 * Interfaccia che modella il filtro generico, esplicandone i metodi.
 * 
 * @author Giacomo Fiara
 * @author Manuele Silvestrini
 */
public interface FilterInterface {
	
	/*
	 * Metodo che restituisce il dato da filtrare.
	 */
	public Object getDataToFilter();
	
	/*
	 * Metodo che restituisce il dato filtrato.
	 * @throws FilterFailure
	 */
	public Object getFilteredData() throws FilterFailure;
	
	/*
	 * Metodo che contiene il codice di filtraggio.
	 * @throws FilterFailure
	 */
	public void computeFilter() throws FilterFailure;

}
