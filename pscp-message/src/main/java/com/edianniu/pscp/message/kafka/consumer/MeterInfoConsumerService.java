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
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.message.bean.GateWayInfo;
import com.edianniu.pscp.message.bean.MeterInfo;
import com.edianniu.pscp.message.config.KafkaConsumerConfiguration;
import com.edianniu.pscp.message.meter.service.MeterService;

/**
 * 仪表信息kafka消费者
 * @author cyl
 */
@Service
@Repository("meterInfoConsumerService")
public class MeterInfoConsumerService extends AbsConsumerService {
	private static final Logger logger = LoggerFactory
			.getLogger(MeterInfoConsumerService.class);
	/*private static final String CONF_FILENAME = MeterInfoConsumerService.class
			.getClassLoader().getResource("kafkaConsumer.properties").getPath();*/
	private KafkaConsumer<String, String> consumer = null;
	private String topicName = "meter_info";
	@Autowired
	private MeterService meterService;
	
	@Autowired
    private KafkaConsumerConfiguration kc;
	
	public MeterInfoConsumerService() {

	}

	@PostConstruct
	public void listener() {
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
					ConsumerRecords<String, String> records = consumer
							.poll(1000);
					logger.info("meterIno handler start pull records size:{}",records.count());
					for (ConsumerRecord<String, String> record : records) {
						logger.info(
								"topic={}, offset={}, key={}, value={}",
								record.topic(), record.offset(), record.key(),
								record.value());
						if(record.key().startsWith("METER#")) { //仪表
							MeterInfo meterInfo = JSONObject.parseObject(
									record.value(), MeterInfo.class);
							logger.info("meterInfo:{}",JSON.toJSONString(meterInfo));
							meterService.handleMeter(meterInfo);
						} else { // 网关设备
							//上线或者下线时上传的仪表设备信息
							GateWayInfo gateWayInfo = JSONObject.parseObject(
									record.value(), GateWayInfo.class);
							meterService.handleGateWay(gateWayInfo); 
							logger.info("gateWayInfo:{}",JSON.toJSONString(gateWayInfo));
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

	@Value(value = "${meter.info.kafka.topic.name}")
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
}
