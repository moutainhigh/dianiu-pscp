package com.edianniu.pscp.sps.bean.user;

import java.io.Serializable;

public class RegisterReqData extends BaseTerminalData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String mobile;
	private String passwd;
	private String msgcodeid;
	private String msgcode;
	
	public RegisterReqData(String mobile, String password, String msgCodeId,
            String msgCode){
		this.mobile=mobile;
		this.passwd=password;
		this.msgcodeid=msgCodeId;
		this.msgcode=msgCode;
		
	}
	public RegisterReqData(){
		
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
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
