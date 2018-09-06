package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;

/**
 * 门户租客管理--租客保存
 * @author zhoujianjian
 * @date 2018年4月4日 下午3:55:06
 */
public class SaveReq implements Serializable{

	private static final long serialVersionUID = 1L;
	// 客户uid
	private Long uid;
	
	// 租客ID 新增可为空，编辑时不为空
	private Long id;
	
	private String name;
	
	private String mobile;
	
	private String contract;
	
	private String address;
	
	// 经MD5加密后的密码
	private String pwd;
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
