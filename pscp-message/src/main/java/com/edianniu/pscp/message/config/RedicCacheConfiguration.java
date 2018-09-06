package com.edianniu.pscp.message.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import javax.annotation.PostConstruct;

@Configuration
@ImportResource({"classpath:cache/redis_cache.xml"})
public class RedicCacheConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        logger.info("dubbo redis cache service running...");
    }
}
