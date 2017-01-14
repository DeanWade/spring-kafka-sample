package org.springframework.integration.samples.kafka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class KafkaCommandLineRunner implements CommandLineRunner{
	
	@Autowired
	@Qualifier("toKafka")
	private MessageChannel messageChannel;
	
	@Autowired
	@Qualifier("fromKafka")
	private PollableChannel pollableChannel;

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 1; i++) {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("key", "value");
			GenericMessage<String> message = new GenericMessage<>("foo" + i);
			System.out.println("Sent:" + message);
			messageChannel.send(message);
		}
		
		Message<?> received = pollableChannel.receive(10000);
		while (received != null) {
			System.out.println("Received:" + received);
			received = pollableChannel.receive(10000);
		}
		
	}

}
