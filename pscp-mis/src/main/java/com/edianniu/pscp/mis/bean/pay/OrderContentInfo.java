/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月2日 下午8:20:05 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月2日 下午8:20:05 
 * @version V1.0
 */
public class OrderContentInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String title;
    private String amount;
	public String getTitle() {
		return title;
	}
	public String getAmount() {
		return amount;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
