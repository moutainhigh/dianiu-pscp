/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.user.invitation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 获取电工邀请信息request
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 上午11:57:19 
 * @version V1.0
 */
@JSONMessage(messageCode = 1002070)
public final class GetElectricianInvitationRequest extends TerminalRequest {
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
