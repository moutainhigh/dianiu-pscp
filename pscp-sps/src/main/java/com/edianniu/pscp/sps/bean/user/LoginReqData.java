package com.edianniu.pscp.sps.bean.user;

import java.io.Serializable;

public class LoginReqData extends BaseTerminalData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String loginName;
	private String passwd;
	public String getLoginName() {
		return loginName;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
