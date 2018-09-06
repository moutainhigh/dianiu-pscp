package com.edianniu.pscp.sps.bean.request.workOrder;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 工作记录详情
 * @author zhoujianjian
 * @date 2018年2月7日 上午11:00:16
 */
@JSONMessage(messageCode = 1002114)
public final class WorkLogDetailRequest extends TerminalRequest {

    private Long id;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
