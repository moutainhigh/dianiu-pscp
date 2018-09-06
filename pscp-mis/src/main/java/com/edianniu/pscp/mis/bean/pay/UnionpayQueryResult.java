package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;
import java.util.List;

import com.edianniu.pscp.mis.bean.Result;

public class UnionpayQueryResult extends Result implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<UnionpayQueryRespData>list;

	public List<UnionpayQueryRespData> getList() {
		return list;
	}

	public void setList(List<UnionpayQueryRespData> list) {
		this.list = list;
	}
	

}
