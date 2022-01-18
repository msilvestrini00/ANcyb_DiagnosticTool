package it.univpm.ancyb_diagnosticTool.filters;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;

/*
 * <b>Interfaccia</b> che modella il filtro generico, esplicandone i metodi.
 * 
 * @author Giacomo Fiara
 * @author Manuele Silvestrini
 */
public interface FilterInterface {
	
	/*
	 * <b>Intestazione</b> del metodo che restituisce il dato da filtrare.
	 */
	public Object getDataToFilter();
	
	/*
	 * <b>Intestazione</b> del metodo che restituisce il dato filtrato.
	 * @throws FilterFailure
	 */
	public Object getFilteredData() throws FilterFailure;
	
	/*
	 * <b>Intestazione</b> del metodo che contiene il codice di filtraggio.
	 * @throws FilterFailure
	 */
	public void computeFilter() throws FilterFailure;

}
