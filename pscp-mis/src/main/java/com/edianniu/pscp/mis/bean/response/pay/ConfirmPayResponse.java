package com.edianniu.pscp.mis.bean.response.pay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
@JSONMessage(messageCode = 2002060)
public class ConfirmPayResponse  extends BaseResponse {
	/**
	 * SUCCESS:支付成功
	 * CONFIRM:支付确认中
	 * FAIL:支付失败 
	 * CANCLE:用户取消 
	 * REPEAT:重复支付(只有当resultCode==200时才会有)
	 */
	private String syncPayStatus;
	
	private String extendParams;
	
	public String getSyncPayStatus() {
		return syncPayStatus;
	}

	public String getExtendParams() {
		return extendParams;
	}

	public void setSyncPayStatus(String syncPayStatus) {
		this.syncPayStatus = syncPayStatus;
	}

	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
	
}
