/**
 * 
 */
package com.edianniu.pscp.mis.bean;

/**
 * 1=收入 / 2=支出
 * @author cyl
 *
 */
public enum WalletDealType {
	INTER(0, "内部流动"),INCOME(1, "收入"), COSTS(2, "支出");
	private int value;
	private String desc;

	WalletDealType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static WalletDealType parse(Integer value){
		if(value==null){
			return COSTS;
		}
		for(WalletDealType walleDealType:WalletDealType.values()){
			if(walleDealType.getValue()==walleDealType.value){
				
			}
		}
		return COSTS;
		
	}

}
