package com.edianniu.pscp.cs.bean.needs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.Result;

/**
 * 上传合作附件
 * @author zhoujianjian
 * 2017年9月18日下午2:52:47
 */
public class UploadFileResult extends Result{
private static final long serialVersionUID = 1L;
	
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
