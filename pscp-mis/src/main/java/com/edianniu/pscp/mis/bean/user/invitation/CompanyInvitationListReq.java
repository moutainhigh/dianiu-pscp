/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:00:40 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.user.invitation;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:00:40 
 * @version V1.0
 */
public class CompanyInvitationListReq implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long companyId;
	private String mobile;//Y	手机号码
	private String companyName;//N	企业名字
	private int offset;
	private int pageSize;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getMobile() {
		return mobile;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getOffset() {
		return offset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	
}
