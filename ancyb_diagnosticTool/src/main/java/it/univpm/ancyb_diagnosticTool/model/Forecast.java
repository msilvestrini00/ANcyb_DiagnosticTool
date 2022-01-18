package it.univpm.ancyb_diagnosticTool.model;

import java.util.ArrayList;

/*
 * <b>Classe</b> che descrive il modello del contenitore di 'oggetti di previsione meteorologica oraria',
 * derivanti da quanto ricevuto dall'API esterna 'Storm Glass'.
 * 
 * Link all'API esterna: https://rapidapi.com/ManniskaMaskin/api/storm-glass/
 * 
 * @see it.univpm.ancyb_diagnosticTool.model.ForecastObject
 * 
 * @author Manuele Silvestrini
 */
public class Forecast {

	/*
	 * ArrayList che contiene gli oggetti {@link ForecastObject#ForecastObject(String, float, float, String, float, float) ForecastObject} creati successivamente alla chiamata all'API esterna.
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.service.ForecastDataManager#getForecast()
	 */
	private ArrayList<ForecastObject> forecastList = new ArrayList<ForecastObject>();
	
	/*
	 * <b>Costruttore</b> a cui viene assegnato l'ArrayList come attributo.
	 */
	public Forecast(ArrayList<ForecastObject> forecastList) {
		this.forecastList = forecastList;
	}
	
	/*
	 * @return L'intero ArrayList dei ForecastObject. 
	 */
	public ArrayList<ForecastObject> getForecastList() {
		return this.forecastList;
	}
	
	/*
	 * @return La dimensione dell'ArrayList.
	 */
	public long getForecastLength() {
		return this.forecastList.size();
	}
	
	/*
	 * <b>Metodo</b> che aggiunge all'ArrayList di Forecast un oggetto ForecastObject.
	 * @param obj Oggetto ForecastObject da aggiungere all'ArrayList.
	 */
	public void addToForecast(ForecastObject obj) {
		forecastList.add(obj);
	}
	
	/*
	 * <b>Metodo</b> che restituisce un ForecastObject corrispondente all'indice dell'ArrayList immesso.
	 * @param index Indice del ForecastObject da restituire.
	 * @return Il ForecastObject con quel determinato indice.
	 */
	public ForecastObject getForecastObject(int index) {
		return this.forecastList.get(index);
	}
	
	/*
	 * @return  L'indirizzo mac del dispositivo la quale posizione è utilizzata per effettuare la chiamata all'API esterna.
	 */
	public String getForecastMacAddr() {
		return forecastList.get(0).getMacAddress();
	}
		
}
