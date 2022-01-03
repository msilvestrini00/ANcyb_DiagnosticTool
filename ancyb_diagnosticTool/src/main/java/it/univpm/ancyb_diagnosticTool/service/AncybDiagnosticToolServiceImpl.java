package it.univpm.ancyb_diagnosticTool.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class AncybDiagnosticToolServiceImpl implements AncybDiagnosticToolService {

	private String apiKey = "4380b6f80amshdae4ed371f74652p1857c6jsn3c4a8bf96244";
	private String uri = "https://stormglass.p.rapidapi.com/forecast?rapidapi-key=";
	
	@Override
	public JSONObject getJSONForecast(String time, float lat, float lng) {
		//JSONObject forecast = null;
		
			
		try {
			URLConnection urlConn = new URL(uri + apiKey).openConnection();
			InputStream in = urlConn.getInputStream();
			
			String JSONData = "";
			String line = "";
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
			
				String i;
				while ((i = buf.readLine()) != null){
					
					//if(line.contains)	// TODO fai controllo per eliminare il testo da ,"meta" incluso, in modo da avere solo l'array
					JSONData += line;
				}
		}	
		finally {
			in.close();
		}
		// TODO PARTE DI ELABORAZIONE E STOCCAGGIO DATI
			
		//	JSONArray = mJsonArray = new JSONArray(JSONData)
			
		
		System.out.println(JSONData);
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}


//TODO implementa i metodi (es di stoccaggio dati)
