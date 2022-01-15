package it.univpm.ancyb_diagnosticTool.stats;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.StatsFailure;
import it.univpm.ancyb_diagnosticTool.model.Forecast;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.utilities.Time;


public class AverageWaveHeight implements StatsInterface{
	
	private Forecast forecastForStats;
	private int days;
	private String stats = null;

	
	public AverageWaveHeight(Forecast forecastForStats, int days) {
		
		this.forecastForStats = forecastForStats;
		this.days = days;


	}
	
	@Override
	public Object getDataForStats() {
		return this.forecastForStats;
	}

	@Override
	public void computeStats() throws StatsFailure{	// TODO creare un metodo apposito? se sì, dove metterlo?

		ArrayList<ForecastObject> list = forecastForStats.getForecastList();

		float sum = 0;
		int totalHours = days*24;

		int index = 0;
		
		//prendo i valori dell'elemento del tempo corrente
		for(ForecastObject fobj : list) {
						
			if(fobj.getForecastTime().equals(Time.currentDateTime2())) {
				index = list.indexOf(fobj);
				break;
			}
			else continue;
		}
		
		//scorro i dati e sommo 
		for(int i = 0; i < totalHours; i++) {
			sum += list.get(index+i).getWaveHeight();
		}
		
		if(sum == 0) throw new StatsFailure("Errore nel calcolo della statistica. Riprovare");
		this.stats = String.format("%.2f", sum/totalHours);
	}

	
	
	
	@Override
	public String getStats() throws StatsFailure {
		if ( stats == null) throw new StatsFailure("Nessuna statistica elaborata, invocare prima la funzione computeStats()");
		return this.stats;
	}

}
