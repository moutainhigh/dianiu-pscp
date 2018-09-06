package com.edianniu.pscp.cs.bean.response.operationrecord;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房设备删除
 * @author zhoujianjian
 * 2017年9月29日下午10:34:20
 */
@JSONMessage(messageCode = 2002152)
public class DeleteResponse extends BaseResponse{
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
