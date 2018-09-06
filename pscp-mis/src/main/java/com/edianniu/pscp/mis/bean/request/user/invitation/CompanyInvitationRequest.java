/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.user.invitation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 企业邀请request
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 上午11:57:19 
 * @version V1.0
 */
@JSONMessage(messageCode = 1002068)
public final class CompanyInvitationRequest extends TerminalRequest {
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
