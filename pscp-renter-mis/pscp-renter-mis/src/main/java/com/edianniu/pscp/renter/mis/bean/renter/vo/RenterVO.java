package com.edianniu.pscp.renter.mis.bean.renter.vo;

import java.io.Serializable;

public class RenterVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long renterId;
	
	private Long memberId;
	
	private String name;
	
	private String mobile;
	
	private String contract;
	
	private String address;
	
	private Long companyId;
	
	private String companyName;
	
	private String createTime;
	
	// 未缴单数（预交费类型租客此处为空）
	private Integer nopayCount;
	
	// 未缴费用（预交费类型租客此处为空）
	private String nopayCharge;
	
	// 闸门状态（ 0：跳闸  1：合闸     2：跳闸中   3： 合闸中）
	private Integer switchStatus;
	
	// 余额
	private String balance;
	
	// 监测点个数 
	private Integer pointCount = 0;
	
	// 配置ID
	private Long configId;

	public Long getRenterId() {
		return renterId;
	}

	public Integer getPointCount() {
		return pointCount;
	}

	public void setPointCount(Integer pointCount) {
		this.pointCount = pointCount;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getNopayCount() {
		return nopayCount;
	}

	public void setNopayCount(Integer nopayCount) {
		this.nopayCount = nopayCount;
	}

	public String getNopayCharge() {
		return nopayCharge;
	}

	public void setNopayCharge(String nopayCharge) {
		this.nopayCharge = nopayCharge;
	}

	public Integer getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(Integer switchStatus) {
		this.switchStatus = switchStatus;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	
}
