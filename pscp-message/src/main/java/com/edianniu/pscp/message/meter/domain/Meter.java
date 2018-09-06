package com.edianniu.pscp.message.meter.domain;

import java.util.Date;

import com.edianniu.pscp.message.commons.BaseDo;
/**
 * 仪表信息
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月17日 下午7:17:52 
 * @version V1.0
 */
public class Meter extends BaseDo{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String buildingId;//" varchar(32) COLLATE "default" NOT NULL,
	private String gatewayId;//" varchar(32) COLLATE "default" NOT NULL,
	private String meterId;//" varchar(32) COLLATE "default" NOT NULL,
	private String subTermCode;//分项编码
	private Integer type;//" int2 NOT NULL,
	private String name;//" varchar(32) COLLATE "default",
	private String address;//" varchar(32) COLLATE "default",
	private Integer status;//" int2,
	private String functions;//" text COLLATE "default",
	private Date reportTime;//" timestamp(6) NOT NULL,
	public String getMeterNo(){
		return this.buildingId+this.gatewayId+this.meterId;
	}
	public Long getId() {
		return id;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public String getGatewayId() {
		return gatewayId;
	}
	public String getMeterId() {
		return meterId;
	}
	public Integer getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public Integer getStatus() {
		return status;
	}
	public String getFunctions() {
		return functions;
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
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setFunctions(String functions) {
		this.functions = functions;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public String getSubTermCode() {
		return subTermCode;
	}
	public void setSubTermCode(String subTermCode) {
		this.subTermCode = subTermCode;
	}
}
