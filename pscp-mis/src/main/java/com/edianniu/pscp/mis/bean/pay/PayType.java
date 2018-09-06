/**
 * 
 */
package com.edianniu.pscp.mis.bean.pay;

/**
 * @author cyl
 *
 */
public enum PayType {
	WALLET(0, "余额"), ALIPAY(1, "支付宝"), WEIXIN(2, "微信支付"),UNIONPAY(3,"银联支付");
	private int value;
	private String desc;

	PayType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	public static boolean isUnipay(int type){
		PayType[] types=PayType.values();
		boolean flag=false;
		for(PayType pay:types){
			if(pay.getValue()==type){
				flag=true;
			}
		}
		return flag;
	}
	public static boolean isAplipay(int type){
		
		boolean flag=false;
		if(ALIPAY.getValue()==type){
			flag=true;
		}
		return flag;
	}
	public static boolean include(int value,int exclude){
    	if(value==exclude){
    		return false;
    	}
    	
    	for(PayType payType:PayType.values()){
    		if(payType.getValue()==value){
    			return true;
    		}
    	}
    	return false;
    }
	
	public static boolean include(int value){
    	
    	for(PayType payType:PayType.values()){
    		if(payType.getValue()==value){
    			return true;
    		}
    	}
    	return false;
    }
}
