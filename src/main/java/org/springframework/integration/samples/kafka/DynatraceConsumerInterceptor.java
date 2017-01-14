package org.springframework.integration.samples.kafka;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import com.dynatrace.adk.DynaTraceADKFactory;
import com.dynatrace.adk.Tagging;

public class DynatraceConsumerInterceptor implements ConsumerInterceptor<String, String>{
	
	public DynatraceConsumerInterceptor(){
		// initialize the dynaTrace ADK
		DynaTraceADKFactory.initialize();
	}

	@Override
	public void configure(Map<String, ?> configs) {
	}

	@Override
	public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
		try {
			Tagging tagging = DynaTraceADKFactory.createTagging();
			
//			if(!(tagging instanceof DummyTaggingImpl)){
				
			tagging.setCustomTag(records.toString().getBytes());
			
			tagging.startServerPurePath();
			
			System.out.println(tagging.getTagAsString());
				
//			}
		} catch (Throwable t) {
			// do nothing
		}
		return records;
	}

	@Override
	public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
	}

	@Override
	public void close() {
	}

}
