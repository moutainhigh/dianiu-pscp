package com.edianniu.pscp.mis.domain;


/**
 * 企业租客信息
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年3月29日 下午9:07:54 
 * @version V1.0
 */
public class CompanyRenter extends BaseDo {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 会员ID
	 */
	private Long memberId;

	/**
	 * 电工名字
	 */
	private String name;
	/**
	 * 状态 0禁用，1启用
	 */
	private Integer status;

	/**
	 * 企业ID
	 */
	private Long companyId;

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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
