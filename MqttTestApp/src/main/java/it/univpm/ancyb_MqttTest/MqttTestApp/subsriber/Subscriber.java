package it.univpm.ancyb_MqttTest.MqttTestApp.subsriber;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import it.univpm.ancyb_MqttTest.MqttTestApp.dataLogger.DataLogger;
import it.univpm.ancyb_MqttTest.MqttTestApp.model.DataReceived;
import it.univpm.ancyb_MqttTest.MqttTestApp.utilities.MqttStringOperator;

/*
A simple Subscriber class which would subscribe to a simple messages on a MqTT server 
on a particular topic.
*/

public class Subscriber implements MqttCallback{
    
    /*
    @param url: the broker url
    @param qos: quality of service for this message transport to mosquitto broker
    @param topic: the topic under which message would be published
    @param clientID: the ID of this publisher (MqTT) client
    */
    
	MqttClient subscriber;
	private DataLogger dataLog;
	public static ArrayList<DataReceived> m5data;
	
    public Subscriber (String url, String topic, int qos, String clientID){
        
    	//TODO qui devo creare un Array List dove memorizzare tutti i subscribe e devo creare il file di log
    	m5data = new ArrayList<DataReceived>();
    	
        try {
            subscriber = new MqttClient(url, clientID);
            subscriber.setCallback(this);
            subscriber.connect();
            subscriber.subscribe(topic);
            //subscriber.disconnect();
        } catch (MqttException ex) {
            Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, "subscriber could not be created", ex);
        }
        
        //creo il datalogger 
        dataLog = new DataLogger();
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("subscriber lost connection");
        System.out.println(thrwbl);
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception { 
        System.out.println("message arrived to subscriber from M5: "+new String(mm.getPayload()));
		//aggiungo il messaggio all'arrayList
        String str = new String(mm.getPayload());
        String[] strArr = MqttStringOperator.split(str);
        DataReceived data = new DataReceived(strArr);
        m5data.add(data);
        //scrivo l'arraylist sul file corrispondente
        this.dataLog.write(data);
        //subscriber.disconnect();
	}

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("i donot know how a subscriber can \"deliver\" messages!!!");
    }
    
}