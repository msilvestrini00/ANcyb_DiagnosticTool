package it.univpm.ancyb_MqttTest.MqttTestApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univpm.ancyb_MqttTest.MqttTestApp.subsriber.Subscriber;

@SpringBootApplication
public class MqttTestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqttTestAppApplication.class, args);
		
		//La parte di subscriber fa riferimento unicamente alla classe Subscriber
		System.out.println("Programma di test subscriber");
        Subscriber subscriber = new Subscriber ("tcp://public.mqtthq.com:1883", "SendTime", 1, "subscriber");
    
	}

}
