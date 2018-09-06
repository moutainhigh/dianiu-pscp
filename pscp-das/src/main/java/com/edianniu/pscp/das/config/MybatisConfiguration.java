package com.edianniu.pscp.das.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: MyIbatisConfiguration
 * Author: yanlin.chen
 * CreateTime: 2018-01-29 17:53
 */
//@Configuration
//@ImportResource({"classpath:myibatis/myibatis-db.xml"})
public class MybatisConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        logger.info("myibatis db service running...");
    }
}
