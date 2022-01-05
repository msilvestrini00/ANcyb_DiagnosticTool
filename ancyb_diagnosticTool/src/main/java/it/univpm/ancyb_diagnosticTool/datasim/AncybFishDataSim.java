package it.univpm.ancyb_diagnosticTool.datasim;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.utilities.Time;

/* 
 * classe di test che simula la ricezione di un arraylist in cui sono stoccate 
 * tutte le informazioni riguardo i dispositivi (macaddress e coordinate)
 */

// TODO FINISCI DI IMPLEMENTARE LA CLASSE DI SIMULAZIONE, COI METODI DEL FILE WORD
// TODO AGGIORNA SERVICEIMPL CON QUESTI METODI
// TODO AGGIORNA DATAMANGER 



public class AncybFishDataSim {

	ArrayList<AncybFishDataObjectSim> dataList = new ArrayList<AncybFishDataObjectSim>();
	

	public AncybFishDataSim(ArrayList<AncybFishDataObjectSim> dataList) {
		

		dataList.add(new AncybFishDataObjectSim("00:00:00:00:00:00", Time.currentDateTime2()	, 11.111111, 11.111111, 1));
		dataList.add(new AncybFishDataObjectSim("00:00:00:00:00:00", "2022-01-01T00:00:00+00:00", 22.222222, 22.222222, 2));

		dataList.add(new AncybFishDataObjectSim("11:11:11:11:11:11", Time.currentDateTime2()	, 33.333333, 33.333333, 3));
		dataList.add(new AncybFishDataObjectSim("11:11:11:11:11:11", "2022-01-01T00:00:00+00:00", 44.444444, 44.444444, 4));



		
	}


	public double getLatByMacAddr(String macAddr) {
		
		double lat = 0.0;
		
		for(AncybFishDataObjectSim dataSimObject : this.dataList) {
			
			if(dataSimObject.getMacAddr().equals(macAddr)) {
				
				lat = dataSimObject.getLat();
			}
			else continue;
		}
		
		return lat;
	}

	
	public double getLngByMacAddr(String macAddr) {
		
		double lng = 0.0;
		
		for(AncybFishDataObjectSim dataSimObject : this.dataList) {
			
			if(dataSimObject.getMacAddr().equals(macAddr)) {
				
				lng = dataSimObject.getLng();
			}
			else continue;
		}
		
		return lng;
	}



	
}
