/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月18日 下午4:09:03 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月18日 下午4:09:03 
 * @version V1.0
 */
public class PayResult implements Serializable{
	private static final long serialVersionUID = 1L;
	String resultStatus;
	String result;
	public PayResult(String resultStatus,String result){
		this.result=result;
		this.resultStatus=resultStatus;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public String getResult() {
		return result;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
