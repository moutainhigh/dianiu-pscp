package com.edianniu.pscp.das.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;

/**
 * ClassName: RedicCacheConfiguration
 * Author: yanlin.chen
 * CreateTime: 2018-01-29 17:53
 */
@Configuration
@ImportResource({"classpath:cache/redis_cache.xml"})
public class RedicCacheConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        logger.info("dubbo redis cache service running...");
    }
}
