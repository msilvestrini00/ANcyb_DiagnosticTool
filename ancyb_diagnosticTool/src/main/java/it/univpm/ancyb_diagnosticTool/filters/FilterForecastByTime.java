package it.univpm.ancyb_diagnosticTool.filters;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;

/*
 * <b>Filtro</b> che implementa l'interfaccia {@link it.univpm.ancyb_diagnosticTool.filters.FilterInterface}, usato per trovare un oggetto ForecastObject nel 'contenitore' Forecast
 * il quale dato di tempo corrisponde con quello inserito.
 * 
 * @implements FilterInterface
 * @see it.univpm.ancyb_diagnosticTool.filters.FilterInterface
 * 
 * @author Manuele Silvestrini
 */
public class FilterForecastByTime implements FilterInterface {
	
	private Forecast forecastToFilter;
	private String time;
	private ForecastObject filteredForecast ;

	/*
	 * <b>Costruttore</b> del filtro.
	 * @param forecastToFilter Oggetto
	 * {@link it.univpm.ancyb_diagnosticTool.model.Forecast#Forecast(ArrayList) Forecast}
	 * in ingresso al filtro.
	 * @param time Parametro che contiene il tempo per cui si cerca il
	 * {@link it.univpm.ancyb_diagnosticTool.model.ForecastObject#ForecastObject(String, float, float, String, float, float) ForecastObject}.
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
			throw new FilterFailure("FilterFailure(FilterForecastByTime) --> please, first invoke the method 'computeFilter()'.");
		}
		return filteredForecast;	
	}
 
	/*
	 * <b>Metodo</b> che scorre tutti gli elementi dell'ArrayList contenuti nell'oggetto 
	 * {@link it.univpm.ancyb_diagnosticTool.model.Forecast#Forecast(ArrayList) Forecast}
	 * e controlla se il loro tempo coincide con quello inserito nella definizione del costruttore.
	 * In caso affermativo, restituisce il 
	 * {@link it.univpm.ancyb_diagnosticTool.model.ForecastObject#ForecastObject(String, float, float, String, float, float) ForecastObject}
	 * trovato al costruttore.
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
			throw new FilterFailure("FilterFaiulure(FilterForecastByTime) --> No forecast with time: " + this.time + " has been found.");
		}
		
	}
}
