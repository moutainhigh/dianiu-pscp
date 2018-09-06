package com.edianniu.pscp.mis.bean.response.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 驗證开关闸操作密码是否存在
 * @author zhoujianjian
 * @date 2018年4月12日 下午5:01:58
 */
@JSONMessage(messageCode = 2002200)
public final class CheckSwitchPwdResponse extends BaseResponse {
	
	private Integer isExist = 0;
	
	public Integer getIsExist() {
		return isExist;
	}

	public void setIsExist(Integer isExist) {
		this.isExist = isExist;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
