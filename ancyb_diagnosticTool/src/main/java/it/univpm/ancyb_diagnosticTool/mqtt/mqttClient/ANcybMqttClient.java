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
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.MqttDataReceived;
import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;
import it.univpm.ancyb_diagnosticTool.utilities.Time;

public class ANcybMqttClient {

	private static final String mqttClientId = "spring-server-ancyb-" + Time.currentDateTime();
    private static final String mqttBrokerAddr= "tcp://public.mqtthq.com:1883";
    private static IMqttClient instance;
    DataLogger dataLog;
	public static ArrayList<DataReceived> m5data;
	
    public ANcybMqttClient() throws MqttException, InterruptedException {
    	ANcybMqttClient.getInstance();
    	m5data = new ArrayList<DataReceived>();
        dataLog = new DataLogger();
        
        //Questo è il subscriber
        this.subscribe("ANcybDiagnosticTool");
  		
  		//Questo è il publisher, ricorda che si disconnette finita la pubblicazione
  		/*try {
  			mqttClient.publish("ANcybDiagnosticTool", "Risposta Di Prova", 1, false);
  		} catch (MqttPersistenceException e) {
  			e.printStackTrace();
  		} catch (MqttException e) {
  			e.printStackTrace();
  		}
  		*/
    }
	
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
    
    public void subscribe(final String topic) throws MqttException {
    	
    	System.out.println("Sottoscrivo dal server:");

    	ANcybMqttClient.getInstance().subscribeWithResponse(topic, (tpic, msg) -> {
    		String str = new String(msg.getPayload());
    		System.out.println(msg.getId() + " -> " + str);
    		try {
    			DataReceived data = MqttDataReceived.createDataObj(str);
        		m5data.add(data);
        		dataLog.write(data);
    		} catch(MqttStringMismatch e) {
    			System.err.println("Dato ricevuto da MQTT broker non valido!");
    			System.err.println("Il dato non è stato memorizzato");
    		}
    		
    	});
    	
    }

    public void publish(final String topic, final String payload, int qos, boolean retained)
    		throws MqttPersistenceException, MqttException {
    	
    	System.out.println("Dentro publish");
    	
    	MqttMessage mqttMessage = new MqttMessage();
    	mqttMessage.setPayload(payload.getBytes());
    	mqttMessage.setQos(qos);
    	mqttMessage.setRetained(retained);
    	
    	System.out.println("Impostato mqttMessage");
    	
    	ANcybMqttClient.getInstance().publish(/*messagePublishModel.getTopic()*/topic, mqttMessage);
    	
        System.out.println("Dovrei aver pubblicato >> " + payload);
        
        //TODO non so se conviene lasciarlo, altrimenti ogni volta che pubblico mi si disconnette il client
        ANcybMqttClient.getInstance().disconnect();
    	
    	System.out.println("Dovrei essermi disconnesso");
    }	
}
