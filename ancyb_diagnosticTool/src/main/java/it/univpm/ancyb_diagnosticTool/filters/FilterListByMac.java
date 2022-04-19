package it.univpm.ancyb_diagnosticTool.filters;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.DataSaved;

/**
 * <b>Filtro</b> che implementa l'interfaccia 
 * {@link it.univpm.ancyb_diagnosticTool.filters.FilterInterface FilterInterface}.<br>
 * Questo restituisce collezioni di oggetti di tipo {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}
 * in base al MAC address.
 * 
 * @author Giacomo Fiara
 *
 */
public class FilterListByMac implements FilterInterface {

	/**
	 * MAC address del dispositivo che si vuole filtrare.
	 */
	private String macAddr;
	
	/**
	 * ArrayList di istanze di tipo {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} che si vuole restituire come risultato del filtro.
	 */
	private ArrayList<ANcybFishData> filteredFishDataList;
	
	/**
	 * <b>Costruttore</b> del filtro.
	 * @param macAddr MAC address legato al dispositivo su cui il filtro baserà le sue attività.
	 */
	public FilterListByMac(String macAddr) {
		this.macAddr = macAddr;
		this.filteredFishDataList = new ArrayList<ANcybFishData>();
	}

	/**
	 * <b>Metodo</b> che restituisce il dato da filtrare.
	 * @return MAC address per il quale viene effettuto il filtro.
	 */
	@Override
	public String getDataToFilter() {
		return this.macAddr;
	}

	/**
	 * <b>Metodo</b>  che restituisce il dato filtrato.
	 * @return ArrayList di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} che son legati al MAC address desiderato.
	 */
	@Override
	public ArrayList<ANcybFishData> getFilteredData() throws FilterFailure {	
		if(filteredFishDataList.size()==0) {
			throw new FilterFailure("FilterFailure(FilterListByMac) --> please, first invoke the method 'computeFilter()'.");
		}
		return filteredFishDataList;	
	}

	/**
	 * <b>Metodo</b> che elabora il filtro e crea un ArrayList di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}
	 * tra quelli salvati in {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.DataSaved DataSaved} e  
	 * legati esclusivamente al MAC address utilizzato per creare il filtro.
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.DataSaved#getList() DataSaved.getList()
	 */
	@Override
	public void computeFilter() throws FilterFailure {

		for(int i=0; i<DataSaved.getList().size(); i++) {
			if(DataSaved.getList().get(i).getMacAddr().equals(macAddr)) {
				filteredFishDataList.add(DataSaved.getList().get(i));
			}
		}
		if(filteredFishDataList.size()==0) {
			throw new FilterFailure("FilterFailure(FilterListByMac) --> No fishData in 'DataSaved' with the following MAC address: " + macAddr + " has been found.");
		}
		
	}
		
}
