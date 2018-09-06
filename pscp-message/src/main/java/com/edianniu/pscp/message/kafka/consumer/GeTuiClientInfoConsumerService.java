package com.edianniu.pscp.message.kafka.consumer;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.message.config.KafkaConsumerConfiguration;
import com.edianniu.pscp.message.getuiclient.domain.GeTuiClient;
import com.edianniu.pscp.message.getuiclient.service.GeTuiClientService;
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
import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * @author cyl
 */
@Service
@Repository("getuiClientInfoConsumerService")
public class GeTuiClientInfoConsumerService extends AbsConsumerService {
    private static final Logger logger = LoggerFactory
            .getLogger(GeTuiClientInfoConsumerService.class);
    /*private static final String CONF_FILENAME = GeTuiClientInfoConsumerService.class
            .getClassLoader().getResource("kafkaConsumer.properties").getPath();*/
    
    private KafkaConsumer<String, String> consumer = null;
    private String topicName = "pscp_getuiclient";
    
    @Autowired
    private GeTuiClientService geTuiClientService;
    
    @Autowired
    private KafkaConsumerConfiguration kc;

    public GeTuiClientInfoConsumerService() {

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
                logger.debug(topicName + " consumer listening...");
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("topic={},offset ={}, key = {}, value = {}",
                            record.topic(), record.offset(), record.key(), record.value());
                    GeTuiClient geTuiClient = JSONObject.parseObject(record.value(), GeTuiClient.class);
                    geTuiClientService.bindClient(geTuiClient);
                }
                consumer.commitAsync();
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    logger.error("InterruptedException {}", e);
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

    public String getTopicName() {
        return topicName;
    }

    @Value(value = "${getuiclient.kafka.topic.name}")
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
