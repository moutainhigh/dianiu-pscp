/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月17日 下午5:25:59 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月17日 下午5:25:59 
 * @version V1.0
 */
public class WalletPayInfo implements Serializable{
	private static final long serialVersionUID = 1L;
    private String orderId;
    private String amount;
	public String getOrderId() {
		return orderId;
	}
	public String getAmount() {
		return amount;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
