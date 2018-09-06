package com.edianniu.pscp.mis.bean.response.log;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.log.DeviceAckInfo;
import com.edianniu.pscp.mis.bean.response.NetDauResponse;

/**
 * netdau online response
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午12:04:43
 * @version V1.0
 */
@JSONMessage(messageCode = 2003003)
public final class NetDauDeviceResponse extends NetDauResponse {

	private DeviceAckInfo device;

	public DeviceAckInfo getDevice() {
		return device;
	}

	public void setDevice(DeviceAckInfo device) {
		this.device = device;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
