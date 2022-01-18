package it.univpm.ancyb_diagnosticTool;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient;


/*
 * <b>Classe</b> di bootstrap
 * 
 * @author Giacomo Fiara
 * @author Manuele Silvestrini
 */
@SpringBootApplication
public class AncybDiagnosticToolApplication {

	public static void main(String[] args) throws MqttException, InterruptedException {
		SpringApplication.run(AncybDiagnosticToolApplication.class, args);
		
		@SuppressWarnings("unused")
		ANcybMqttClient mqttClient = new ANcybMqttClient();
		
	}
}
