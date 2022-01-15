package it.univpm.ancyb_diagnosticTool.filters;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
/**
 * 
 * @author Giacomo Fiara
 *
 */
public class FilterObjByMac implements FilterInterface {
	
	ANcybFishData fishData;
	private String macAddr;
	
	public FilterObjByMac(String macAddr) {
		this.macAddr = macAddr;
		this.fishData = null;
	}

	@Override
	public String getDataToFilter() {
		return this.macAddr;
	}

	@Override
	public ANcybFishData getFilteredData() throws FilterFailure {
		if(fishData == null) {
			throw new FilterFailure("Nessun 'ANcybFishData' filtrato, invocare prima la funzione computeFilter()");
		}
		return fishData;
	}

	@Override
	public void computeFilter() throws FilterFailure {
		
		for(int i=ANcybFishData.list.size()-1; i>=0; i--) {
			if(ANcybFishData.list.get(i).getMacAddr().equals(macAddr)) {
				fishData = ANcybFishData.list.get(i);
			}
		}
		if(fishData == null) {
			throw new FilterFailure("Nessun elemento di posizione trovato nel database con questo mac address" + macAddr);
		}
		
	}

}

