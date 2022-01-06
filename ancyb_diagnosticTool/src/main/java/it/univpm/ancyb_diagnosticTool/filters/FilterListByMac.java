package it.univpm.ancyb_diagnosticTool.filters;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;

/**
 * 
 * @author giaco
 *
 */
public class FilterListByMac implements FilterInterface {

	private String macAddr;
	ArrayList<ANcybFishData> fishDataList;
	
	public FilterListByMac(String macAddr) {
		this.macAddr = macAddr;
	}

	@Override
	public String getDataToFilter() {
		return this.macAddr;
	}

	@Override
	public ArrayList<ANcybFishData> getDataFiltered() throws FilterFailure {
		
		for(int i=ANcybFishData.list.size()-1; i>=0; i--) {
			if(ANcybFishData.list.get(i).getMacAddr().equals(macAddr)) {
				fishDataList.add(ANcybFishData.list.get(i));
			}
		}
		if(fishDataList ==null) {
			throw new FilterFailure("Nessun elemento di posizione trovato nel database con questo mac address" + macAddr);
		}
		return fishDataList;
		
	}
		
}
