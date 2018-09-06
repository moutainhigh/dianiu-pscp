package com.edianniu.pscp.das.config;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.edianniu.pscp.das.kafka.consumer.KafkaConsumerClient;

/**
 * ClassName: KafkaConsumerConfiguration
 * Author: yanlin.chen
 * CreateTime: 2018-01-29 17:53
 */
@Configuration
public class KafkaConsumerConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private KafkaConsumerClient kafkaConsumerClient = null;
    @Value(value = "${kafka.meterlog.consumer.bootstrap.servers}")
    private String bootstrapServers;
    @Value(value = "${kafka.meterlog.consumer.group.id}")
    private String groupId;
    @Value(value = "${kafka.meterlog.consumer.enable.auto.commit}")
    private boolean enableAutoCommit;
    @Value(value = "${kafka.meterlog.consumer.auto.commit.interval.ms}")
    private int autoCommitIntervalMs;
    @Value(value = "${kafka.meterlog.consumer.max.poll.interval.ms}")
    private int maxPollIntervalMs;
    @Value(value = "${kafka.meterlog.consumer.max.poll.records}")
    private int maxPollRecords;
    @Value(value = "${kafka.meterlog.consumer.session.timeout.ms}")
    private int sessionTimeoutMs;
    @Value(value = "${kafka.meterlog.topic.name}")
    private String topicName;
    @Bean
    public KafkaConsumerClient  init() {
    	 logger.info("Building KafkaConsumer");
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
		try{
			KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
			consumer.subscribe(Arrays.asList(topicName));
			kafkaConsumerClient=new KafkaConsumerClient();
			kafkaConsumerClient.setConsumer(consumer);
			kafkaConsumerClient.setTopicName(topicName);
		}
		catch(Exception e){
			logger.error("Error build KafkaConsumer: {}", e.getMessage());
		}
		return kafkaConsumerClient;
    }
}
