package com.edianniu.pscp.mis.domain;

/**
 * 租客信息类
 * @author zhoujianjian
 * @date 2018年3月28日 下午6:40:14
 */
public class Renter extends BaseDo{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long memberId;
	
	private String name;
	
	private Long companyId;
	// 0:禁用(默认)   1:启用
	private Integer status = 0;
	
	private String contract;
	
	private String mobile;
	
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Renter(Long id, Integer status) {
		this.id = id;
		this.status = status;
	}
	public Renter() {
		super();
	}
	
	
	
	
	
	
}
