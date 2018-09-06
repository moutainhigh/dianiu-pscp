package com.edianniu.pscp.das.meter.domain;

import com.edianniu.pscp.das.common.BaseDo;


public class CompanyMeter extends BaseDo {
	private static final long serialVersionUID = 1L;
    private Long id;
    private Long companyId;
    private String companyName;
    private String meterId;
    private int multiple;//倍率
    private int bindType;//绑定类型 0主线，1楼宇，2设备
    private int referRoom;//是否绑定配电房0 否，1是
    private String subTermcode;//分项编码
    private Integer status;//0下线，1上线
    private Double uHigh=400D;//电压上限
    private Double uLower=180D;//电压下限
    private String normalFactor="0.8-0.9";//正常功率因数的范围
    private String economicLoad="5-12";//经济负荷的范围
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
	public int getReferRoom() {
		return referRoom;
	}
	public void setReferRoom(int referRoom) {
		this.referRoom = referRoom;
	}
	public String getNormalFactor() {
		return normalFactor;
	}
	public void setNormalFactor(String normalFactor) {
		this.normalFactor = normalFactor;
	}
	public String getEconomicLoad() {
		return economicLoad;
	}
	public void setEconomicLoad(String economicLoad) {
		this.economicLoad = economicLoad;
	}
}
