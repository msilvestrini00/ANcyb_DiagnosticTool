package it.univpm.ancyb_MqttTest.MqttTestApp.newClient;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import it.univpm.ancyb_MqttTest.MqttTestApp.dataLogger.DataLogger;
import it.univpm.ancyb_MqttTest.MqttTestApp.model.DataReceived;

public class Mqtt {

    private static final String MQTT_PUBLISHER_ID = "spring-server-ancyb";
    private static final String MQTT_SERVER_ADDRES= "tcp://public.mqtthq.com:1883";
    private static IMqttClient instance;
    Mqtt mqttClient;
    DataLogger dataLog;
	public static ArrayList<DataReceived> m5data;

    public static IMqttClient getInstance() {
        
    	try {
            if (instance == null) {
                instance = new MqttClient(MQTT_SERVER_ADDRES, MQTT_PUBLISHER_ID);
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
            return Mqtt.getInstance();
        }
        return instance;
    }

    public Mqtt() {
    	Mqtt.getInstance();
    	m5data = new ArrayList<DataReceived>();
        dataLog = new DataLogger();
    }
    
    public void subscribe(final String topic) throws MqttException, InterruptedException {
    	
    	System.out.println("Sottoscrivo dal server:");

    	Mqtt.getInstance().subscribeWithResponse(topic, (tpic, msg) -> {
    		String str = new String(msg.getPayload());
    		System.out.println(msg.getId() + " -> " + str);
    		/*DataReceived data = MqttDataReceived.createDataObj(str);
    		if(data!=null) {
    			m5data.add(data);
    			this.dataLog.write(data);
    		}*/
    		dataLog.write(str);
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
    	
        Mqtt.getInstance().publish(/*messagePublishModel.getTopic()*/topic, mqttMessage);
    	
        System.out.println("Dovrei aver pubblicato >> " + payload);
        
        //TODO non so se conviene lasciarlo, altrimenti ogni volta che pubblico mi si disconnette il client
    	Mqtt.getInstance().disconnect();
    	
    	System.out.println("Dovrei essermi disconnesso");
    }
}