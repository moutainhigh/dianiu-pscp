/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

/**
 * netdau 透传命令
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 上午11:57:19
 * @version V1.0
 */
@JSONMessage(messageCode = 1003006)
public final class NetDauControlRequest extends TerminalRequest {
	private String cmd="";//合闸closing, 跳闸tripping
	private String meterId;
	private Long renterId;
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public Long getRenterId() {
		return renterId;
	}
	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
