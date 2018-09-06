package com.edianniu.pscp.cms.dao;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.entity.MemberEntity;

/**
 * 系统用户
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月19日 下午18:33:11
 */
public interface MemberDao extends BaseDao<MemberEntity> {
	
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
	 * 根据登录名，查询系统用户
	 */
	MemberEntity queryByLoginName(String loginName);
	/**
	 * 根据手机号，查询系统用户
	 */
	MemberEntity queryByMobile(String mobile);
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
	
	/**
	 * 修改密码
	 */
	int updatePassword(Map<String, Object> map);
	
	/**
	 * 查询公司管理员
	 */
	MemberEntity getCompanyAdmin(Long companyId);
}
