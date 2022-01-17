package it.univpm.ancyb_diagnosticTool.filters;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;

/*
 * Filtro che implementa la relativa interfaccia, usato per trovare un oggetto ForecastObject nel 'contenitore' Forecast
 * il quale dato di tempo corrisponde con quello inserito.
 * 
 * @implements FilterInterface
 * @see it.univpm.ancyb_diagnosticTool.filters.FilterInterface
 * 
 * @author Giacomo Fiara
 * @author Manuele Silvestrini
 * 
 */
public class FilterForecastByTime implements FilterInterface {
	
	private Forecast forecastToFilter;
	private String time;
	private ForecastObject filteredForecast ;

	/*
	 * Costruttore del filtro.
	 * @param forecastToFilter Oggetto Forecast in ingresso al filtro
	 * @param time Parametro che contiene il tempo per cui si cerca il ForecastObject
	 */
	public FilterForecastByTime(Forecast forecastToFilter, String time) {
		
		this.forecastToFilter = forecastToFilter;
		this.time = time;
		this.filteredForecast = null;
 }

	/*
	 * @see it.univpm.ancyb_diagnosticTool.filters.FilterInterface#getDataToFilter()
	 */
	@Override
	public Forecast getDataToFilter() {
		return this.forecastToFilter;
	}
	
	/*
	 * @throws FilterFailure
	 * @see it.univpm.ancyb_diagnosticTool.filters.FilterInterface#getFilteredData()
	 */
	@Override
	public ForecastObject getFilteredData() throws FilterFailure{	
		
		if(filteredForecast == null) {
			throw new FilterFailure("Filter not yet computed. Please, first invoke the method 'computeFilter()'.");
		}
		return filteredForecast;	
	}
 
	/*
	 * Metodo che scorre tutti gli elementi dell'ArrayList contenuto nell'oggetto Forecast
	 * e controlla se il loro tempo coincide con quello inserito nella definizione del costruttore.
	 * In caso affermativo, restituisce il ForecastObject trovato in uscita.
	 * In caso negativo lancia l'eccezione apposita.
	 * 
	 * @throws FilterFailure
	 * @see it.univpm.ancyb_diagnosticTool.filters.FilterInterface#computeFilter()
	 */
	@Override
	public void computeFilter() throws FilterFailure {
				
		for(ForecastObject fobj : forecastToFilter.getForecastList()) {
					
			if(fobj.getForecastTime().equals(this.time)) {
				this.filteredForecast = fobj;
				break;
			}
		}
		
		if(filteredForecast == null) {
			throw new FilterFailure("No forecast with time: " + this.time + " has been found.");
		}
		
	}
}
