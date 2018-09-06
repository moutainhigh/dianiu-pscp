package com.edianniu.pscp.message.meter.domain;

import java.util.Date;

import com.edianniu.pscp.message.commons.BaseDo;
/**
 * 网关信息
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月17日 下午7:18:03 
 * @version V1.0
 */
public class GateWay extends BaseDo{
	private static final long serialVersionUID = 1L;
	private Long id;//" int8 NOT NULL,
	private String buildingId;//" varchar(32) COLLATE "default" NOT NULL,
	private String gatewayId;//" varchar(32) COLLATE "default" NOT NULL,
	private String buildingName;//" varchar(32) COLLATE "default" NOT NULL,
	private String buildNo;//" varchar(32) COLLATE "default" NOT NULL,
	private String devNo;//" varchar(32) COLLATE "default" NOT NULL,
	private String factory;//" varchar(32) COLLATE "default",
	private String hardware;//" varchar(32) COLLATE "default",
	private String software;//" varchar(32) COLLATE "default",
	private String mac;//" varchar(32) COLLATE "default",
	private String ip;//" varchar(15) COLLATE "default",
	private String mask;//" varchar(15) COLLATE "default",
	private String gate;//" varchar(15) COLLATE "default",
	private String server;//" varchar(15) COLLATE "default",
	private Integer port;//" int4,
	private String host;//" varchar(15) COLLATE "default",
	private String com;//" varchar(15) COLLATE "default",
	private Integer devNum;//" int4,
	private Integer period;//" int4,
	private Date beginTime;//" timestamp(6),
	private String address;//" varchar(32) COLLATE "default",
	private Integer status;//" int2,
	private Date reportTime;//" timestamp(6) NOT NULL,
	public Long getId() {
		return id;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public String getGatewayId() {
		return gatewayId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public String getBuildNo() {
		return buildNo;
	}
	public String getDevNo() {
		return devNo;
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
	public Integer getPort() {
		return port;
	}
	public String getHost() {
		return host;
	}
	public String getCom() {
		return com;
	}
	public Integer getDevNum() {
		return devNum;
	}
	public Integer getPeriod() {
		return period;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public String getAddress() {
		return address;
	}
	public Integer getStatus() {
		return status;
	}
	public Date getReportTime() {
		return reportTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public void setBuildNo(String buildNo) {
		this.buildNo = buildNo;
	}
	public void setDevNo(String devNo) {
		this.devNo = devNo;
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
	public void setPort(Integer port) {
		this.port = port;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public void setDevNum(Integer devNum) {
		this.devNum = devNum;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
}
