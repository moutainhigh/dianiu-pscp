/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月13日 下午5:44:20 
 * @version V1.0
 */
package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月13日 下午5:44:20 
 * @version V1.0
 */
public class DeviceDo implements Serializable{
	private static final long serialVersionUID = 1L;
	private DefaultAttrDo attr;
	private String build_name;
	private String build_no;
	private String dev_no;
	private String factory;
	private String hardware;
	private String software;
	private String mac;
	private String ip;
	private String mask;
	private String gate;
	private String server;
	private String port;
	private String host;
	private String com;
	private String dev_num;
	private String period;
	private String begin_time;
	private String address;
	
	public String getBuild_name() {
		return build_name;
	}
	public String getBuild_no() {
		return build_no;
	}
	public String getDev_no() {
		return dev_no;
	}
	public String getFactory() {
		return factory;
	}
	public String getHardware() {
		return hardware;
	}
	public String getSoftware() {
		return software;
	}
	public String getMac() {
		return mac;
	}
	public String getIp() {
		return ip;
	}
	public String getMask() {
		return mask;
	}
	public String getGate() {
		return gate;
	}
	public String getServer() {
		return server;
	}
	public String getPort() {
		return port;
	}
	public String getHost() {
		return host;
	}
	public String getCom() {
		return com;
	}
	public String getDev_num() {
		return dev_num;
	}
	public String getPeriod() {
		return period;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public String getAddress() {
		return address;
	}
	public void setBuild_name(String build_name) {
		this.build_name = build_name;
	}
	public void setBuild_no(String build_no) {
		this.build_no = build_no;
	}
	public void setDev_no(String dev_no) {
		this.dev_no = dev_no;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public void setHardware(String hardware) {
		this.hardware = hardware;
	}
	public void setSoftware(String software) {
		this.software = software;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public void setGate(String gate) {
		this.gate = gate;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public void setDev_num(String dev_num) {
		this.dev_num = dev_num;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public DefaultAttrDo getAttr() {
		return attr;
	}
	public void setAttr(DefaultAttrDo attr) {
		this.attr = attr;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
