package com.edianniu.pscp.message.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;

@Configuration
@ImportResource({"classpath:dubbo/consumer.xml"})
public class DubboConsumerConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        logger.info("dubbo consumer service running...");
    }
}
