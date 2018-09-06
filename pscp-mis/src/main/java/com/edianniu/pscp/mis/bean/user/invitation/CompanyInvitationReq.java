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
public class CompanyInvitationReq implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long uid;
	private String mobile;//Y	手机号码
	private String companyName;//N	企业名字

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

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
}
