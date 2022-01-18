package it.univpm.ancyb_diagnosticTool.service;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.ForecastBuildingFailure;
import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;

/**
 * <b>Interfaccia</b> di servizio per gestire le operazioni sulle previsioni orarie e i dati ricevuti dal dispositivo.
 * 
 * @see it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolServiceImpl AncybDiagnosticToolServiceImpl
 * 
 * @author Fiara Giacomo
 * @author Silvestrini Manuele
 */
public interface AncybDiagnosticToolService {

	/**
	 * <b>Intestazione</b> del metodo che restituisce il ForecastObject in base alla data e l'ora correnti.
	 * @param macAddr Indirizzo MAC dal quale ricavo le coordinate per l'API esterna.
	 * @return Il ForecastObject relativo alla data e l'ora correnti.
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 * @throws ForecastBuildingFailure
	 * 
	 * @author Manuele Silvestrini
	 */
	public ForecastObject getForecastByRealTime(String macAddr) throws FilterFailure, VersionMismatch, ForecastBuildingFailure;				
	
	/**
	 * <b>Intestazione</b> del metodo che restituisce il ForecastObject filtrato secondo la data e l'ora inserite.
	 * @param macAddr Indirizzo MAC dal quale ricavo le coordinate per l'API esterna.
	 * @param date La data di cui si vuole ottenere la previsione.
	 * @param hour L'ora di cui si vuole ottenere la previsione.
	 * @return Il ForecastObject riguardante la data e l'ora inserite.
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 * @throws ForecastBuildingFailure
	 * 
	 * @author Manuele Silvestrini
	 */
	public ForecastObject getForecastBySelectedTime(String macAddr, String date, byte hour) throws FilterFailure, VersionMismatch, ForecastBuildingFailure;				

	/**
	 * <b>Intestazione</b> del metodo che restituisce le statistiche sulle previsioni meteorologiche,
	 * in base al numero di giorni per cui si vuole che esse siano estese.
	 * 
	 * @param macAddr Indirizzo MAC dal quale ricavo le coordinate per l'API esterna.
	 * @param days Numero di giorni per cui si vuole estendere le statistiche (a partire dal giorno corrente).
	 * @return L'oggetto JSON che contiene le statistiche.
	 * @throws StatsFailure
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 * @throws ForecastBuildingFailure
	 * 
	 * @author Manuele Silvestrini
	 */
	public JSONObject getForecastStats(String macAddr, byte days) throws StatsFailure, VersionMismatch, FilterFailure, ForecastBuildingFailure;				

	/**
	 * <b>Intestazione</b> del metodo che restituisce l'ultima istanza di tipo {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}
	 *  relativa all'indirizzo MAC desiderato.
	 *  
	 * @param macAddr MAC address del dispositivo da cui si vuole ottenere l'ulima istanza tra i dati inviati.
	 * @return ultima istanza ({@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}) ricevuta corrispondente al MAC address.
	 * @throws FilterFailure nel caso la ricerca via MAC address non restituisce alcuna istanza.
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.filters.FilterListByMac FilterObjByMac
	 * 
	 * @author Giacomo Fiara
	 */
	ANcybFishData getLatestResultByMac(String macAddr) throws FilterFailure;
	
	/**
	 * <b>Intestazione</b> del metodo che restituisce l'ArrayList di istanze di tipo {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}
	 *  relative all'indirizzo MAC desiderato.
	 *  
	 * @param macAddr MAC address del dispositivo da cui si vogliono ottenere tutte le istanze dei dati inviati.
	 * @return ArrayList di tipo {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} corrispondente al MAC address.
	 * @throws FilterFailure nel caso non venga trovato alcun elemento desiderato.
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.filters.FilterListByMac FilterListByMac
	 * 
	 * @author Giacomo Fiara
	 */
	ArrayList<ANcybFishData> getAllResultsByMac(String macAddr) throws FilterFailure;

	/**
	 * <b>Intestazione</b> del metodo che restituisce le statistiche computabili all'ArrayList di ANcybFishData relativo al MAC address specificato.
	 *  
	 * @param macAddr indirizzo MAC del dispositivo interessato.
	 * @return JSONObject contenente tutta la struttura del risultato delle varie statistiche.
	 * @throws JSONException
	 * @throws StatsFailure nel caso si verifichimo eccezioni nell'elaborazione delle statistiche e gestione delle versioni.
	 * @throws FilterFailure nel caso non venga trovato alcun elemento desiderato.
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.filters.FilterListByMac FilterListByMac
	 * @see it.univpm.ancyb_diagnosticTool.stats.AverageTemperatureFish AverageTemperatureFish
	 * @see it.univpm.ancyb_diagnosticTool.stats.GodeticDistanec GodeticDistanec
	 * 
	 * @author Giacomo Fiara
	 */
	public abstract JSONObject getFishStats(String macAddr) throws JSONException, StatsFailure, FilterFailure;

}

