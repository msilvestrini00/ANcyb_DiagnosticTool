package it.univpm.ancyb_MqttTest.MqttTestApp;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univpm.ancyb_MqttTest.MqttTestApp.newClient.Mqtt;

@SpringBootApplication
public class MqttTestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqttTestAppApplication.class, args);
		
		//La parte di subscriber fa riferimento unicamente alla classe Subscriber
		System.out.println(">> Programma di test della Spring Boot App >>");
        
		//@SuppressWarnings("unused")
		//Subscriber subscriber = new Subscriber ("tcp://public.mqtthq.com:1883", "ANcybDiagnosticTool", 1, "subscriber");
    	
		Mqtt mqttClient = new Mqtt();
		
		//Questo è il subscriber
		try {
			mqttClient.subscribe("ANcybDiagnosticTool");
		} catch (MqttException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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

}
