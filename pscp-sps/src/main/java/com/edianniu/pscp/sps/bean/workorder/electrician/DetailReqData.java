/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午6:00:46 
 * @version V1.0
 */
package com.edianniu.pscp.sps.bean.workorder.electrician;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午6:00:46 
 * @version V1.0
 */
public class DetailReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String orderId;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
