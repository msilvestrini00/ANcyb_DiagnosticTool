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
	 * @param macAddr Indirizzo mac dal quale ricavo le coordinate per l'API esterna.
	 * @return Il ForecastObject relativo alla data e l'ora correnti.
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 * @throws ForecastBuildingFailure
	 * 
	 * @author Manuele Silvestrini
	 */
	public ForecastObject getForecastByRealTime(String macAddr) throws FilterFailure, VersionMismatch, ForecastBuildingFailure;				
	
	/**
	 * <b>Intestazione</b> del metodo che restituisce il ForecastObject filtrato secondo la data e ora inseriti.
	 * @param macAddr Indirizzo mac dal quale ricavo le coordinate per l'API esterna.
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
	 * @param macAddr Indirizzo mac dal quale ricavo le coordinate.
	 * @param days Numero di giorni per cui si vuole estendere le statistiche (a partire dal giorno corrente).
	 * @return L'oggetto JSON che contiene le statistiche.
	 * 
	 * @param macAddr Indirizzo mac dal quale ricavo le coordinate per l'API esterna.
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
	 * 
	 * @param macAddr
	 * @return
	 * @throws FilterFailure
	 * @throws VersionMismatch
	 */
	ArrayList<ANcybFishData> getAllResultsByMac(String macAddr) throws FilterFailure;

	/**
	 * 
	 * @param macAddr
	 * @return
	 * @throws VersionMismatch
	 * @throws FilterFailure
	 */
	ANcybFishData getLatestPositionByMac(String macAddr) throws VersionMismatch, FilterFailure;

	/**
	 * 
	 * @param historyFishData
	 * @return
	 * @throws StatsFailure 
	 * @throws JSONException 
	 * @throws VersionMismatch 
	 */
	public abstract JSONObject getFishStats(ArrayList<ANcybFishData> historyFishData) throws JSONException, StatsFailure, VersionMismatch;

}

