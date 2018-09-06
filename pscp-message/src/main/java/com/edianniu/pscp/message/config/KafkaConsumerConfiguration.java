package com.edianniu.pscp.message.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class KafkaConsumerConfiguration {
	
	@Value(value = "${kafka.bootstrap.servers}")
    private String bootstrapServers;
    
	@Value(value = "${kafka.consumer.group.id}")
    private String groupId;
    
	@Value(value = "${kafka.consumer.enable.auto.commit}")
    private boolean enableAutoCommit;
    
	@Value(value = "${kafka.consumer.auto.commit.interval.ms}")
    private int autoCommitIntervalMs;
    
	@Value(value = "${kafka.consumer.max.poll.interval.ms}")
    private int maxPollIntervalMs;
    
	@Value(value = "${kafka.consumer.max.poll.records}")
    private int maxPollRecords;
    
	@Value(value = "${kafka.consumer.session.timeout.ms}")
    private int sessionTimeoutMs;
    
	public Properties buildProperties() {
		Properties props = new Properties();
		props.put("key.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("bootstrap.servers",bootstrapServers);
		props.put("group.id",groupId);
		props.put("enable.auto.commit",enableAutoCommit);
		props.put("auto.commit.interval.ms",autoCommitIntervalMs);
		props.put("max.poll.interval.ms",maxPollIntervalMs);
		props.put("max.poll.records",maxPollRecords);
		props.put("session.timeout.ms",sessionTimeoutMs);
		return props;
	}
    
    
    
}
