package com.edianniu.pscp.cms.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.entity.SysRoleEntity;



/**
 * 角色
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月16日 下午4:22:02 
 * @version V1.0
 */
public interface SysRoleService {
	
	SysRoleEntity queryObject(Long roleId);
	
	List<SysRoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRoleEntity role);
	
	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);
}
