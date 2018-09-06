package com.edianniu.pscp.mis.bean.wallet;

/**
 * 审核状态
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月21日 上午11:52:34
 * @version V1.0
 */
public enum WalletDetailCheckStatus {
	SUCCESS(1, "审核成功"), FAIL(-1, "审核失败"), NORMAL(0, "未审核");

	private String desc;
	private int value;

	WalletDetailCheckStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public int getValue() {
		return value;
	}

	public static boolean isContainValue(Integer value) {
		boolean result = false;
		if (value == null) {
			return result;
		}
		for (WalletDetailCheckStatus status : WalletDetailCheckStatus.values()) {
			if (status.getValue() == value) {
				result = true;
				break;
			}
		}
		return result;
	}

}
