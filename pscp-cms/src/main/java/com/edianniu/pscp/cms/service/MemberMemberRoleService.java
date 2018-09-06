package com.edianniu.pscp.cms.service;

import java.util.List;


/**
 * 用户与角色对应关系
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月5日 上午10:40:47 
 * @version V1.0
 */
public interface MemberMemberRoleService {
	
	void saveOrUpdate(Long userId, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
}
