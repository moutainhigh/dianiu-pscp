package com.edianniu.pscp.cms.dao;

import java.util.List;

import com.edianniu.pscp.cms.entity.MemberMemberRoleEntity;

/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:46
 */
public interface MemberMemberRoleDao extends BaseDao<MemberMemberRoleEntity> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
}
