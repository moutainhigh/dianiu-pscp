package com.edianniu.pscp.portal.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.utils.ShiroUtils;

/**
 * Shiro权限标签(Velocity版)
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月20日 上午11:23:47
 */
public class VelocityShiro {

	/**
	 * 是否拥有该权限
	 * @param permission  权限标识
	 * @return   true：是     false：否
	 */
	public boolean hasPermission(String permission) {
		Subject subject = SecurityUtils.getSubject();
		return subject != null && subject.isPermitted(permission);
	}
	/**
	 * 获取登录用户信息
	 * @return
	 */
	public SysUserEntity getUser(){
		return ShiroUtils.getUserEntity();
	}
	/**
	 * 判断登录状态
	 * @return
	 */
	public boolean isLogin(){
		return ShiroUtils.isLogin();
	}
	

}
