package it.univpm.ancyb_diagnosticTool.mqtt.mqttClient;

import java.util.ArrayList;
import java.util.concurrent.Flow.Subscriber;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import it.univpm.ancyb_diagnosticTool.dataLogger.DataLogger;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.MqttDataReceived;
import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;

public class ANcybMqttClient implements MqttCallback {
	
	/*
    @param url: the broker url
    @param qos: quality of service for this message transport to mosquitto broker
    @param topic: the topic under which message would be published
    @param clientID: the ID of this publisher (MqTT) client
    */
    
	MqttClient subscriber;
	private DataLogger dataLog;
	public static ArrayList<DataReceived> m5data;
	
    public ANcybMqttClient (String url, String topic, int qos, String clientID){
        
    	//TODO qui devo creare un Array List dove memorizzare tutti i subscribe e devo creare il file di log
    	m5data = new ArrayList<DataReceived>();
    	
        try {
            subscriber = new MqttClient(url, clientID);
            subscriber.setCallback(this);
            subscriber.connect();
            subscriber.subscribe(topic);
            //subscriber.disconnect();
        } catch (MqttException ex) {
        	//TODO da capire questa eccezione
            Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, "subscriber could not be created", ex);
        }
        
        //creo il datalogger 
        dataLog = new DataLogger();
	}

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("subscriber lost connection");
        System.out.println(cause);
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String str = new String(message.getPayload());
		System.out.println("message arrived to subscriber from M5: "+ str);
		
		//aggiungo il messaggio all'arrayList e scrivo l'arraylist sul file corrispondente
		DataReceived data = MqttDataReceived.createDataObj(str);
		if(data!=null) {
			m5data.add(data);
			this.dataLog.write(data);
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("i donot know how a subscriber can \"deliver\" messages!!!");
		
	}

}
