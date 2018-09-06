package com.edianniu.pscp.message.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class KafkaProducerConfiguration {

	@Value(value = "${kafka.bootstrap.servers}")
    private String bootstrapServers;
	
	@Value(value = "${kafka.producer.acks}")
	private String acks; 
	
	@Value(value = "${kafka.producer.retries}")
	private int retries;
	
	@Value(value = "${kafka.producer.batch.size}")
	private int batchSize;
	
	@Value(value = "${kafka.producer.linger.ms}")
	private int lingerMs;
	
	@Value(value = "${kafka.producer.buffer.memory}")
	private int bufferMemory;
	
	public Properties buildProperties(){
		
		Properties props = new Properties();
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("bootstrap.servers",bootstrapServers);
		props.put("acks", acks);
		props.put("retries", retries);
		props.put("batch.size", batchSize);
		props.put("linger.ms", lingerMs);
		props.put("buffer.memory", bufferMemory);
		return props;
	}
	
}
