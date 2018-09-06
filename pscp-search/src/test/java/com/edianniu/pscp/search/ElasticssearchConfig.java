/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月8日 下午5:49:06 
 * @version V1.0
 */
package com.edianniu.pscp.search;

import java.net.InetAddress;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.search.config.ElasticsearchConfiguration;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月8日 下午5:49:06 
 * @version V1.0
 */
public class ElasticssearchConfig {
	private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfiguration.class);

    
    private String clusterNodes;
    private String clusterName;

    private TransportClient client;
    public TransportClient getClient(){
    	return this.client;
    }

    
    public TransportClient init() {
        logger.info("Building ElasticSearch client");
        Settings settings = Settings.builder().put("cluster.name", clusterName).build();
        client = new PreBuiltTransportClient(settings);
        try {
            if (!"".equals(clusterNodes)) {
                for (String nodes : clusterNodes.split(SEPARATOR)) {
                    String[] inetSocket = nodes.split(":");
                    String address = inetSocket[0];
                    Integer port = Integer.valueOf(inetSocket[1]);
                    client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(address), port));
                }
            }
        } catch (Exception e) {
            logger.error("Error build ElasticSearch client: {}", e.getMessage());
        }
        return client;
    }
    public void destroy() {
        if (client != null) {
            client.close();
        }
    }

    /**
     * 字符串分隔符
     */
    private static final String SEPARATOR = ",";
	public String getClusterNodes() {
		return clusterNodes;
	}


	public String getClusterName() {
		return clusterName;
	}


	public void setClusterNodes(String clusterNodes) {
		this.clusterNodes = clusterNodes;
	}


	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
}
