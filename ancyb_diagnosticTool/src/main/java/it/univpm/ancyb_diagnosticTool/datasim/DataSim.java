package it.univpm.ancyb_diagnosticTool.datasim;

public class DataSim {

	
	String macAddr; //= "a4:cf:12:76:76:95";
	double lat; // = 43.574998;
	double lng; // = 13.492686;
	String time; // = "2022-01-05T00:00:00+00:00";	//TODO fai una stringa di conversione del tempo tra me e jack? (in caso da mettere in "utils")
	

	public String getMacAddr() {
		return "a4:cf:12:76:76:95";
	}


	public double getLat() {
		return 43.574998;
	}


	public double getLng() {
		return 13.492686;
	}


	public String getRealTime() {
		return "2022-01-05T00:00:00+00:00";
	}


	
}
