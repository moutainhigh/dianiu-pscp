package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;
import java.util.Map;

public class UnionpayNotifyReq implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UnionpayPrepayInfo unionpayPrepayInfo;
	
	private Map<String,String>map;
	
	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public UnionpayPrepayInfo getUnionpayPrepayInfo() {
		return unionpayPrepayInfo;
	}

	public void setUnionpayPrepayInfo(UnionpayPrepayInfo unionpayPrepayInfo) {
		this.unionpayPrepayInfo = unionpayPrepayInfo;
	}
	
	

}
