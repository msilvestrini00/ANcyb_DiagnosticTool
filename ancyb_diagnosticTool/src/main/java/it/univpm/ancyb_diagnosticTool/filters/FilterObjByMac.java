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
	}

	@Override
	public String getDataToFilter() {
		return this.macAddr;
	}

	@Override
	public ANcybFishData getDataFiltered() throws FilterFailure {
		
		for(int i=ANcybFishData.list.size()-1; i>=0; i--) {
			if(ANcybFishData.list.get(i).getMacAddr().equals(macAddr)) {
				fishData = ANcybFishData.list.get(i);
				return fishData;
			}
		}
		if(fishData ==null) {
			throw new FilterFailure("Nessun elemento di posizione trovato nel database con questo mac address" + macAddr);
		}
		return fishData;
	}

}

