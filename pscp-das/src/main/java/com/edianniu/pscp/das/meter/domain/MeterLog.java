package com.edianniu.pscp.das.meter.domain;

import java.util.Date;

import com.edianniu.pscp.das.common.BaseDo;

public class MeterLog extends BaseDo{
	private static final long serialVersionUID = 1L;
	private Long id;//" int8 NOT NULL,
	private String meterId;//" varchar(64) COLLATE "default" NOT NULL,
	private Integer type;//" int2 NOT NULL,
	private String subTermCode;//分项编码
	private String data;//" text COLLATE "default",
	private Date reportTime;//" timestamp(6) NOT NULL,
	public Long getId() {
		return id;
	}
	public String getMeterId() {
		return meterId;
	}
	public Integer getType() {
		return type;
	}
	public String getData() {
		return data;
	}
	public Date getReportTime() {
		return reportTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setData(String data) {
		this.data = data;
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
