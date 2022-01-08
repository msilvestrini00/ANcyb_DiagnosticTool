package it.univpm.ancyb_diagnosticTool.filters;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;

/**
 * 
 * @author Giacomo Fiara
 *
 */
public class FilterListByMac implements FilterInterface {

	private String macAddr;
	private ArrayList<ANcybFishData> filteredFishDataList;
	
	public FilterListByMac(String macAddr) {
		this.macAddr = macAddr;
		this.filteredFishDataList = new ArrayList<ANcybFishData>();
	}

	@Override
	public String getDataToFilter() {
		return this.macAddr;
	}

	@Override
	public ArrayList<ANcybFishData> getFilteredData() throws FilterFailure {	
		if(filteredFishDataList.size()==0) {
			throw new FilterFailure("Nessun 'ArrayList<ANcybFishData>' filtrato, invocare prima la funzione computeFilter()");
		}
		return filteredFishDataList;	
	}

	@Override
	public void computeFilter() throws FilterFailure {

		for(int i=ANcybFishData.list.size()-1; i>=0; i--) {
			if(ANcybFishData.list.get(i).getMacAddr().equals(macAddr)) {
				filteredFishDataList.add(ANcybFishData.list.get(i));
			}
		}
		if(filteredFishDataList.size()==0) {
			throw new FilterFailure("Nessun elemento di posizione trovato nel database con questo mac address: " + macAddr);
		}
		
	}
		
}
