package com.edianniu.pscp.portal.entity;

import java.io.Serializable;
/**
 * 企业楼宇
 * @author zhoujianjian
 * @date 2017年12月18日 下午7:37:00
 */
public class CompanyBuildingEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private Long companyId;

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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

}
