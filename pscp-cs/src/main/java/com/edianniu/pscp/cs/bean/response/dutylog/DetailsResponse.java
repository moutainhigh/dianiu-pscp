package com.edianniu.pscp.cs.bean.response.dutylog;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.dutylog.vo.DutyLogVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房值班日志详情
 * @author zhoujianjian
 * @date 2017年10月30日 下午7:01:35
 */
@JSONMessage(messageCode = 2002142)
public class DetailsResponse extends BaseResponse{
	
	private DutyLogVO dutyLog;
	
	public DutyLogVO getDutyLog() {
		return dutyLog;
	}

	public void setDutyLog(DutyLogVO dutyLog) {
		this.dutyLog = dutyLog;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
