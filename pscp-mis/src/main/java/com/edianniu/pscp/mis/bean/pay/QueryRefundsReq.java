/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月7日 上午10:34:06 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月7日 上午10:34:06 
 * @version V1.0
 */
public class QueryRefundsReq implements Serializable{
	private static final long serialVersionUID = 1L;
	private int limit;
	private int status;
	public int getLimit() {
		return limit;
	}
	public int getStatus() {
		return status;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
