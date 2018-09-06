/**
 * 
 */
package com.edianniu.pscp.mis.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * netdau 透传命令
 * 
 * @author yanlin.chen
 * @version V1.0
 */
@JSONMessage(messageCode = 1003006)
public final class NetDauControlRequest extends TerminalRequest {
	private Long renterId;//租客ID
	private String cmd="";//合闸closing, 跳闸tripping
	List<String> meterIds=new ArrayList<String>();
	public Long getRenterId() {
		return renterId;
	}
	public String getCmd() {
		return cmd;
	}
	public List<String> getMeterIds() {
		return meterIds;
	}
	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public void setMeterIds(List<String> meterIds) {
		this.meterIds = meterIds;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
