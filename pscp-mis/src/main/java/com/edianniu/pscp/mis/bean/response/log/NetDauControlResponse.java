package com.edianniu.pscp.mis.bean.response.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.log.ControlResult;
import com.edianniu.pscp.mis.bean.response.BaseResponse;

/**
 * netdau 透传命令相应
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午12:04:43
 * @version V1.0
 */
@JSONMessage(messageCode = 2003006)
public final class NetDauControlResponse extends BaseResponse {
	List<ControlResult> controls=new ArrayList<>();
	public List<ControlResult> getControls() {
		return controls;
	}
	public void setControls(List<ControlResult> controls) {
		this.controls = controls;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
