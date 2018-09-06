/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.log;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.log.DeviceInfo;
import com.edianniu.pscp.mis.bean.request.NetDauRequest;

/**
 * netdau Online 上线
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 上午11:57:19
 * @version V1.0
 */
@JSONMessage(messageCode = 1003003)
public final class NetDauDeviceRequest extends NetDauRequest {
	private DeviceInfo device;

	public DeviceInfo getDevice() {
		return device;
	}

	public void setDevice(DeviceInfo device) {
		this.device = device;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
