package it.univpm.ancyb_diagnosticTool.model;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;

/**
 * <b>Classe</b> che descrive il modello della previsione meteorologica oraria,
 * i quali dati si basano sulla chiamata all'API esterna 'Storm Glass'.
 * Questi oggetti verranno contenuti in un ArrayList appartenente all'oggetto {@link Forecast#Forecast(ArrayList) Forecast}. 
 * 
 * </p>Link all'API esterna: </br>https://rapidapi.com/ManniskaMaskin/api/storm-glass/</p>
 * 
 * @implements DataReceived
 * Link all'API esterna: https://rapidapi.com/ManniskaMaskin/api/storm-glass/
 * 
 * @see it.univpm.ancyb_diagnosticTool.model.Forecast
 * 
 * @author Manuele Silvestrini
 */
public class ForecastObject implements DataReceived{

	private String forecastTime;
	private String macAddress;
	private float waveHeight;
	private float currentDirection;
	private float latitude;
	private float longitude;
	
	/**
	 * <b>Costruttore</b> 
	 * 
	 * @param macAddress Indirizzo mac la quale posizione è stata utilizzata per ricevere le previsioni meteorologiche.
	 * @param latitude Latitudine del dispositivo.
	 * @param longitude Longitudine del dispositivo.
	 * @param forecastTime Data e ora della previsione, nel formato nativo dell'API esterna.
	 * @param waveHeight Valore dell'altezza delle onde (in metri) di una data previsione oraria.
	 * @param currentDirection Valore della direzione della corrente (in gradi) di una data previsione oraria.
	 */
	public ForecastObject(String macAddress, float latitude, float longitude, String forecastTime, float waveHeight, float currentDirection) {
		
		this.forecastTime = forecastTime;
		this.macAddress = macAddress;
		this.latitude = latitude;
		this.longitude = longitude;
		this.waveHeight = waveHeight;
		this.currentDirection = currentDirection;
	}

	/**
	 * @return L'indirizzo mac del dispositivo.
	 */
	public String getMacAddress() {
		return macAddress;
	}

	/**
	 * @return La latitudine del dispositivo.
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * @return La longitudine del dispositivo.
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * @return L'ora e la data della previsione nel formato nativo dell'API esterna.
	 */
	public String getForecastTime() {
		return forecastTime;
	}

	/**
	 * @return L'orario della previsione nel formato 'HH:mm:ss'.
	 */
	public String getTime() { 
		return forecastTime.substring(10, 18);
	}

	/**
	 * @param time L'orario che si vuole modificare della previsione.
	 */
	public void setTime(String time) {
		
		String firstHalf = forecastTime.substring(0, 10);
		String secondHalf = forecastTime.substring(19, 25);

		this.forecastTime = firstHalf + time + secondHalf;
	}

	/**
	 * @return La data della previsione nel formato 'yyyy.MM.dd'.
	 */
	public String getDate() { 
		return forecastTime.substring(0,10).replace('-', '.');
	}

	/**
	 * @param date La data che si vuole modificare della previsione.
	 */
	public void setDate(String date) {
		
		String date2 = date.replace('.', '-');
		
		String firstHalf = forecastTime.substring(0, 10);
		String secondHalf = forecastTime.substring(19, 25);

		this.forecastTime = firstHalf + date2 + secondHalf;
	}
	
	/**
	 * @return L'altezza delle onde della previsione.
	 */
	public float getWaveHeight() {
		return waveHeight;
	}

	/*
	 * @return La direzione della corrente marittima della previsione.
	 */
	public float getCurrentDirection() {
		return currentDirection;
	}

	/**
	 * <b>Metodo</b> che converte il ForecastObject in oggetto JSON.
	 * @return L'oggetto JSON con le informazioni sulla previsione oraria.
	 */
    public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        
        jo.put("macAddress", this.getMacAddress());
        jo.put("Latitude", this.getLatitude());
        jo.put("Longitude", this.getLongitude());
        jo.put("Time", this.getForecastTime());
        jo.put("WaveHeight", this.getWaveHeight());
        jo.put("CurrentDirection", this.getCurrentDirection());
        
        return jo;
    }

    /**
     * <b>Metodo</b> che restituisce il toString della previsione oraria.
     * @return La stringa con le informazioni sulla previsione oraria.
     */
    public String toString() {
    	
		return "macAddr: " 		  	  + this.getMacAddress() 		+ "\n" + 
			   "Latitude: " 		  + this.getLatitude() 	 		+ "\n" + 
			   "Longitude: " 		  + this.getLongitude()  		+ "\n" + 
			   "time: " 		  	  + this.getForecastTime() 		+ "\n" + 
			   "waveHeight: " 	  	  + this.getWaveHeight() 		+ "\n" + 
			   "currentDirection: "   + this.getCurrentDirection();
	}

}
