package com.edianniu.pscp.renter.mis.bean.response.renter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.renter.mis.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 租客开合闸
 * @author zhoujianjian
 * @date 2018年4月4日 上午9:17:27
 */
@JSONMessage(messageCode = 2002306)
public class SwitchResponse extends BaseResponse{
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
