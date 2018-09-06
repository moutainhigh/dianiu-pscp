package com.edianniu.pscp.portal.dao;

import java.util.List;

import com.edianniu.pscp.portal.entity.SysMemberRoleEntity;

/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:46
 */
public interface SysMemberRoleDao extends BaseDao<SysMemberRoleEntity> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
}
