package com.edianniu.pscp.renter.mis.bean.request;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

public class NetworkTrack extends BaseRequest {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private String clientHost;
	private String accessHost;
	private int accessPort;
	private static String localHost;
	private int localPort;

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

	public String getClientHost() {
		if (StringUtils.isBlank(this.clientHost)) {
			return "localhost";
		}
		return this.clientHost;
	}

	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;
	}

	public String getAccessHost() {
		return this.accessHost;
	}

	public void setAccessHost(String accessHost) {
		this.accessHost = accessHost;
	}

	public int getAccessPort() {
		return this.accessPort;
	}

	public void setAccessPort(int accessPort) {
		this.accessPort = accessPort;
	}

	public String getLocalHost() {
		if (StringUtils.isBlank(localHost)) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				localHost = addr.getHostAddress();
			} catch (Exception e) {
				this.logger.error("getLocalHost is error :{}", e);
			}
		}
		return localHost;
	}

	public void setLocalHost(String localHost) {
		localHost = localHost;
	}

	public void setLocalPort(short localPort) {
		this.localPort = localPort;
	}

	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}

	public int getLocalPort() {
		return this.localPort;
	}
}
