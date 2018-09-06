package com.edianniu.pscp.message.kafka.producer;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.edianniu.pscp.message.config.KafkaProducerConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.PostConstruct;

@Service
@Repository("kafkaProducerService")
public class DefaultKafkaProducerService extends AbsKafkaProducerService{
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultKafkaProducerService.class);
	
	@Autowired
	private KafkaProducerConfiguration kp;
	
	private static Producer<String, String> producer = null;
	
	private String topic_sms = "pscp_sms";
	
	private String topic_msg = "pscp_msg";

	private String topic_getuiclient = "pscp_getuiclient";
	
	private List<String> meterLogTopics=new ArrayList<String>();
    private String topic_meter_info="meter-info";
    private String topic_warning_info="warning_info";
    
   @Value(value = "${meter.log.kafka.topic.names}")
	public void setMeterLogTopics(String topicNames) {
    	if(StringUtils.isNoneBlank(topicNames)){
    		String[] temps=StringUtils.split(topicNames,",");
    		for(String topicName:temps){
    			meterLogTopics.add(topicName);
    		}
    	}
	}
    
	@Value(value = "${meter.info.kafka.topic.name}")
	public void setTopic_meter_info(String topic_meter_info) {
		this.topic_meter_info = topic_meter_info;
	}
	
	@Value(value = "${meter.warning.kafka.topic.name}")
	public void setTopic_warning_info(String topic_warning_info) {
		this.topic_warning_info = topic_warning_info;
	}
	
	@Value(value = "${sms.kafka.topic.name}")
	public void setTopic_sms(String topic_sms) {
		this.topic_sms = topic_sms;
	}

	@Value(value = "${msg.kafka.topic.name}")
	public void setTopic_msg(String topic_msg) {
		this.topic_msg = topic_msg;
	}

	@Value(value = "${getuiclient.kafka.topic.name}")
	public void setTopic_getuiclient(String topic_getuiclient) {
		this.topic_getuiclient = topic_getuiclient;
	}

	public DefaultKafkaProducerService() {
		
	}

	@Override
	@PostConstruct
	protected void init() {
		try {
			logger.info("kafka producer init...");
			Properties props = kp.buildProperties();
			producer = new KafkaProducer<String, String>(props);
			logger.info("kafka producer init success...");
		} catch (Exception e) {
			logger.error("kafka producer init error:{}", e);
		}
	}

	public void sendSms(String key, String value) {
		try {
			Future<RecordMetadata> future = producer
					.send(new ProducerRecord<String, String>(topic_sms, key,
							value));
			logger.info("send sms,isDone:{},", future.isDone(), future.get()
					.toString());
		} catch (InterruptedException e) {
			logger.error("sendSms:{}", e);
		} catch (ExecutionException e) {
			logger.error("sendSms:{}", e);
		} catch (Exception e) {
			logger.error("sendMsg:{}", e);
		}
	}

	public void sendMsg(String key, String value) {
		try {
			Future<RecordMetadata> future = producer
					.send(new ProducerRecord<String, String>(topic_msg, key,
							value));
			logger.info("send msg,isDone:{},", future.isDone(), future.get()
					.toString());
		} catch (InterruptedException e) {
			logger.error("sendMsg:{}", e);
		} catch (ExecutionException e) {
			logger.error("sendMsg:{}", e);
		} catch (Exception e) {
			logger.error("sendMsg:{}", e);
		}

	}

	public void sendGeTuiClient(String key, String value) {
		try {
			Future<RecordMetadata> future = producer
					.send(new ProducerRecord<String, String>(topic_getuiclient,
							key, value));
			logger.info("send getuiclient,isDone:{},", future.isDone(), future.get()
					.toString());
		} catch (InterruptedException e) {
			logger.error("sendGeTuiClient:{}", e);
		} catch (ExecutionException e) {
			logger.error("sendGeTuiClient:{}", e);
		} catch (Exception e) {
			logger.error("sendGeTuiClient:{}", e);
		}
	}
	
	public void sendMeterInfo(String key, String value) {
		try {
			Future<RecordMetadata> future = producer
					.send(new ProducerRecord<String, String>(topic_meter_info,
							key, value));
			logger.info("send meterInfo,isDone:{},", future.isDone(), future.get()
					.toString());
		} catch (InterruptedException e) {
			logger.error("sendMeterInfo:{}", e);
		} catch (ExecutionException e) {
			logger.error("sendMeterInfo:{}", e);
		} catch (Exception e) {
			logger.error("sendMeterInfo:{}", e);
		}
	}
	
	public void sendWarningInfo(String key, String value){
		try {
			Future<RecordMetadata> future = producer
					.send(new ProducerRecord<String, String>(topic_warning_info, 
							key, value));
			logger.info("send warningInfo,isDone{},", future.isDone(), future.get().toString());
		} catch(InterruptedException e){
			logger.error("sendWarningInfo:{}", e);
		} catch (ExecutionException e) {
			logger.error("sendWarningInfo:{}", e);
		}catch (Exception e) {
			logger.error("sendWarningInfo:{}", e);
		}
	}
	
	public void sendMeterLog(String key, String value) {
		try {
			String topicName=getTopicName(key);
			if(StringUtils.isBlank(topicName)){
				throw new RuntimeException("not found topicName");
			}
			Future<RecordMetadata> future = producer
					.send(new ProducerRecord<String, String>(topicName,
							key, value));
			logger.info("send meterInfoLog,isDone:{},", future.isDone(), future.get()
					.toString());
		} catch (InterruptedException e) {
			logger.error("sendMeterInfoLog:{}", e);
		} catch (ExecutionException e) {
			logger.error("sendMeterInfoLog:{}", e);
		} catch (Exception e) {
			logger.error("sendMeterInfoLog:{}", e);
		}

	}
	/**
	 * topic的配置必须要保证顺序
	 * 每个topic配置4个分区，pscp-das消费的时候开启4个线程进行消费，每个线程消费一个分区的数据。
	 * @param key
	 * @return
	 */
	private String getTopicName(String key){
		if(StringUtils.isBlank(key)){
			return null;
		}
		if(meterLogTopics.isEmpty()){
			return null;
		}
		else{
			if(meterLogTopics.size()==1){
				return meterLogTopics.get(0);
			}
			else{
				//根据key的第一位进行topic的派发
				//仪表编号规则
				//0 meter_log_0
				//1 meter_log_1
				//2 meter_log_2
				//3 meter_log_3
				//4 meter_log_4
				//根据key进行匹配
				if(key.startsWith("0")){
					return meterLogTopics.get(0);
				}
				else if(key.startsWith("1")){
					if(meterLogTopics.size()>=2){
						return meterLogTopics.get(1);
					}
					else{
						return meterLogTopics.get(0);
					}
					
				}
				else if(key.startsWith("2")){
					if(meterLogTopics.size()>=3){
						return meterLogTopics.get(2);
					}
					else{
						return meterLogTopics.get(0);
					}
					
				}
				else if(key.startsWith("3")){
					if(meterLogTopics.size()>=4){
						return meterLogTopics.get(3);
					}
					else{
						return meterLogTopics.get(0);
					}
					
				}
				else if(key.startsWith("4")){
					if(meterLogTopics.size()>=5){
						return meterLogTopics.get(4);
					}
					else{
						return meterLogTopics.get(0);
					}
					
				}
				return meterLogTopics.get(0);
			}
		}
	}
}
