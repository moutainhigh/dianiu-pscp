package com.edianniu.pscp.message.bean;

/**
 * 网关状态
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月17日 下午5:10:24
 * @version V1.0
 */
public enum GateWayStatus {
	ONLINE(1, "上线"), OFFLINE(0, "下线");
	private int value;
	private String desc;

	GateWayStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
