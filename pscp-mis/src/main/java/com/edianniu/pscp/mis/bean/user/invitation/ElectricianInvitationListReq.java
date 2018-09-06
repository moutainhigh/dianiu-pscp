/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:02:43 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.user.invitation;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:02:43 
 * @version V1.0
 */
public class ElectricianInvitationListReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long companyId;
	private String mobile;//Y手机号码
	private String userName;//N电工姓名
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
	public String getUserName() {
		return userName;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
