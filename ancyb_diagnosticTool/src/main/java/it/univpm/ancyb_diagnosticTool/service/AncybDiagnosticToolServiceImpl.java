package it.univpm.ancyb_diagnosticTool.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.filters.FilterForecastByTime;
import it.univpm.ancyb_diagnosticTool.filters.FilterListByMac;
import it.univpm.ancyb_diagnosticTool.filters.FilterObjByMac;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.utilities.Time;
import it.univpm.ancyb_diagnosticTool.utilities.checkVersion;


@Service
public class AncybDiagnosticToolServiceImpl implements AncybDiagnosticToolService {

	
	/*
	 * METODO VECCHIO
	 */
	/*
	@Override
	public ForecastObject getRealTimeForecast(String macAddr) throws FilterFailure, VersionMismatch {
		
		//definisco l'oggetto per cui ricavo le coordinate per elaborare i dati
		ForecastDataManager dataManager = new ForecastDataManager(macAddr);
		
		ArrayList<ForecastObject> forecastList = new ArrayList<ForecastObject>();
		Forecast forecast = new Forecast(forecastList);
		
		dataManager.buildUrl();		
		dataManager.downloadJSONData();
		forecast = dataManager.buildForecast();

	    FilterForecastByTime filter = new FilterForecastByTime(forecast, Time.currentDateTime2());

		return filter.getDataFiltered();
	}
	*/
	
	/*
	 * METODO NUOVO (senza filtro e eccezione relativa, esportati in ancybRestController)
	 */
	@Override
	public Forecast getForecast(String macAddr) throws FilterFailure, VersionMismatch {
		
		//definisco l'oggetto per cui ricavo le coordinate per elaborare i dati
		ForecastDataManager dataManager = new ForecastDataManager(macAddr);
		
		ArrayList<ForecastObject> forecastList = new ArrayList<ForecastObject>();
		Forecast forecast = new Forecast(forecastList);
		
		dataManager.buildUrl();		
		dataManager.downloadJSONData();
		forecast = dataManager.buildForecast();

		return forecast;
	}

	@Override
	public ANcybFishData getLatestPositionByMac(String macAddr) throws VersionMismatch, FilterFailure {
		FilterObjByMac filterFishData = new FilterObjByMac(macAddr);
		ANcybFishData fishData = filterFishData.getDataFiltered();
		checkVersion.verG(fishData);
		return fishData;
	}


	@Override
	public ArrayList<ANcybFishData> getAllPositionsByMac(String macAddr) throws FilterFailure, VersionMismatch {
		FilterListByMac filterFishData = new FilterListByMac(macAddr);
		ArrayList<ANcybFishData> fishData = filterFishData.getDataFiltered();
		checkVersion.verG(fishData);
		return fishData;
	}




}


//TODO implementa i metodi (es di stoccaggio dati)


