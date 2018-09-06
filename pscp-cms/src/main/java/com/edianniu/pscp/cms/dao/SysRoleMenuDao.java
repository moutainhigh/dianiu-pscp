package com.edianniu.pscp.cms.dao;

import java.util.List;

import com.edianniu.pscp.cms.entity.SysRoleMenuEntity;

/**
 * 角色与菜单对应关系
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月16日 下午4:32:20 
 * @version V1.0
 */
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
}
