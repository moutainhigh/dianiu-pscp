/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.socialworkorder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 社会电工上下线接口
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月21日 上午11:12:22
 * @version V1.0
 */
@JSONMessage(messageCode = 1002044)
public final class OnOffRequest extends TerminalRequest {
	private String status;// 上线:on下线:off

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
