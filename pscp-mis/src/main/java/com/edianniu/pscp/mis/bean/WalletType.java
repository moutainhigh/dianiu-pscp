/**
 * 
 */
package com.edianniu.pscp.mis.bean;

/**
 * 账单类型
 *  1:充值
 *  2:冻结
 *  3:提现
 *  4:解冻
 *  5:扣款 
 *  6:收入
 *  7:电费
 * @author cyl
 *
 */
public enum WalletType {
	RECHARGE(1, "充值"), FROZEN(2, "冻结"),WITHDRAW_CASH(3,"提现"),UNFROZEN(4,"解冻"),WITHHOLD(5,"扣款"),INCOME(6,"收入"),ELECTRIC_CHARGE(7,"电费");
	private int value;
	private String desc;

	WalletType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static WalletType parse(Integer value){
		if(value==null){
			return RECHARGE;
		}
		for(WalletType walletType:WalletType.values()){
			if(walletType.getValue()==value){
				return walletType;
			}
		}
		return RECHARGE;
		
	}

}
