package com.edianniu.pscp.cs.bean.response.needs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 上传合作编辑附件
 * @author zhoujianjian
 * 2017年9月18日下午2:50:49
 */
@JSONMessage(messageCode = 2002129)
public class UploadFileResponse extends BaseResponse{
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
