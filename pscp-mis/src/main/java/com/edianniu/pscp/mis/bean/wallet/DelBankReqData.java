package com.edianniu.pscp.mis.bean.wallet;

import java.io.Serializable;

public class DelBankReqData  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	private Long id;
	
	private String msgcodeid;
	
	private String msgcode;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsgcodeid() {
		return msgcodeid;
	}

	public void setMsgcodeid(String msgcodeid) {
		this.msgcodeid = msgcodeid;
	}

	public String getMsgcode() {
		return msgcode;
	}

	public void setMsgcode(String msgcode) {
		this.msgcode = msgcode;
	}
	
	

}
