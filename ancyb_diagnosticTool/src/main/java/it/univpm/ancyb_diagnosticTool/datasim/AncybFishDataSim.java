package it.univpm.ancyb_diagnosticTool.datasim;

import java.util.ArrayList;

import it.univpm.ancyb_diagnosticTool.utilities.Time;

/* 
 * classe di test che simula la ricezione dei dati da MQTT con un arraylist in cui sono stoccate 
 * tutte le informazioni riguardo i dispositivi (macaddress e coordinate).
 * Le informazioni riguardano due dispositivi 
 */

// TODO FINISCI DI IMPLEMENTARE LA CLASSE DI SIMULAZIONE, COI METODI DEL FILE WORD
// TODO AGGIORNA SERVICEIMPL CON QUESTI METODI
// TODO AGGIORNA DATAMANGER 



public class AncybFishDataSim {

	ArrayList<AncybFishDataObjectSim> dataList = new ArrayList<AncybFishDataObjectSim>();
	

	public AncybFishDataSim(ArrayList<AncybFishDataObjectSim> dataList) {
		

		dataList.add(new AncybFishDataObjectSim("00:00:00:00:00:00", Time.currentDateTime2()	, 43.699964, 13.410088, 1));
		dataList.add(new AncybFishDataObjectSim("00:00:00:00:00:00", "2022-01-01T00:00:00+00:00", 44.079568, 13.729647, 2));

		dataList.add(new AncybFishDataObjectSim("11:11:11:11:11:11", Time.currentDateTime2()	, 42.034962, 15.436648, 3));
		dataList.add(new AncybFishDataObjectSim("11:11:11:11:11:11", "2022-01-01T00:00:00+00:00", 41.899620, 16.289014, 4));	
	}

	

	public AncybFishDataObjectSim getDataSim(String macAddr, String time) {
		
		int index = -1;
		
		for(int i=0; i< this.dataList.size(); i++) {
			
			if(dataList.get(i).getMacAddr().equals(macAddr) && dataList.get(i).getTime().equals(time)) {
				
				index = dataList.indexOf(dataList.get(i));
			}
			else continue;
		}
		
		if(index == -1) {
			
			System.out.println("Oggetto AncybFishDataObjectSim non trovato");
			return null;
		}
		
		else return dataList.get(index);
	}

	
	public ArrayList<AncybFishDataObjectSim> getDataSimList(String macAddr) {
		
		ArrayList<AncybFishDataObjectSim> list = new ArrayList<AncybFishDataObjectSim>();
		
		for(AncybFishDataObjectSim obj : dataList) {
		
			if(obj.getMacAddr().equals(macAddr)) list.add(obj);
		}
		return list;
	}



	
}
