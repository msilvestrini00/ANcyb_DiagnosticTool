package it.univpm.ancyb_MqttTest.MqttTestApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.ancyb_MqttTest.MqttTestApp.model.HelloWorldClass;
import it.univpm.ancyb_MqttTest.MqttTestApp.subsriber.Subscriber;

@RestController
public class SimpleRestController {	
	@GetMapping("/hello")
	public HelloWorldClass exampleMethod(@RequestParam(name="param1", defaultValue="World") String param1) {
		System.out.println(param1);
		return new HelloWorldClass("Giorgio", "Fiara");
	}
	
	@PostMapping("/hello")
	public HelloWorldClass exampleMethod2(@RequestBody HelloWorldClass body) {
		return body;
	}
	
	@GetMapping("/hello/prova")
	public HelloWorldClass provaMethod(@RequestParam(name="param1", defaultValue="World") String param1) {
		System.out.println(param1);
		String str = String.valueOf(Subscriber.m5data.size());
		System.out.println(str);
		//TODO invece di param1 o di str printa l'ultimo valore aggiunto.
		return new HelloWorldClass( str, Subscriber.m5data.get(Subscriber.m5data.size()-1));
	}
}
