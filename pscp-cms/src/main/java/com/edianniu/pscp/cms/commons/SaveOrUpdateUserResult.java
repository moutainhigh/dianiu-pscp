package com.edianniu.pscp.cms.commons;

import com.edianniu.pscp.cms.entity.MemberEntity;

public class SaveOrUpdateUserResult extends AbstractResult implements Result {
     private MemberEntity user;

	public MemberEntity getUser() {
		return user;
	}

	public void setUser(MemberEntity user) {
		this.user = user;
	}
}
