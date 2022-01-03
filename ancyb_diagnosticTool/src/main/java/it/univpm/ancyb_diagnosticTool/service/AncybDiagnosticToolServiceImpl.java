package it.univpm.ancyb_diagnosticTool.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class AncybDiagnosticToolServiceImpl implements AncybDiagnosticToolService {

	private String apiKey = "4380b6f80amshdae4ed371f74652p1857c6jsn3c4a8bf96244";
	private String uri = "https://stormglass.p.rapidapi.com/forecast?rapidapi-key=";
	private String JSONData;
	
	@Override
	public void receiveJSONData() {

		try {
			URLConnection urlConn = new URL(uri + apiKey).openConnection();
			InputStream in = urlConn.getInputStream();
			
			String data = "";
			String line = "";
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
			
				String i;
				while ((i = buf.readLine()) != null){
					
					//if(line.contains)	// TODO fai controllo per eliminare il testo da ,"meta" incluso, in modo da avere solo l'array
					data += line;
				}
		}	
		finally {
			in.close();
		}
		// TODO PARTE DI ELABORAZIONE E STOCCAGGIO DATI
			
		JSONData = data;

			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	
	@Override
	public String getJSONData() {

		return JSONData;
	}
	
	
	
	
	
}


//TODO implementa i metodi (es di stoccaggio dati)
