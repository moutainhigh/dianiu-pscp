package com.edianniu.pscp.portal.commons;

import com.edianniu.pscp.portal.entity.SysUserEntity;

public class SaveOrUpdateUserResult extends AbstractResult implements Result {
     private SysUserEntity user;

	public SysUserEntity getUser() {
		return user;
	}

	public void setUser(SysUserEntity user) {
		this.user = user;
	}
}
