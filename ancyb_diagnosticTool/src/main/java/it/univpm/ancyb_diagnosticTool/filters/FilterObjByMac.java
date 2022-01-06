package it.univpm.ancyb_diagnosticTool.filters;

import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;

public class FilterObjByMac implements FilterInterface {
	
	ANcybFishData ancybData;
	
	public FilterObjByMac(ANcybFishData ancybData) {
		this.ancybData = ancybData;
	}

	@Override
	public ANcybFishData getDataToFilter() {
		return this.ancybData;
	}

	@Override
	public ANcybFishData getDataFiltered() {
		// TODO Auto-generated method stub
		return null;
	}

}
