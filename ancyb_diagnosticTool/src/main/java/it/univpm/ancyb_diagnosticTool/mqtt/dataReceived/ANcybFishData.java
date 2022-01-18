package it.univpm.ancyb_diagnosticTool.mqtt.dataReceived;

import org.json.JSONObject;

import it.univpm.ancyb_diagnosticTool.Exception.InvalidParameter;
import it.univpm.ancyb_diagnosticTool.utilities.CheckInputParameters;
import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

/**
 * <b>Classe</b> che implementa l'interfacia {@link it.univpm.ancyb_diagnosticTool.utilities.DataReceived DataReceived} e modella i dati provenienti
 * dai dispositivi sottomarini.
 * 
 * @author Giacomo Fiara
 *
 */
public class ANcybFishData implements DataReceived {

	/**
	 * Attributo dell'orario.
	 */
	private String time;
	
	/**
	 * Attributo della data.
	 */
	private String date;
	
	/**
	 * Attributo della versione del dispositivo da cui provengono i dati.
	 */
	private String ver;
	
	/**
	 * Attributo dell'indirizzo Mac address univoco per ciascun dispositivo.
	 */
	private String macAddr;
		
	/**
	 * <b>Costruttore</b> genera istanze di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} 
	 * a partire da un vettore di stringhe.
	 * 
	 * @param strArr è il vettore di stringhe che viene ottenuto dall'elaborazione dei messaggi 
	 * (da parte di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager#createDataObj(String) createDataObj(String)})
	 * ricevuti tramite {@link it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient#subscribe(String) subscribe(String)}. 
	 * Contiene gli elementi necessari alla costruzione dell'oggetto.
	 * 
	 * @throws InvalidParameter se la stringa della data o dell'orario non rispettano la grammatica che ci si aspetta.
	 * @throws ArrayIndexOutOfBoundsException
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager ANcybDataManager
	 * @see it.univpm.ancyb_diagnosticTool.utilities.CheckInputParameters CheckInputParameters
	 */
	protected ANcybFishData(String[] strArr) throws InvalidParameter, ArrayIndexOutOfBoundsException {
		
		this.date = Time.currentDate();
		
		this.macAddr = strArr[0];
		CheckInputParameters.CheckMacAddr(this.macAddr);
		
		this.ver = strArr[1];
		
		this.time = strArr[2];
		CheckInputParameters.CheckTime(time);
		
	}
	
	/**
	 * <b>Costruttore</b> genera istanze di {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData ANcybFishData} 
	 * a partire da più stringhe.
	 * @deprecated
     * Questo costruttore non viene più utilizzato.
     * <p> Utilizzare in compenso {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData#ANcybFishData(String[]) ANcyFishData(String[])}.
 	 * @param date data in cui viene ricevuto il messaggio corrispondente dal dispositivo.
	 * @param time tempo in cui viene inviato il messaggio dal dispositivo.
	 * @param macAddr MAC address del dispositivo che ha inviato il messaggio.
	 * @param ver versione del software del dispositivo che ha inviato il messaggio.
	 */
	protected ANcybFishData(String date, String time, String macAddr, String ver) {
		this.date=date;
		this.time=time;
		this.ver=ver;
		this.macAddr=macAddr;
	}

	@Override
	public String getDate() {
		return date;
	}
	
	@Override
	public void setDate(String date) {	
		this.date = date;
	}

	@Override
	public String getTime() {
		return time;
	}
	
	@Override
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * <b>Metodo</b> che restituisce il MAC address del dispositivo che ha creato questa istanza.
	 * @return MAC address dell'istanza.
	 */
	public String getMacAddr() {
		return macAddr;
	}

	/**
	 * <b>Metodo</b> che resituisce la versione del dispositivo legato a questo istanza.
	 * @return versione del dispositivo (più propriamente, della sua versione software).
	 */
	public String getVer() {
		return ver;
	}
	
	@Override
	public String toString() {
		
		String s = "Date " + getDate() + " " +
				   "Time" + getTime() + " " +
			       "Mac address " + getMacAddr() + " " +
				   "Version " + getVer() + "\n";
		
		return s;
		
	}

	/**
	 * <b>Metodo</b> che costruisce il metadato che descrive gli attributi dell'istanza ANcybFishData.
	 * @return JSONObject contenente tutte le informazioni di questa istanza.
	 */
	public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        
        jo.put("Date", this.getDate());
        jo.put("Time", this.getTime());
        jo.put("Mac address", this.getMacAddr());
        jo.put("Version", this.getVer());
        
        return jo;
	}
}
