package com.edianniu.pscp.cms.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.entity.MemberRoleEntity;


/**
 * 角色
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 下午17:22:52
 */
public interface MemberRoleService {
	
	MemberRoleEntity queryObject(Long roleId);
	
	List<MemberRoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MemberRoleEntity role);
	
	void update(MemberRoleEntity role);
	
	void deleteBatch(Long[] roleIds);
}
