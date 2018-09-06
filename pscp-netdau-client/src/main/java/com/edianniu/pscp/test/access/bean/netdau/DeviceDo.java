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
	private String server;//
	private String port;//
	private String host;//
	private String enable1;
	private String com;
	private String enable2;//
	private String server3;//
	private String port3;//
	private String enable3;//
	private String server4;//
	private String port4;//
	private String enable4;//
	private String server5;//
	private String port5;//
	private String enable5;//
	private String dev_item;//
	private String dev_num;
	private String period;
	private String dns;//
	private String sync;//
	private String dhcp;//
	private String compress;//
	private String mstatus;//
	private String product;//
	private String product_sub;//
	private String revision;//
	private String iccid;//
	private String network;//
	private String signal;//
	private String apn;//
	private String operator;//
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEnable1() {
		return enable1;
	}
	public String getEnable2() {
		return enable2;
	}
	public String getServer3() {
		return server3;
	}
	public String getPort3() {
		return port3;
	}
	public String getEnable3() {
		return enable3;
	}
	public String getServer4() {
		return server4;
	}
	public String getPort4() {
		return port4;
	}
	public String getEnable4() {
		return enable4;
	}
	public String getServer5() {
		return server5;
	}
	public String getPort5() {
		return port5;
	}
	public String getEnable5() {
		return enable5;
	}
	public String getDev_item() {
		return dev_item;
	}
	public String getDns() {
		return dns;
	}
	public String getSync() {
		return sync;
	}
	public String getDhcp() {
		return dhcp;
	}
	public String getCompress() {
		return compress;
	}
	public String getMstatus() {
		return mstatus;
	}
	public String getProduct() {
		return product;
	}
	public String getProduct_sub() {
		return product_sub;
	}
	public String getRevision() {
		return revision;
	}
	public String getIccid() {
		return iccid;
	}
	public String getNetwork() {
		return network;
	}
	public String getSignal() {
		return signal;
	}
	public String getApn() {
		return apn;
	}
	public String getOperator() {
		return operator;
	}
	public void setEnable1(String enable1) {
		this.enable1 = enable1;
	}
	public void setEnable2(String enable2) {
		this.enable2 = enable2;
	}
	public void setServer3(String server3) {
		this.server3 = server3;
	}
	public void setPort3(String port3) {
		this.port3 = port3;
	}
	public void setEnable3(String enable3) {
		this.enable3 = enable3;
	}
	public void setServer4(String server4) {
		this.server4 = server4;
	}
	public void setPort4(String port4) {
		this.port4 = port4;
	}
	public void setEnable4(String enable4) {
		this.enable4 = enable4;
	}
	public void setServer5(String server5) {
		this.server5 = server5;
	}
	public void setPort5(String port5) {
		this.port5 = port5;
	}
	public void setEnable5(String enable5) {
		this.enable5 = enable5;
	}
	public void setDev_item(String dev_item) {
		this.dev_item = dev_item;
	}
	public void setDns(String dns) {
		this.dns = dns;
	}
	public void setSync(String sync) {
		this.sync = sync;
	}
	public void setDhcp(String dhcp) {
		this.dhcp = dhcp;
	}
	public void setCompress(String compress) {
		this.compress = compress;
	}
	public void setMstatus(String mstatus) {
		this.mstatus = mstatus;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public void setProduct_sub(String product_sub) {
		this.product_sub = product_sub;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public void setSignal(String signal) {
		this.signal = signal;
	}
	public void setApn(String apn) {
		this.apn = apn;
	}
	public void setOperator(String operator) {
		this.operator = operator;
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
