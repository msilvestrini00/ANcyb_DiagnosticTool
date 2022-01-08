package it.univpm.ancyb_diagnosticTool.mqtt.mqttClient;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch;
import it.univpm.ancyb_diagnosticTool.dataLogger.DataLogger;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager;
import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

/**
 * Classe che implementa i vari metodi per creare e gestire un MQTT client
 * 
 * @author Giacomo Fiara
 *
 */
public class ANcybMqttClient {

	private static final String mqttClientId = "spring-server-ancyb-" + Time.currentDateTime();
    private static final String mqttBrokerAddr= "tcp://public.mqtthq.com:1883";
    private static IMqttClient instance;
    DataLogger dataLog;
	public static ArrayList<DataReceived> m5data;
	
	/**
	 * Costruttore del client. Viene inoltre creato un nuovo datalogger.
	 * 
	 * @throws	MqttException
	 * @throws	InterruptedException
	 */
    public ANcybMqttClient() throws MqttException, InterruptedException {
    	
    	ANcybMqttClient.getInstance();
        dataLog = new DataLogger();
        
        this.subscribe("ANcybDiagnosticTool");
        //this.publish("ANcybDiagnosticTool", "Risposta Di Prova", 1, false);
  		
    }
	
    /**
     * Metodo che restituisce un oggetto che implementa l'interfaccia IMqttClient.
     * Questo metodo crea un nuova istanza se ancora non è stata creata,
     * imposta inoltre le opzioni di connessioni, verifica lo stato di connessione 
     * e lancia in automatico un nuovo tentativo in caso di errori.
     * 
     * @return	l'oggetto che può usare i metodi dell'interfaccia IMQTT per comunicare con un broker MQTT.
     */
    public static IMqttClient getInstance() {
        
    	try {
            if (instance == null) {
                instance = new MqttClient(mqttBrokerAddr, mqttClientId);
            }
            
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(30);
            options.setKeepAliveInterval(15);

            if (!instance.isConnected()) {
            	System.out.println("Cerco di connettermi al server...");
            	instance.connect(options);
                System.out.println("Client connesso");
            }
        } catch (MqttException e) {
        	System.err.println("Connessione con il server persa!");
        	System.err.println("Exception: " + e);
        	System.err.println("Riconnessione...");
        	try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
            return ANcybMqttClient.getInstance();
        }
        return instance;
    }
    
    /**
     * Questo metodo sottoscrive il client al topic inserito come parametro.
     * Il dato ricevuto viene poi mostrato in console e scritto sul data logger
     * creato precedentemente dal costruttore nel caso abbia una struttura nota.
     * 
     * @param	topic	è il topic a cui si vuole fare la sottoscrizione
     * @throws	MqttException
     */
    public void subscribe(final String topic) throws MqttException {
    	
    	System.out.println("Sottoscrivo dal server:");

    	ANcybMqttClient.getInstance().subscribeWithResponse(topic, (tpic, msg) -> {
    		String str = new String(msg.getPayload());
    		System.out.println(msg.getId() + " -> " + str);
    		try {
    			DataReceived data = ANcybDataManager.createDataObj(str);
        		dataLog.write(data);
    		} catch(MqttStringMismatch e) {
    			System.err.println("Exception:" + e);
    			System.err.println("Dato ricevuto da MQTT broker non valido!");
    			System.err.println("Il dato non è stato memorizzato");
    		}
    		
    	});
    	
    }

    /**
     * 
     * 
     * @param	topic		è il topic in cui si vuole fare la pubblicazione
     * @param	payload		è la stringa che si vuole pubblicare sul broker
     * @param	qos			è il parametro "Quality Of Service" (può essere impostato da 0 a 2)
     * @param	retained	
     * @throws	MqttPersistenceException
     * @throws	MqttException
     */
    public void publish(final String topic, final String payload, int qos, boolean retained)
    		throws MqttPersistenceException, MqttException {
    	
    	MqttMessage mqttMessage = new MqttMessage();
    	mqttMessage.setPayload(payload.getBytes());
    	mqttMessage.setQos(qos);
    	mqttMessage.setRetained(retained);
    	
    	System.out.println("Impostato mqttMessage");
    	
    	ANcybMqttClient.getInstance().publish(topic, mqttMessage);
    	
        System.out.println(Time.currentDateTime() + " pubblicato >> " + payload);
        
        // In questo modo evito di disconettermi al client nel caso facessi un publish
        //ANcybMqttClient.getInstance().disconnect();
  
    }	
}
