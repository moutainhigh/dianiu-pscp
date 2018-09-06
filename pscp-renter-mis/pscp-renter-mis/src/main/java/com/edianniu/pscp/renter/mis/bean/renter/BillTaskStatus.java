package com.edianniu.pscp.renter.mis.bean.renter;

/**
 * 0:未执行（默认） 1:执行中
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月18日 下午5:21:22
 * @version V1.0
 */
public enum BillTaskStatus {

	UNEXECUTED(0, "未执行"), EXECUTING(1, "执行中");

	private Integer value;
	private String desc;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private BillTaskStatus(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static Boolean isValueExist(Integer value) {
		if (null == value) {
			return false;
		}
		BillTaskStatus[] billTaskStatuss = BillTaskStatus.values();
		for (BillTaskStatus billTaskStatus : billTaskStatuss) {
			if (value.equals(billTaskStatus.getValue())) {
				return true;
			}
		}
		return false;
	}

}
