package com.edianniu.pscp.sps.bean.workorder.worklog;

import com.edianniu.pscp.sps.bean.Result;

/**
 * ClassName: ListResult
 * Author: tandingbo
 * CreateTime: 2017-05-12 14:44
 */
public class DetailResult extends Result {
    private static final long serialVersionUID = 1L;

    private ElectricianWorkLogInfo workLogInfo;

	public ElectricianWorkLogInfo getWorkLogInfo() {
		return workLogInfo;
	}

	public void setWorkLogInfo(ElectricianWorkLogInfo workLogInfo) {
		this.workLogInfo = workLogInfo;
	}

    
}
