/**
 * 
 */
package com.edianniu.pscp.mis.bean;

/**
 * @author cyl
 *
 */
public enum WalleFundType {
	WALLE_BALANCE(0, "余额"), WALLE_FROZEN(98, "冻结金额"),ALI_PAY(1,"支付宝"),WEIXIN_PAY(2,"微信支付"),UNIONPAY(3,"银联支付"),PLATFORM(99,"消费扣款");
	private int value;
	private String desc;

	WalleFundType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static WalleFundType parse(Integer value){
		if(value==null){
			return WALLE_BALANCE;
		}
		if(value.intValue()==1){
			return WALLE_BALANCE;
		}
		if(value.intValue()==2){
			return  ALI_PAY;
		}
		if(value.intValue()==3){
			return WEIXIN_PAY;
		}
		if(value.intValue()==4){
			return WALLE_FROZEN;
		}
		if(value.intValue()==99){
			return PLATFORM;
		}
		
		return null;
		
	}
}
