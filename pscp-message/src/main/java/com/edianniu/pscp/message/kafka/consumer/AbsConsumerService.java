package com.edianniu.pscp.message.kafka.consumer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author cyl
 */
public abstract class AbsConsumerService {

    protected Properties getPorperties(String confFileName) throws FileNotFoundException, IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(confFileName));
        return props;
    }

    protected abstract void init();
}
