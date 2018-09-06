/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 下午3:30:46 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.electrician;

/**
 * 电工上下线状态
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 下午3:30:46 
 * @version V1.0
 */
public enum ElectricianWorkStatus {
	ON_LINE(1, "上班"), OFF_LINE(0, "下班");

	private int value;
	private String desc;

	ElectricianWorkStatus(int value, String desc) {
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
