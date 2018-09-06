package com.edianniu.pscp.mis.bean.response.user.invitation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;
/**
 * 同意/拒绝电工解绑接口response
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午12:04:43 
 * @version V1.0
 */
@JSONMessage(messageCode = 2002067)
public final class ConfirmElectricianUnBindResponse extends BaseResponse {
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
