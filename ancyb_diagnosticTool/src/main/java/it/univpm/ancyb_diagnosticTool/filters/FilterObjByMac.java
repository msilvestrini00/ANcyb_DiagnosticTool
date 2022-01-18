package it.univpm.ancyb_diagnosticTool.filters;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.DataSaved;
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
			throw new FilterFailure("FilterFailure(FilterObjByMac) --> please, first invoke the method 'computeFilter()'.");
		}
		return fishData;
	}

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

