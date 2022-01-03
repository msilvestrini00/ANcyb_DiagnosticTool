package it.univpm.ancyb_diagnosticTool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univpm.ancyb_diagnosticTool.mqtt.mqttClient.ANcybMqttClient;

@SpringBootApplication
public class AncybDiagnosticToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(AncybDiagnosticToolApplication.class, args);
		
		@SuppressWarnings("unused")
		ANcybMqttClient subscriber = new ANcybMqttClient("tcp://public.mqtthq.com:1883", "ANcybDiagnosticTool", 1, "subscriber");
		
	}

}
