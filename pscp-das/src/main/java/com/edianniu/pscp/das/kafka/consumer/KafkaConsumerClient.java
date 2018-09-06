package com.edianniu.pscp.das.kafka.consumer;

import java.io.Serializable;

import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumerClient implements Serializable {
	private static final long serialVersionUID = 1L;
	private String topicName;
	private KafkaConsumer<String, String> consumer;

	public String getTopicName() {
		return topicName;
	}

	public KafkaConsumer<String, String> getConsumer() {
		return consumer;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public void setConsumer(KafkaConsumer<String, String> consumer) {
		this.consumer = consumer;
	}
}
