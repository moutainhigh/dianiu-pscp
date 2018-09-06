package com.edianniu.pscp.message.web.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.access.MBeanConnectFailureException;
import org.springframework.util.CollectionUtils;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JmxConnector {
    private static final Logger logger = LoggerFactory.getLogger(JmxConnector.class);

    private MBeanServerConnection connection;
    private JMXServiceURL serviceUrl;
    private Map<String, Object> environment = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(ObjectName name, String attribute)
            throws MBeanException, AttributeNotFoundException,
            InstanceNotFoundException, ReflectionException,
            IOException {
        try {
            return (T) doInvoke(name, attribute);
        } catch (MBeanConnectFailureException | IOException ex) {
            logger.warn("Could not connect to JMX server - retrying", ex);
            connect();
            return (T) doInvoke(name, attribute);
        }
    }

    private Object doInvoke(ObjectName name, String attribute) throws MBeanException, AttributeNotFoundException,
            InstanceNotFoundException, ReflectionException,
            IOException {
        if (connection == null) {
            connect();
        }
        return connection.getAttribute(name, attribute);
    }

    private void connect() throws IOException {
        JMXConnector connector = JMXConnectorFactory.connect(this.serviceUrl, this.environment);
        this.connection = connector.getMBeanServerConnection();
    }

    public void setServiceUrl(String url) throws MalformedURLException {
        this.serviceUrl = new JMXServiceURL(url);
    }

    public void setEnvironment(Properties environment) {
        CollectionUtils.mergePropertiesIntoMap(environment, this.environment);
    }
}
