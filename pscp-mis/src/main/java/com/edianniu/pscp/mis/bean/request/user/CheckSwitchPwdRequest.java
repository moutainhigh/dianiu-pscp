/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 驗證开关闸操作密码是否存在
 * @author zhoujianjian
 * @date 2018年4月12日 下午4:54:37
 */
@JSONMessage(messageCode = 1002200)
public final class CheckSwitchPwdRequest extends TerminalRequest {
	private Long uid;
	private String token;

	public Long getUid() {
		return this.uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
