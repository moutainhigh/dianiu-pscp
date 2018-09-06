/**
 * 
 */
package com.edianniu.pscp.mis.bean.pay;

/**
 * @author cyl
 *
 */
public enum MemberRefundStatus {
	PROCESSING(0, "处理中"),  SUCCESS(1, "成功"), FAIL(-1, "失败");

	private int value;
	private String desc;

	MemberRefundStatus(int value, String desc) {
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
