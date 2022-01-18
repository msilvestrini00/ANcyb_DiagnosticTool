package it.univpm.ancyb_diagnosticTool.stats;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

/*
 * <b>Statistica</b> che implementa l'interfaccia {@link it.univpm.ancyb_diagnosticTool.stats.StatsInterface}, 
 * usata per calcolare il valore medio della direzione della corrente marina.
 * La statistica è calcolata a partire dalla data e ora attuali, fino alla stessa ora di 'days' giorni dopo.
 * 
 * @implements StatsInterface
 * @see it.univpm.ancyb_diagnosticTool.filters.StatsInterface
 * 
 * @author Manuele Silvestrini
 */
public class AverageCurrentDirection implements StatsInterface{
	
	private Forecast forecastForStats;
	private byte days;
	private String stats = null;

	/*
	 * <b>Costruttore</b> della statistica.
	 * @param forecastForStats Oggetto
	 * {@link it.univpm.ancyb_diagnosticTool.model.Forecast#Forecast(ArrayList) Forecast}
	 * in ingresso alla statistica.
	 * @param days Parametro che contiene i giorni per cui si vuole esterdere la statistica.
	 */
	public AverageCurrentDirection(Forecast forecastForStats, byte days) {
		this.forecastForStats = forecastForStats;
		this.days = days;
	}
	
	/*
	 * @see it.univpm.ancyb_diagnosticTool.stats.StatsInterface#getDataForStats()
	 */
	@Override
	public Object getDataForStats() {
		return this.forecastForStats;
	}

	/*
	 * <b>Metodo</b> che scorre tutti gli elementi dell'ArrayList contenuti nell'oggetto 
	 * {@link it.univpm.ancyb_diagnosticTool.model.Forecast#Forecast(ArrayList) Forecast}
	 * il quale tempo corrisponde con quello corrente, fino alla stessa ora di 'days' giorni successivi.
	 * Nel mentre, da ogni oggetto viene prelevato il valore di direzione della corrente marina per calcolare la media,
	 * che infine viene restituita al costruttore.
	 * 
	 * @throws StatsFailure
	 * @see it.univpm.ancyb_diagnosticTool.stats.StatsInterface#computeStats()
	 */
	@Override
	public void computeStats() throws StatsFailure{	

		ArrayList<ForecastObject> list = forecastForStats.getForecastList();

		float sum = 0;
		int totalHours = days*24;
		int index = 0;
		
		for(ForecastObject fobj : list) {
						
			if(fobj.getForecastTime().equals(Time.currentDateTime2())) {
				index = list.indexOf(fobj);
				break;
			}
			else continue;
		}
		
		for(int i = 0; i < totalHours; i++) {
			sum += list.get(index+i).getCurrentDirection();
		}
		
		if(sum == 0) throw new StatsFailure("StatsFailure(AverageWaveHeight) --> error during 'AverageCurrentDicrection' statistic computation. Please retry");
		
		this.stats = String.format("%.2f", sum/totalHours);
	}
	
	/*
	 * @throws StatsFailure
	 * @see it.univpm.ancyb_diagnosticTool.stats.StatsInterface#getStats()
	 */
	@Override
	public String getStats() throws StatsFailure {
		if ( stats == null) throw new StatsFailure("StatsFailure(AverageWaveHeight) --> please, first invoke the method 'computeStats()'.");
		return this.stats;
	}
	
}
