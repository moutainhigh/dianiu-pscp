package com.edianniu.pscp.portal.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.portal.entity.SysRoleEntity;


/**
 * 角色
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 下午17:22:52
 */
public interface SysRoleService {
	
	SysRoleEntity queryObject(Long roleId);
	
	List<SysRoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRoleEntity role);
	
	void update(SysRoleEntity role);
	
	boolean isExist(Long id,Long companyId);
	
	void deleteBatch(Long[] roleIds);
}
