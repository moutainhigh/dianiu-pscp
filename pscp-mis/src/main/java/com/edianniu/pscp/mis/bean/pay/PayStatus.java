/**
 * 
 */
package com.edianniu.pscp.mis.bean.pay;

/**
 * 支付状态
 * 0:未支付
 * 1:支付确认中(客户端)
 * 2:支付成功(服务端异步通知)
 * 3:支付失败
 * 4:用户取消

 * @author cyl
 *
 */
public enum PayStatus {
	UNPAY(0, "未支付"), INPROCESSING(1, "支付确认中"), SUCCESS(2, "支付成功"), FAIL(3, "支付失败"),CANCLE(4,"用户取消");

	private int value;
	private String desc;

	PayStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static boolean inclide(Integer value){
		if (null == value) {
			return false;
		}
		PayStatus[] values = PayStatus.values();
		for (PayStatus payStatus : values) {
			if (value.equals(payStatus.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	public static String tip(){
		StringBuffer sb = new StringBuffer("支付状态只能是：");
		PayStatus[] values = PayStatus.values();
		for (PayStatus payStatus : values) {
			sb.append(payStatus.getValue()).append("[").append(payStatus.getDesc()).append("],");
		}
		return sb.toString().substring(0, sb.length()-1);
	}
	
}
