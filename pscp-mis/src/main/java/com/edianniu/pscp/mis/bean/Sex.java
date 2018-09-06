/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月23日 下午7:39:47 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean;

/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月23日 下午7:39:47
 * @version V1.0
 */
public enum Sex {
	DEFALULT(0, "保密"), MAIL(1, ""), FEMAIL(2, "");
	private int value;
	private String desc;

	Sex(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
    public static boolean include(Integer value){
    	if(value==null){
    		return false;
    	}
    	for(Sex sex:Sex.values()){
    		if(sex.getValue()==value){
    			return true;
    		}
    	}
    	return false;
    }
	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
