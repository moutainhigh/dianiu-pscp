package com.edianniu.pscp.mis.bean.wallet;

/**
 * 提现打款状态
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月21日 下午12:23:34 
 * @version V1.0
 */
public enum WalleDetailPayStatus {

	SUCCESS(1, "打款成功"), FAIL(-1, "打款失败"), NORMAL(0, "未打款");
	private String desc;
	private int value;

	WalleDetailPayStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public int getValue() {
		return value;
	}

	public static boolean isContainsValue(Integer value) {
		boolean result = false;
		if (value == null) {
			return result;
		}
		for (WalleDetailPayStatus status : WalleDetailPayStatus.values()) {

			if (status.getValue() == value) {
				result = true;
				break;
			}
		}
		return result;

	}
}
