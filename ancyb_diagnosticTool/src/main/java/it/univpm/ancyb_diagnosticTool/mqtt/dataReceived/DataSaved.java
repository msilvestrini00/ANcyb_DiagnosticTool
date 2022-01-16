package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import java.util.ArrayList;

public class DataSaved {

	private static ArrayList<ANcybFishData> list = new ArrayList<>();

	public static ArrayList<ANcybFishData> getList() {
		return list;
	}

}
