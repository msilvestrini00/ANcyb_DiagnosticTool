package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.InvalidParameter;
import it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat;

/**
 * <b>Classe</b> che eredita dalla superclasse {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerG ANcybFishData_VerG} e modella i dati provenienti
 * dai dispositivi sottomarini dotati di version software 'GT' (oppure 'Ver_GT', 'VerGT'), ovvero dotati di modulo GPS e sensore di temperatura.
 * 
 * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData
 * @see it.univpm.ancyb_diagnosticTool.utilities.DataReceived DataReceived
 * 
 * @author Giacomo Fiara
 */
public class ANcybFishData_VerGT extends ANcybFishData_VerG {

	/**
	 * Attributo della temperatura rilevata dal dispositivo.
	 */
	private float temp;
	
	/**
	 * <b>Costruttore</b> genera istanze di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT ANcybFishData_VerGT} 
	 * a partire da un vettore di stringhe.<br><br>
	 * 
	 * La temperatura, ricevuta come stringa viene convertita in float tramite il metodo {@link java.lang.Float#parseFloat(String) parseFloat(String)}. 
	 * 
	 * @param strArr è il vettore di stringhe che viene ottenuto dall'elaborazione dei messaggi 
	 * (da parte di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager#createDataObj(String) createDataObj(String)})
	 * ricevuti tramite {@link it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient#subscribe(String) subscribe(String)}. 
	 * Contiene gli elementi necessari alla costruzione dell'oggetto.
	 * 
	 * @throws InvalidParameter se la stringa della data o dell'orario non rispettano la grammatica che ci si aspetta.
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws WrongCoordFormat se la stringa della latitudine o longitudine non corrisponde ad un formato accettato.
	 * @throws NullPointerException se temperatureStr è 'null'.
	 * @throws NumberFormatException se la stringa non ha un contenuto convertibile in float.
	 * 
	 * @see java.lang.Float#parseFloat(String) parseFloat(String)
	 */
	protected ANcybFishData_VerGT(String[] strArr) throws InvalidParameter, WrongCoordFormat, ArrayIndexOutOfBoundsException, NullPointerException, NumberFormatException {
		
		super(strArr);

		String temperatureStr = strArr[6];
		this.temp = Float.parseFloat(temperatureStr);
		
	}
	
	/**
	 * <b>Costruttore</b> genera istanze di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData_VerGT ANcybFishData_VerGT} 
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
	 * @param temp temperatura misurata dal sensore.
	 */
	protected ANcybFishData_VerGT(String date, String time, String macAddr, String ver, float latitude, float longitude,
			String qualPos, float temp) {
		super(date, time, macAddr, ver, latitude, longitude, qualPos);
		
		this.temp = temp;
	}

	/**
	 * <b>Metodo</b> che restituisce l'attributo temperatura dell'istanza.
	 * @return la temperatura in tipo 'float'.
	 */
	public float getTemp() {
		return temp;
	}

	/**
	 * <b>Metodo</b> che permette di impostare la temperatura dell'istanza manualmente.
	 * @param la temperatura desiderata in tipo 'float'.
	 */
	public void setTemp(float temp) {
		this.temp = temp;
	}	
	
	@Override
	public String toString() {
		
		String s = "Date " + getDate() + " " +
				   "Time " + getTime() + " " +
			       "Mac address " + getMacAddr() + " " +
				   "Version " + getVer() +  " " +
				   "Latitude " + getLatitude() + " " +
				   "Longitude " + getLongitude() + " " +
				   "Quality position " + getQualPos() + " " + 
				   "Temperature " + getTemp() + "\n";
		
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
        jo.put("Temperature", this.getTemp());
        
        return jo;
	}
}
