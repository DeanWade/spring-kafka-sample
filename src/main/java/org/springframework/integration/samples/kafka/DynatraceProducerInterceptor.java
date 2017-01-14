package org.springframework.integration.samples.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.dynatrace.adk.DynaTraceADKFactory;
import com.dynatrace.adk.Tagging;
import com.dynatrace.adk.Tagging.CustomTag;

public class DynatraceProducerInterceptor implements ProducerInterceptor<String, String>{
	
	public DynatraceProducerInterceptor(){
		// initialize the ADK factory (once on process startup)
		DynaTraceADKFactory.initialize();
	}

	@Override
	public void configure(Map<String, ?> arg0) {
	}

	@Override
	public void close() {
	}

	@Override
	public void onAcknowledgement(RecordMetadata recordMetadata, Exception exception) {
		
	}

	@Override
	public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
		try {
			Tagging tagging = DynaTraceADKFactory.createTagging();
			
			byte[] requestId = producerRecord.toString().getBytes();
			
//			if(!(tagging instanceof DummyTaggingImpl)){
			
			CustomTag customTag = tagging.createCustomTag(requestId);
			
			tagging.linkClientPurePath(false, customTag);
			
//			}
			
			System.out.println(tagging.getTagAsString());
			
		} catch (Throwable t) {
			// do nothing
		}

		return producerRecord;
	}

}
