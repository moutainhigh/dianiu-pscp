package com.edianniu.pscp.mis.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import stc.skymobi.cache.redis.JedisUtil;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.user.BaseTerminalData;
import com.edianniu.pscp.mis.bean.user.LoginInfo;
import com.edianniu.pscp.mis.commons.CacheKey;
import com.edianniu.pscp.mis.service.CachedService;
import com.edianniu.pscp.mis.util.BizUtils;

@Service
@Repository("cachedService")
public class DefaultCachedService implements CachedService {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultCachedService.class);
	@Autowired
	private JedisUtil jedisUtil;
	@Value(value = "${app.login.exptime:2592000}")
	private int loginExptime;

	public DefaultCachedService() {
		System.out.println("init..");
	}

	public boolean isLogin(Long uid, String token) {
		try {
			LoginInfo loginInfo = null;
			String s = this.jedisUtil.get(CacheKey.CACHE_KEY_USER_LOGININFO + uid);
			if (StringUtils.isNoneBlank(s)) {
				loginInfo = (LoginInfo) JSONObject.parseObject(s,
						LoginInfo.class);
			}
			if ((loginInfo != null) && (loginInfo.getToken().equals(token))) {
				return true;
			}
		} catch (Exception e) {
			logger.error("jedisUtil isLogin:{}", e);
		}
		return false;
	}
	@Override
	public boolean isLogin(Long uid) {
		String s = this.jedisUtil.get(CacheKey.CACHE_KEY_USER_LOGININFO + uid);
		if (StringUtils.isNotBlank(s)) {
			return true;
		}
		return false;
	}
	public boolean checkMsgCode(String msgCodeId, String msgCode) {
		try {
			String key="msg#code#" + msgCodeId;
			String value = this.jedisUtil.get("msg#code#" + msgCodeId);
			if ((StringUtils.isNoneBlank(new CharSequence[] { value }))
					&& (msgCode.trim().equals(value))) {
				this.jedisUtil.del(key);
				return true;
			}
		} catch (Exception e) {
			logger.error("jedisUtil checkMsgCode:{}", e);
		}
		return false;
	}

	public LoginInfo setLoginInfo(Long uid,BaseTerminalData baseTerminalData) {
		LoginInfo loginInfo = null;
		try {
			loginInfo = new LoginInfo();
			loginInfo.setLoginTime(new Date());
			loginInfo.setDid(baseTerminalData.getDid());
			loginInfo.setOsType(baseTerminalData.getOsType());
			loginInfo.setPbrand(baseTerminalData.getPbrand());
			loginInfo.setPtype(baseTerminalData.getPtype());
			loginInfo.setAppPkg(baseTerminalData.getAppPkg());
			loginInfo.setAppType(baseTerminalData.getAppType());
			String token = BizUtils.createToken();
			loginInfo.setToken(token);
			this.jedisUtil.setex(CacheKey.CACHE_KEY_USER_LOGININFO + uid, loginExptime,
					JSONObject.toJSONString(loginInfo));
		} catch (Exception e) {
			logger.error("jedisUtil setLoginInfo:{}", e);
			loginInfo = new LoginInfo();
			loginInfo.setLoginTime(new Date());
			String token = BizUtils.createToken();
			loginInfo.setToken(token);
			this.jedisUtil.setex(CacheKey.CACHE_KEY_USER_LOGININFO + uid, loginExptime,
					JSONObject.toJSONString(loginInfo));
		}
		return loginInfo;
	}
	@Override
	public LoginInfo getLoginInfo(Long uid) {
		LoginInfo loginInfo = null;
		try {
			String s = this.jedisUtil.get(CacheKey.CACHE_KEY_USER_LOGININFO + uid);
			if (StringUtils.isNoneBlank(s)) {
				loginInfo = (LoginInfo) JSONObject.parseObject(s,
						LoginInfo.class);
			}
		} catch (Exception e) {
			logger.error("jedisUtil isLogin:{}", e);
		}
		return loginInfo;
	}

	@Override
	public LoginInfo getLoginInfo(Long uid, String token) {
		LoginInfo loginInfo = null;
		try {
			String s = this.jedisUtil.get(CacheKey.CACHE_KEY_USER_LOGININFO + uid);
			if (StringUtils.isNoneBlank(s)) {
				loginInfo = (LoginInfo) JSONObject.parseObject(s,
						LoginInfo.class);
			}
			if(loginInfo!=null&&loginInfo.getToken().equals(token)){
				return loginInfo;
			}
		} catch (Exception e) {
			logger.error("jedisUtil isLogin:{}", e);
		}
		return loginInfo;
	}
	public boolean setMsgCode(String msgCodeId, String msgCode) {
		boolean result = false;
		try {

			this.jedisUtil.setex("msg#code#" + msgCodeId, 5 * 60, msgCode);
			result = true;
		} catch (Exception e) {
			logger.error("jedisUtil setMsgCode:{}", e);
		}
		return result;
	}

	public boolean clearLoginInfo(Long uid, String token) {
		boolean result = false;
		try {
			if ((this.jedisUtil.exists(CacheKey.CACHE_KEY_USER_LOGININFO + uid))
					&& (isLogin(uid, token))) {
				this.jedisUtil.del(new String[] { CacheKey.CACHE_KEY_USER_LOGININFO + uid });
				result = true;
			}
			
		} catch (Exception e) {
			logger.error("jedisUtil clear loginInfo:{}", e);
		}
		return result;
	}
	@Override
	public void clearLoginInfo(Long uid) {
		try {
			if (this.jedisUtil.exists(CacheKey.CACHE_KEY_USER_LOGININFO + uid)) {
				this.jedisUtil.del(new String[] { CacheKey.CACHE_KEY_USER_LOGININFO + uid });
			}
		} catch (Exception e) {
			logger.error("jedisUtil clear loginInfo:{}", e);
		}
	}

	@Override
	public void set(String key, String value, int exp) {
		try {
			this.jedisUtil.setex(key, exp, value);

		} catch (Exception e) {
			logger.error("jedisUtil set:{}", e);
		}

	}

	@Override
	public boolean isExist(String key) {
		boolean result = false;
		try {
			if (this.jedisUtil.exists(key)) {
				result = true;
			}
		} catch (Exception e) {
			logger.error("jedisUtil exists:{}", e);
		}
		return result;
	}

	

	

	
}
