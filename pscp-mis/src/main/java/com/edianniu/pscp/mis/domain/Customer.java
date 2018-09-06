package com.edianniu.pscp.mis.domain;

import java.io.Serializable;

public class Customer extends BaseDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long memberId;
	private String mobile;
	private String cpName;
	private Long companyId;
	private String cpContact;
	private String cpContactMobile;
	
	private String cpAddress;
	private String coPhone;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCpContact() {
		return cpContact;
	}
	public void setCpContact(String cpContact) {
		this.cpContact = cpContact;
	}
	public String getCpContactMobile() {
		return cpContactMobile;
	}
	public void setCpContactMobile(String cpContactMobile) {
		this.cpContactMobile = cpContactMobile;
	}
	public String getCpAddress() {
		return cpAddress;
	}
	public void setCpAddress(String cpAddress) {
		this.cpAddress = cpAddress;
	}
	public String getCoPhone() {
		return coPhone;
	}
	public void setCoPhone(String coPhone) {
		this.coPhone = coPhone;
	}
	
	

}
