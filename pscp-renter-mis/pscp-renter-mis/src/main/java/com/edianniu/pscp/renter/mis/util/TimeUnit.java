package com.edianniu.pscp.renter.mis.util;

/**
 * 时间类型
 * @author zhoujianjian
 * @date 2017年10月31日 下午2:11:29
 */
public enum TimeUnit {
	
	DAY(1,"天"),
	MONTH(2,"月"),
	YEAR(3,"年");
	
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

	private TimeUnit(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	/**
	 * 判断value是否存在
	 * @param value
	 * @return
	 */
	public static Boolean isExist(Integer value){
		TimeUnit[] values = TimeUnit.values();
		for (TimeUnit timeUnit : values) {
			if (value.equals(timeUnit.getValue())) {
				return true;
			}
		}
		return false;
	}
}
