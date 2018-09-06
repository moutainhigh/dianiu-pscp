package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.Result;


public class PayNotifyResult extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean isReturn=false;//是否还车
	public boolean isReturn() {
		return isReturn;
	}
	public void setReturn(boolean isReturn) {
		this.isReturn = isReturn;
	}
	
	
}
