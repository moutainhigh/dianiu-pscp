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
import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.message.bean.WarningInfo;
import com.edianniu.pscp.message.config.KafkaConsumerConfiguration;
import com.edianniu.pscp.message.meter.service.MeterService;

/**
 * 告警kafak消费者
 */
@Service
@Repository("warningConsumerService")
public class WarningConsumerService extends AbsConsumerService{
	
	private static final Logger logger = LoggerFactory.getLogger(WarningConsumerService.class);
	
	/*private static final String CONF_FILENAME = WarningConsumerService.class
			.getClassLoader().getResource("kafkaConsumer.properties").getPath();*/
	private KafkaConsumer<String, String> consumer = null;
	private String topicName = "meter_warning";
	
	@Autowired
    private KafkaConsumerConfiguration kc;
	@Autowired
	private MeterService meterService;
	
	public WarningConsumerService() {

	}
	
	@PostConstruct
	public void listener(){
		init();
		consumer.subscribe(Arrays.asList(topicName));
		new Thread(new ListenerThread(consumer)).start();
	}
	
	class ListenerThread implements Runnable {
		KafkaConsumer<String, String> consumer;
		public ListenerThread(KafkaConsumer<String, String> consumer) {
			this.consumer = consumer;
		}

		@Override
		public void run() {
			while (true) {
				logger.debug(topicName + " lister start."); 
				try {
					ConsumerRecords<String, String> records = consumer.poll(1000L);
					logger.info("warning handler start pull records size:{}", records.count());
					for (ConsumerRecord<String, String> record : records) {
						logger.info("topic={}, offset={}, key={}, value={}",
								record.topic(), record.offset(), record.key(), record.value());
						WarningInfo warningInfo = JSON.parseObject(record.value(), WarningInfo.class);
						meterService.handleWarning(warningInfo);
						logger.info("warningInfo:{}", JSON.toJSONString(warningInfo));
					}
					consumer.commitAsync();
					Thread.sleep(100L);
				} catch(InterruptedException e){
					logger.error("InterruptedException {}", e);
				} catch (Exception e) {
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

	@Value(value = "${meter.warning.kafka.topic.name}")
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	

}
