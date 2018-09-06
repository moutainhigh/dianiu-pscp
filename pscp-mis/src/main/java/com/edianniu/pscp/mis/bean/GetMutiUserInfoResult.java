package com.edianniu.pscp.mis.bean;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.user.MutiUserInfo;

public class GetMutiUserInfoResult extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private MutiUserInfo mutiUserInfo;
	public MutiUserInfo getMutiUserInfo() {
		return mutiUserInfo;
	}
	public void setMutiUserInfo(MutiUserInfo mutiUserInfo) {
		this.mutiUserInfo = mutiUserInfo;
	}
}
