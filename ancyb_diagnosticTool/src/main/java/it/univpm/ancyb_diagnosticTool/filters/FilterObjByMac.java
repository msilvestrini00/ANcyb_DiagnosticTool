package it.univpm.ancyb_diagnosticTool.filters;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.DataSaved;

/**
 * <b>Filtro</b> che implementa l'interfaccia 
 * {@link it.univpm.ancyb_diagnosticTool.filters.FilterInterface FilterInterface}.<br>
 * Questo restituisce l'ulitmo oggetto di tipo {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}
 * in base al MAC address.
 * 
 * @author Giacomo Fiara
 *
 */
public class FilterObjByMac implements FilterInterface {
	
	/**
	 * MAC address del dispositivo che si vuole filtrare.
	 */
	private String macAddr;
	
	/**
	 * Istanza {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} che si vuole restituire come risultato del filtro.
	 */
	ANcybFishData fishData;

	/**
	 * <b>Costruttore</b> del filtro.
	 * @param macAddr MAC address legato al dispositivo su cui il filtro baserà le sue attività.
	 */
	public FilterObjByMac(String macAddr) {
		this.macAddr = macAddr;
		this.fishData = null;
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
	 * @return ultima istanza registrata di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} legata al MAC address desiderato.
	 */
	@Override
	public ANcybFishData getFilteredData() throws FilterFailure {
		if(fishData == null) {
			throw new FilterFailure("FilterFailure(FilterObjByMac) --> please, first invoke the method 'computeFilter()'.");
		}
		return fishData;
	}

	/**
	 * <b>Metodo</b> che elabora il filtro e ottiene l'ultima istanza {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData}
	 * salvata in {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.DataSaved DataSaved} e legata al MAC address utilizzato per creare il filtro.
	 * 
 	 * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.DataSaved#getList() DataSaved.getList()
	 */
	@Override
	public void computeFilter() throws FilterFailure {
		
		for(int i=DataSaved.getList().size()-1; i>=0; i--) {
			if(DataSaved.getList().get(i).getMacAddr().equals(macAddr)) {
				fishData = DataSaved.getList().get(i);
				break;
			}
		}
		if(fishData == null) {
			throw new FilterFailure("FilterFailure(FilterObjByMac) --> No fishData in 'DataSaved' with the following MAC address: " + macAddr + " has been found.");
		}
		
	}

}

