package com.edianniu.pscp.cs.bean.response.firefightingequipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 消防设施删除
 * @author zhoujianjian
 * @date 2017年11月1日 下午10:15:19
 */
@JSONMessage(messageCode = 2002138)
public class DeleteResponse extends BaseResponse{
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
