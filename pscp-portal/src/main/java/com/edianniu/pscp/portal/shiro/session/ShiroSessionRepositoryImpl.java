package com.edianniu.pscp.portal.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import stc.skymobi.cache.redis.JedisUtil;


/**
 * @author zhoujianjian
 */
@Component
public class ShiroSessionRepositoryImpl implements ShiroSessionRepository {

	public Logger logger = Logger.getLogger(ShiroSessionRepositoryImpl.class);
	
	@Autowired
    protected JedisUtil jedisUtil;
	
	//前缀
    public final String REDIS_SHIRO_SESSION = "redis_shiro_session:";
    
    /**
     * 保存
     */
	public void saveSession(Session session) {
        if (session == null || session.getId() == null){
        	throw new NullPointerException("session or sessionID is empty");
        }
        try {
        	Serializable id = session.getId();
        	String key = buildRedisSessionKey(id);
        	//将时间单位毫秒转换成 秒 默认1800秒
        	int timeout = (int) (session.getTimeout()/1000);
        	jedisUtil.setObject(key, session,timeout);
        } catch (Exception e) {
        	logger.error("save session error");
        }
	}

	/**
	 * 删除
	 */
	public void deleteSession(Serializable id) {
		if (id == null) {
			logger.error("sessionId is empty");
	    }
        try {
        	jedisUtil.del(buildRedisSessionKey(id));
        } catch (Exception e) {
        	logger.error("delete session error");
        }
	}

	/**
	 * 查询一个
	 */
	public Session getSession(Serializable sessionId) {
		 if (sessionId == null){
			 throw new NullPointerException("session id is empty");
		 }	            
        Session session = null;
        try {
        	String key = buildRedisSessionKey(sessionId);
        	session= jedisUtil.getObject(key);
        } catch (Exception e) {
            logger.error("get session error");
        }
        return session;
	}
	
	/**
	 * 查询所有
	 */
	public Collection<Session> getAllSessions() {
		Set<Session> sessions = new HashSet<Session>();
		Set<String> keys = new HashSet<String>();
		try {
			keys =jedisUtil.keys(REDIS_SHIRO_SESSION+"*");
		    if (null != keys && keys.size() > 0) {
			    for (String key : keys) {
			    	Session s  = jedisUtil.getObject(key);
					sessions.add(s);
			    }
		    }
		} catch (Exception e) {
		    logger.error("get all session error");;
		}
	        return sessions;
	}
	
	/**
	 * 创建sessionId
	 */
    public String buildRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + sessionId;
    }

}
