package it.univpm.ancyb_MqttTest.MqttTestApp.subsriber;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class MqttSubscribeSample implements MqttCallback {
	
	MqttClient mqttClient;
	MqttConnectOptions connOpt;

	static final String BROKER_URL = "tcp://public.mqtthq.com:1883";
	// static final String M2MIO_DOMAIN = "<Insert m2m.io domain here>";
	//static final String M2MIO_STUFF = "things";
	
	//Device ID
	static final String M2MIO_THING = "<Unique device ID>";
	
	// static final String M2MIO_USERNAME = "<m2m.io username>";
	// static final String M2MIO_PASSWORD_MD5 = "<m2m.io password (MD5 sum of
	// password)>";

	// the following two flags control whether this example is a publisher, a
	// subscriber or both
	static final Boolean subscriber = true;
	static final Boolean publisher = false;

	public void connectionLost(Throwable arg0) {
		
		System.out.println("Connection Lost");

	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
		
		System.out.println("Delivery Complete");

	}

	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		
		System.out.println("-------------------------------------------------");
		System.out.println("| Topic:" + arg0);
		System.out.println("| Message: " + new String(arg1.getPayload()));
		System.out.println("-------------------------------------------------");
		//mqttClient.disconnect();
	}

	/**
	 * 
	 * MAIN
	 * 
	 */
	public static void main(String[] args) {

		MqttSubscribeSample smc = new MqttSubscribeSample();
		smc.runClient();

	}

	/**
	 * 
	 * runClient The main functionality of this simple example. Create a MQTT
	 * client, connect to broker, pub/sub, disconnect.
	 * 
	 */
	public void runClient() {
		// setup MQTT Client
		String clientID = M2MIO_THING;
		connOpt = new MqttConnectOptions();

		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
		// connOpt.setUserName(M2MIO_USERNAME);
		// connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());

		// Connect to Broker
		try {
			mqttClient = new MqttClient(BROKER_URL, clientID);
			//rendi il client asincrono
			mqttClient.setCallback(MqttSubscribeSample.this);
			mqttClient.connect(connOpt);

		} catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("Connected to " + BROKER_URL);

		// setup topic
		// topics on m2m.io are in the form <domain>/<stuff>/<thing>
		String myTopic = "SendTime";
		MqttTopic topic = mqttClient.getTopic(myTopic);

		// subscribe to topic if subscriber
		if (subscriber) {
			try {
				int subQoS = 1;
				mqttClient.subscribe(topic.getName(), subQoS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// publish messages if publisher
		if (publisher) {
			for (int i = 1; i <= 10; i++) {
				String pubMsg = "{\"pubmsg\":" + i + "}";
				int pubQoS = 1;
				MqttMessage message = new MqttMessage(pubMsg.getBytes());
				message.setQos(pubQoS);
				message.setRetained(false);

				// Publish the message
				// System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
				MqttDeliveryToken token = null;
				try {
					// publish message to broker
					// token = topic.publish(message);
					// Wait until the message has been delivered to the broker
					token.waitForCompletion();
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// disconnect
		try {
			// wait to ensure subscribed messages are delivered
			if (subscriber) {
				Thread.sleep(5000);
			}
			mqttClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}