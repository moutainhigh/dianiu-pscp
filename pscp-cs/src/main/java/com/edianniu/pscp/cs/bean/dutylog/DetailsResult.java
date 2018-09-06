package com.edianniu.pscp.cs.bean.dutylog;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.dutylog.vo.DutyLogVO;

/**
 * 配电房值班日志详情
 * @author zhoujianjian
 * @date 2017年10月30日 下午7:05:12
 */
public class DetailsResult extends Result {
	private static final long serialVersionUID = 1L;
	
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
