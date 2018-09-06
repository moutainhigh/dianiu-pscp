package com.edianniu.pscp.cms.entity;

import java.io.Serializable;

/**
 * 企业用电其他配置
 * @author zhoujianjian
 * @date 2018年1月3日 下午8:11:17
 */
public class PowerOtherConfigEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	// 企业ID
	private Long companyId;
	// 类型  1:负荷   2:功率因数  3:电压
	private Integer type;
	// 级别
	private Integer key;
	// 级别名称
	private String keyName;
	// 取值
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
