package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.Result;

public class AliPayPrepayResult extends Result implements Serializable{
	private static final long serialVersionUID = 1L;
	private AlipayPrepayInfo alipayPrepayInfo;
	public AlipayPrepayInfo getAlipayPrepayInfo() {
		return alipayPrepayInfo;
	}
	public void setAlipayPrepayInfo(AlipayPrepayInfo alipayPrepayInfo) {
		this.alipayPrepayInfo = alipayPrepayInfo;
	}
	
	

}
