package com.edianniu.pscp.cms.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.entity.SysUserEntity;



/**
 * 系统用户
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月16日 下午4:22:43 
 * @version V1.0
 */
public interface SysUserService {
	/**
	 * 获取用户信息
	 * @param userId
	 * @return
	 */
	public SysUserEntity getByUserId(Long userId);
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
	 * 根据登录名，查询系统用户
	 */
	SysUserEntity queryByLoginName(String loginName);
	/**
	 * 根据手机号码，查询系统用户
	 * @param mobile
	 * @return
	 */
	SysUserEntity queryByMobile(String mobile);
	/**
	 * 是否存在登录名
	 * @param loginName
	 * @return
	 */
	boolean isExistLoginName(String loginName);
	/**
	 * 是否存在手机号码
	 * @param mobile
	 * @return
	 */
	boolean isExistMobile(String mobile);
	/**
	 * 根据用户ID，查询用户
	 * @param userId
	 * @return
	 */
	SysUserEntity queryObject(Long userId);
	
	/**
	 * 查询用户列表
	 */
	List<SysUserEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存用户
	 */
	void save(SysUserEntity user);
	
	/**
	 * 修改用户
	 */
	void update(SysUserEntity user);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);
	
	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param passwd     原密码
	 * @param newPasswd  新密码
	 */
	int updatePasswd(Long userId, String passwd, String newPasswd);
}
