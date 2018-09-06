package com.edianniu.pscp.sps.bean.response.workOrder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import com.edianniu.pscp.sps.bean.workorder.worklog.ElectricianWorkLogInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 工作记录详情
 * @author zhoujianjian
 * @date 2018年2月7日 上午11:01:52
 */
@JSONMessage(messageCode = 2002114)
public final class WorkLogDetailResponse extends BaseResponse {
    
    private ElectricianWorkLogInfo workLogInfo;

    public ElectricianWorkLogInfo getWorkLogInfo() {
		return workLogInfo;
	}

	public void setWorkLogInfo(ElectricianWorkLogInfo workLogInfo) {
		this.workLogInfo = workLogInfo;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
