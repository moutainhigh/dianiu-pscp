package com.edianniu.pscp.message.kafka.consumer;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.message.bean.MessageInfo;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.config.KafkaConsumerConfiguration;
import com.edianniu.pscp.message.result.SmsResult;
import com.edianniu.pscp.message.service.dubbo.SmsInfoService;

/**
 * @author cyl
 */
@Service
@Repository("smsConsumerService")
public class SmsConsumerService extends AbsConsumerService {
	private static final Logger logger = LoggerFactory
			.getLogger(SmsConsumerService.class);
	/*private static final String CONF_FILENAME = SmsConsumerService.class
			.getClassLoader().getResource("kafkaConsumer.properties").getPath();*/
	private KafkaConsumer<String, String> consumer = null;
	private String topicName = "pscp_sms";
	
	@Autowired
    private KafkaConsumerConfiguration kc;
	@Autowired
	private SmsInfoService smsInfoService;

	public SmsConsumerService() {

	}

	@PostConstruct
	public void listener() {
		init();
		consumer.subscribe(Arrays.asList(topicName));
		new Thread(new ListenerThread(consumer)).start();
		System.out.println("end");

	}

	class ListenerThread implements Runnable {
		KafkaConsumer<String, String> consumer;

		public ListenerThread(KafkaConsumer<String, String> consumer) {
			this.consumer = consumer;
		}

		@Override
		public void run() {
			while (true) {
				logger.debug(topicName + "consumer loading...");
				try {
					ConsumerRecords<String, String> records = consumer
							.poll(1000);

					for (ConsumerRecord<String, String> record : records) {
						logger.info(
								"topic={},offset ={}, key = {}, value = {}",
								record.topic(), record.offset(), record.key(),
								record.value());
						MessageInfo messageInfo = JSONObject.parseObject(
								record.value(), MessageInfo.class);
						SmsResult result = smsInfoService.sendSms(
								messageInfo.getMobile(),
								MessageId.parse(messageInfo.getMsgId()),
								messageInfo.getParams());
						if (!result.isSuccess()) {
							logger.error("sms result:{}",
									JSONObject.toJSONString(result));
						}
					}
					consumer.commitAsync();
					Thread.sleep(100L);
				}catch (InterruptedException e) {
					logger.error("InterruptedException {}", e);
				}catch(Exception e){
					logger.error("Exception {}", e);
				}
			}

		}

	}

	@Override
	protected void init() {
		try {
			consumer = new KafkaConsumer<>(kc.buildProperties());
		} catch (KafkaException e) {
			logger.error("kafka consumer load error:{}", e);
		}
	}

	@Value(value = "${sms.kafka.topic.name}")
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
}
