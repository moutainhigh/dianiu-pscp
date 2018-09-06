/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.socialworkorder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 社会电工接单接口
 * @author cyl
 *
 */
@JSONMessage(messageCode = 1002016)
public final class TakeRequest extends TerminalRequest {
	private String orderId;
	

	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
