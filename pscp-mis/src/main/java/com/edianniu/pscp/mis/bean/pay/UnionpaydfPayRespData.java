package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.Result;

public class UnionpaydfPayRespData extends Result implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8504529512032422413L;
	
	private UnionpayPrepayInfo unionpayPrepayInfo ;

	public UnionpayPrepayInfo getUnionpayPrepayInfo() {
		return unionpayPrepayInfo;
	}

	public void setUnionpayPrepayInfo(UnionpayPrepayInfo unionpayPrepayInfo) {
		this.unionpayPrepayInfo = unionpayPrepayInfo;
	}
	
	

}
