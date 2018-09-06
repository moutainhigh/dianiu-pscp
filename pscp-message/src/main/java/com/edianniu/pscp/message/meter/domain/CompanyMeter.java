package com.edianniu.pscp.message.meter.domain;

import com.edianniu.pscp.message.commons.BaseDo;

public class CompanyMeter extends BaseDo {
	private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Long companyId;
    private String companyName;
    private String meterId;
    private int multiple;//倍率
    private int bindType;//绑定类型 0主线，1楼宇，2设备
    private String subTermcode;//分项编码
    private Integer status;//0下线，1上线
    private Double uHigh=200D;//电压上限
    private Double uLower=100D;//电压下限
    public Integer getMeterType(){
    	if(bindType==0){
    		return 1;
    	}
    	else if(bindType==1){
    		return 2;
    	}
    	else if(bindType==2){
    		return 3;
    	}
    	return null;
    }
	public Long getCompanyId() {
		return companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getMeterId() {
		return meterId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public int getMultiple() {
		return multiple;
	}
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	public int getBindType() {
		return bindType;
	}
	public void setBindType(int bindType) {
		this.bindType = bindType;
	}
	public String getSubTermcode() {
		return subTermcode;
	}
	public void setSubTermcode(String subTermcode) {
		this.subTermcode = subTermcode;
	}
	public Double getuHigh() {
		return uHigh;
	}
	public Double getuLower() {
		return uLower;
	}
	public void setuHigh(Double uHigh) {
		this.uHigh = uHigh;
	}
	public void setuLower(Double uLower) {
		this.uLower = uLower;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
