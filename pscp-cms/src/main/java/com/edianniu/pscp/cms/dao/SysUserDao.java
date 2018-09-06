package com.edianniu.pscp.cms.dao;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.entity.SysUserEntity;

/**
 * 系统用户
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月16日 下午4:32:42 
 * @version V1.0
 */
public interface SysUserDao extends BaseDao<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);
	
	/**
	 * 修改密码
	 */
	int updatePasswd(Map<String, Object> map);
	
	/**
	 * 根据登录名，查询系统用户
	 */
	SysUserEntity queryByLoginName(String loginName);
	/**
	 * 根据手机号，查询系统用户
	 */
	SysUserEntity queryByMobile(String mobile);
	/**
	 * 根据手机号获取用户数量
	 * @param mobile
	 * @return
	 */
	int getCountByMobile(String mobile);
	/**
	 * 根据登录名获取用户数量
	 * @param loginName
	 * @return
	 */
	int getCountByLoginName(String loginName);
}
