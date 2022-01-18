package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.InvalidParameter;
import it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat;
import it.univpm.ancyb_diagnosticTool.utilities.Coord;

/**
 * <b>Classe</b> che eredita dalla superclasse {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} e modella i dati provenienti
 * dai dispositivi sottomarini dotati di version software 'G' (oppure 'Ver_G', 'VerG'), ovvero dotati di modulo GPS.
 * 
 * @see it.univpm.ancyb_diagnosticTool.utilities.DataReceived DataReceived
 * 
 * @author Giacomo Fiara
 */
public class ANcybFishData_VerG extends ANcybFishData {

	/**
	 * Attributo della latitudine in formato DD (posizione del dispositivo).
	 */
	private float latitude;
	
	/**
	 * Attributo della longitudine in formato DD (posizione del dispositivo).
	 */
	private float longitude;
	
	/**
	 * Attributo della qualità del segnale di posizione.
	 */
	private String qualPos;
	
	/**
	 * <b>Costruttore</b> genera istanze di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG ANcybFishData_VerG} 
	 * a partire da un vettore di stringhe.<br><br>
	 * 
	 * La latitudine e longitudine ricevuti in formato DDM (gradi e minuti decimali) vengono controllati nella loro forma 
	 * e poi convertiti dai metodi della classe {@link it.univpm.ancyb_diagnosticTool.utilities.Coord Coord}.
	 * 
	 * @param strArr è il vettore di stringhe che viene ottenuto dall'elaborazione dei messaggi 
	 * (da parte di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager#createDataObj(String) createDataObj(String)})
	 * ricevuti tramite {@link it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient#subscribe(String) subscribe(String)}. 
	 * Contiene gli elementi necessari alla costruzione dell'oggetto.
	 * 
	 * @throws InvalidParameter se la stringa della data o dell'orario non rispettano la grammatica che ci si aspetta.
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws WrongCoordFormat se la stringa della latitudine o longitudine non corrisponde ad un formato accettato.
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.utilities.Coord#checkIsLat(String) checkIsLat(String)
	 * @see it.univpm.ancyb_diagnosticTool.utilities.Coord#checkIsLat(String) checkIsLat(String)
	 * @see it.univpm.ancyb_diagnosticTool.utilities.Coord#latDDMstringToDDfloat(String) latDDMstringToDDfloat(String)
	 * @see it.univpm.ancyb_diagnosticTool.utilities.Coord#lonDDMstringToDDfloat(String) lonDDMstringToDDfloat(String)
	 */
	protected ANcybFishData_VerG(String[] strArr) throws InvalidParameter, WrongCoordFormat, ArrayIndexOutOfBoundsException {
		
		super(strArr);
		
		String latitudeStr = strArr[3];
		Coord.checkIsLat(latitudeStr);
		this.latitude = Coord.latDDMstringToDDfloat(latitudeStr);
	
		String longitudeStr = strArr[4];
		Coord.checkIsLon(longitudeStr);
		this.longitude = Coord.lonDDMstringToDDfloat(longitudeStr);
		
		this.qualPos = strArr[5];
		
	}
	
	/**
	 * <b>Costruttore</b> genera istanze di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG ANcybFishData_VerG} 
	 * a partire da più stringhe.
	 * @deprecated
     * Questo costruttore non viene più utilizzato.
     * <p> Utilizzare in compenso {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData#ANcybFishData(String[]) ANcyFishData(String[])}.
 	 * @param date data in cui viene ricevuto il messaggio corrispondente dal dispositivo.
	 * @param time tempo in cui viene inviato il messaggio dal dispositivo.
	 * @param macAddr MAC address del dispositivo che ha inviato il messaggio.
	 * @param ver versione del software del dispositivo che ha inviato il messaggio.
	 * @param latitude latitudine in DDM.
	 * @param longitude longitudine in DDM.
	 * @param qualPos qualità della posizione.
	 */
	protected ANcybFishData_VerG(String date, String time, String macAddr, String ver, float latitude, float longitude, String qualPos) {
		super(date, time, macAddr, ver);
		this.latitude = latitude;
		this.longitude = longitude;
		this.qualPos = qualPos;
	}
	
	/**
	 * <b>Metodo</b> che restituisce la latitudine dell'istanza.
	 * @return restituisce la latitudine in float e in formato DD (gradi decimali).
	 */
	public float getLatitude() {
		return latitude;
	}
	
	/**
	 * <b>Metodo</b> che permette di impostare la latitudine dell'istanza manualmente.
	 * @param latitude cordinata in formato DD (gradi decimali).
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * <b>Metodo</b> che restituisce la longitudine dell'istanza.
	 * @return restituisce la longitudine in float e in formato DD (gradi decimali).
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * <b>Metodo</b> che permette di impostare la longitudine dell'istanza manualmente.
	 * @param longitude cordinata in formato DD (gradi decimali).
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * <b>Metodo</b> che restituisce la latitudine e longitudine dell'istanza.</br></br>
	 * 
	 * Utilizzato ad esempio nel contesto seguente:</br></br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;{@code double distance = Coord.disgeod( A.getCoord(), B.getCoord());}</br>
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.utilities#Coord.disgeod(float[], float[]) Coord.disgeod(float[], float[])
	 * 
	 * @return restituisce un vettore di dati float contenente latitudine e longitudine in formato DD (gradi decimali).
	 */
	public float[] getCoord() {
		float[] coord = {this.latitude, this.longitude};
		return coord;
	}
	
	/**
	 * <b>Metodo</b> che restituisce la qualità del segnale di posizione al momento dell'invio del messaggio da parte del dispositivo.
	 * @return stringa contenente il valore "NO_Signal" o numerico crescente che rappresenta la qualità di ricezione del segnale del modulo GPS.
	 */
	public String getQualPos() {
		return qualPos;
	}

	/**
	 * <b>Metodo</b> modifica la qualità del segnali di posizione dell'istanza.
	 * @param qualPos è l'attributo della qualità del segnale di posizione che si vuole impostare.
	 */
	public void setQualPos(String qualPos) {
		this.qualPos = qualPos;
	}
	
	@Override
	public String toString() {
		
		String s = "Date " + getDate() + " " +
				   "Time " + getTime() + " " +
			       "Mac address " + getMacAddr() + " " +
				   "Version " + getVer() +  " " +
				   "Latitude " + getLatitude() + " " +
				   "Longitude " + getLongitude() + " " +
				   "Quality position " + getQualPos() + "\n";
		
		return s;
		
	}
	
	@Override
	public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        
        jo.put("Date", this.getDate());
        jo.put("Time", this.getTime());
        jo.put("Mac address", this.getMacAddr());
        jo.put("Version", this.getVer());
        jo.put("Latitude", this.getLatitude());
        jo.put("Longitude", this.getLongitude());
        jo.put("Quality position", this.getQualPos());
        
        return jo;
	}
}
