package com.edianniu.pscp.portal.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.edianniu.pscp.portal.entity.SysUserEntity;

/**
 * Shiro工具类
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2016年11月12日 上午9:49:19
 */
public class ShiroUtils {

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static SysUserEntity getUserEntity() {
		return (SysUserEntity)SecurityUtils.getSubject().getPrincipal();
	}

	public static Long getUserId() {
		return getUserEntity().getUserId();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		try{
			return SecurityUtils.getSubject().getPrincipal() != null;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
		
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	public static String getKaptcha(String key) {
		Object object=getSessionAttribute(key);
		if(object==null){
			return "";
		}
		String kaptcha = object.toString();
		getSession().removeAttribute(key);
		return kaptcha;
	}

}
