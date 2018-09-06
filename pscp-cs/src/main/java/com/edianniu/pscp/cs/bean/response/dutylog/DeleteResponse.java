package com.edianniu.pscp.cs.bean.response.dutylog;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房值班日志删除
 * @author zhoujianjian
 * @date 2017年10月30日 下午7:34:37
 */
@JSONMessage(messageCode = 2002143)
public class DeleteResponse extends BaseResponse{
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
