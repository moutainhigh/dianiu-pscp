package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * 注册接口
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午6:30:06 
 * @version V1.0
 */
public class NetDauDeviceRequest implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private CommonDo common;
	private DeviceDo device;
	public CommonDo getCommon() {
		return common;
	}
	
	public void setCommon(CommonDo common) {
		this.common = common;
	}

	public DeviceDo getDevice() {
		return device;
	}

	public void setDevice(DeviceDo device) {
		this.device = device;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	
}
