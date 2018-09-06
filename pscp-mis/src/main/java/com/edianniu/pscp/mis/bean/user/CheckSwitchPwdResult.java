package com.edianniu.pscp.mis.bean.user;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.Result;

public class CheckSwitchPwdResult extends Result implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer isExist = 0;

	public Integer getIsExist() {
		return isExist;
	}

	public void setIsExist(Integer isExist) {
		this.isExist = isExist;
	}

	
	
}
