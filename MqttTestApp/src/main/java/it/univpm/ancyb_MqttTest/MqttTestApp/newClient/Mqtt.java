package it.univpm.ancyb_MqttTest.MqttTestApp.newClient;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class Mqtt {

    private static final String MQTT_PUBLISHER_ID = "spring-server-ancyb";
    private static final String MQTT_SERVER_ADDRES= "tcp://public.mqtthq.com:1883";
    private static IMqttClient instance;
    Mqtt mqttClient;

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
        	System.out.println("Riconnessione...");
            return Mqtt.getInstance();
        }
        return instance;
    }

    public Mqtt() {
    	Mqtt.getInstance();
    }
    
    public void subscribe(final String topic) throws MqttException, InterruptedException {
    	
    	System.out.println("Sottoscrivo dal server:");

    	Mqtt.getInstance().subscribeWithResponse(topic, (tpic, msg) -> {
    		System.out.println(msg.getId() + " -> " + new String(msg.getPayload()));
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