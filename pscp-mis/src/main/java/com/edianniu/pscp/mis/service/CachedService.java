package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.user.BaseTerminalData;
import com.edianniu.pscp.mis.bean.user.LoginInfo;


public interface CachedService {
	public boolean isLogin(Long uid, String token);
	
	public boolean isLogin(Long uid);

	public LoginInfo setLoginInfo(Long uid,BaseTerminalData baseTerminalData);
	
	public LoginInfo getLoginInfo(Long uid);
	
	public LoginInfo getLoginInfo(Long uid,String token);

	public boolean clearLoginInfo(Long uid, String token);
	
	public void clearLoginInfo(Long uid);

	/**
	 * 检查验证码是否正确，如果正确的话，直接将验证码从缓存中清除。
	 * @param msgCodeId
	 * @param msgCode
	 * @return
	 */
	public boolean checkMsgCode(String msgCodeId, String msgCode);

	public boolean setMsgCode(String msgCodeId,String msgCode);
	
	public void set(String key,String value,int exp);
	
	public boolean isExist(String key);
	
	
}
