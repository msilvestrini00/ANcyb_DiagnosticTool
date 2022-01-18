package it.univpm.ancyb_diagnosticTool.mqtt.mqttClient;

import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
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

	/**
	 * Genera un Client Id nuovo ogni volta che viene avviato l'applicativo. Il nome dipende dall'orario corrente.
	 */
	private static final String mqttClientId = "spring-server-ancyb-" + Time.currentDateTime();
	
	/**
	 * Imposta l'indirizzo del broker server utilizzato dal sistema per lo scambio dati via protocollo MQTT.
	 */
    private static final String mqttBrokerAddr= "tcp://public.mqtthq.com:1883";
    
    /**
     * Istanza che implementa l'interfaccia {@link org.eclipse.paho.client.mqttv3.IMqttClient IMQTTClient}.
     */
    private static IMqttClient instance;
    
    /**
     * Datalogger utilizzato ogni qualvolta viene ricevuto un dato tramite il subscribe
     */
    DataLogger dataLog;
    
    /**
     * Manager delle stringhe che vengono ricevute e convertite in istanze ANcybFishData
     */
	private ANcybDataManager ancybDataManager;
	
	/**
	 * <b>Costruttore</b> del client. Viene stabilita la connessione con il broker ({@link it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient#getInstance() getInstance()}),
	 * creato un nuovo datalogger ({@link it.univpm.ancyb_diagnosticTool.dataLogger.DataLogger#DataLogger() DataLogger()}),
	 * un nuovo manager per la gestione dei dati ricevuti ({@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager#ANcybDataManager() ANcybDataManager()})
	 * e inizializzato il subsribe al broker (specificando il topic) ({@link it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient#subscribe(String) subscribe(final String topic)}).
	 * 
	 * @throws	MqttException
	 * @throws	InterruptedException
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient#getInstance() getInstance()
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient#ANcybDataManager() ANcybDataManager()
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient#DataLogger() DataLogger()
	 * @see it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient#subscribe(String) ANcybMqttClient.subscribe(String)
	 */
    public ANcybMqttClient() throws MqttException, InterruptedException {
    	
    	ANcybMqttClient.getInstance();
    	
    	ancybDataManager = new ANcybDataManager();
    	
        dataLog = new DataLogger();
        
        this.subscribe("ANcybDiagnosticTool");
        //this.publish("ANcybDiagnosticTool", "Risposta Di Prova", 1, false);
  		
    }
	
    /**
     * <b>Metodo</b> che restituisce un oggetto che implementa l'interfaccia {@link org.eclipse.paho.client.mqttv3.IMqttClient IMQTTClient}.
     * Questo metodo crea un nuova istanza se ancora non è stata creata,
     * imposta inoltre le opzioni di connessioni ({@link org.eclipse.paho.client.mqttv3.MqttConnectOptions#MqttConnectOptions() MqttConnectOptions()}), verifica lo stato di connessione ({@link org.eclipse.paho.client.mqttv3.IMqttClient#isConnected() isConnected()})
     * e lancia in automatico un nuovo tentativo in caso di errori.
     * 
     * Nel caso venga lanciata una {@link java.io.EOFException EOFException} potrebbe essere rilanciato il metodo più volte prima di stabilire la connessione con il broker.
     * 
     * @return un'istanza che può usare i metodi dell'interfaccia IMQTT per comunicare con un broker MQTT.
     * 
     * @see org.eclipse.paho.client.mqttv3.IMqttClient IMQTTClient
     * @see org.eclipse.paho.client.mqttv3.IMqttClient#subscribeWithResponse(String topic, IMqttMessageListener messageListener) subscribeWithResponse(String, IMqttMessageListener)
     * @see org.eclipse.paho.client.mqttv3.IMqttClient#publish(String topic, MqttMessage message) publish(String, MqttMessage)
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
            	System.out.println("Trying to connect to the server...");
            	instance.connect(options);
                System.out.println("Client connected");
            }
        } catch (MqttException e) {
        	System.err.println("Server connection lost!");
        	System.err.println("Exception: " + e);
        	System.err.println("Reconnecting...");
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
     * <b>Metodo</b> che sottoscrive ({@link org.eclipse.paho.client.mqttv3.IMqttClient#subscribeWithResponse(String topic, IMqttMessageListener messageListener) subscribeWithResponse(String, IMqttMessageListener)}) il client al topic inserito come parametro.
     * Il dato ricevuto viene poi mostrato in console, passato al metodo {@link it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager#createDataObj(String) createDataObj(String)} che restituisce un oggetto di tipo 
     * {@link it.univpm.ancyb_diagnosticTool.utilities.DataReceived DataReceived} che viene poi scritto sul data logger {@link it.univpm.ancyb_diagnosticTool.dataLogger.DataLogger#write(DataReceived data) write(DataReceived)}.
     * 
     * @param topic è il topic a cui si effettua il subscribe.
     * @throws MqttException
     * 
     * @see it.univpm.ancyb_diagnosticTool.utilities.DataReceived DataReceived
     * @see it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybDataManager ANcybDataManager
     * @see it.univpm.ancyb_diagnosticTool.dataLogger.DataLogger DataLogger
     */
    public void subscribe(final String topic) throws MqttException {
    	
    	System.out.println("Subscribe to the MQTT topic:");

    	ANcybMqttClient.getInstance().subscribeWithResponse(topic, (tpic, msg) -> {
    		String str = new String(msg.getPayload());
    		System.out.println(msg.getId() + " -> " + str);
    		try {
    			DataReceived data = ancybDataManager.createDataObj(str);
    			dataLog.write(data);
    		} catch(MqttStringMismatch | ArrayIndexOutOfBoundsException e) {
    			System.err.println("Exception:" + e);
    			System.err.println("MQTT data received from broker isn't valid!");
    			System.err.println("The data was not stored.");
    		}
    		
    	});
    	
    }

    /**
     * <b>Metodo</b> che effettua il publish sul topic passato come parametro. Genera il messaggio MQTT da pubblicare
     * sul broker, imposta le sue proprietà, effettua il publish e stampa a console il contenuto del messaggio.
     * 
     * <b>ATTENZIONE</b> - questo metodo non è stato implementato nell'applicativo.
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
    	
    	System.out.println("MQTT message initialized");
    	
    	ANcybMqttClient.getInstance().publish(topic, mqttMessage);
    	
        System.out.println(Time.currentDateTime() + " published >> " + payload);
        
        // In questo modo evito di disconettermi al client nel caso facessi un publish
        //ANcybMqttClient.getInstance().disconnect();
  
    }	
}
