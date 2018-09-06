package com.edianniu.pscp.renter.mis.bean.request.renter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.renter.mis.bean.request.TerminalRequest;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 租客首页
 * @author zhoujianjian
 * @date 2018年4月4日 上午9:17:09
 */
@JSONMessage(messageCode = 1002303)
public class HomeRequest extends TerminalRequest{
	
	private Long renterId;
	public Long getRenterId() {
		return renterId;
	}
	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}

}
