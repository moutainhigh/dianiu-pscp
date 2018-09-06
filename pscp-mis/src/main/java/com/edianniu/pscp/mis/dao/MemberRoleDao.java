package com.edianniu.pscp.mis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.mis.domain.MemberRole;

public interface MemberRoleDao {
	public void save(MemberRole memberRole);
	public void deleteByUidAndRoleId(@Param("uid")Long uid,@Param("roleId")Long roleId);
	public void deleteByUid(Long uid);
	public List<String> getPerms(Long uid);
	
}
