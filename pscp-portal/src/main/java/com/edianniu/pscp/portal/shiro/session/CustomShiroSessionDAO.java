package com.edianniu.pscp.portal.shiro.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;


/**
 * defaultWebSessionManager会使用MemorySessionDAO做为默认实现类
 * 自定义CustomShiroSessionDAO继承AbstractSessionDAO
 * 
 * @author zhoujanjian
 *
 */
public class CustomShiroSessionDAO extends AbstractSessionDAO {

	private static Logger logger = Logger.getLogger(CustomShiroSessionDAO.class);
	
	private ShiroSessionRepository shiroSessionRepository;
	
	public ShiroSessionRepository getShiroSessionRepository() {
		return shiroSessionRepository;
	}

	public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}
	
	/**
	 * update
	 */
	public void update(Session session) throws UnknownSessionException {
		getShiroSessionRepository().saveSession(session); 
	}

	/**
	 * delete
	 */
	public void delete(Session session) {
	    if (session == null) {  
	        logger.error( "session is null,delete failed");  
	        return;  
	    }  
		Serializable id=session.getId();
		if (id != null){
		    getShiroSessionRepository().deleteSession(id); 
		}
	}
	
	/**
	 * save
	 */
    @SuppressWarnings("unused")
	private void saveSession(Session session) throws UnknownSessionException{  
    	getShiroSessionRepository().saveSession(session);
    } 

	/**
	 * 统计当前活动的session
	 */
	public Collection<Session> getActiveSessions() {
		return getShiroSessionRepository().getAllSessions(); 
	}

	/**
	 * Create
	 */
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);  
        getShiroSessionRepository().saveSession(session);  
        return sessionId; 
	}

	/**
	 * Read
	 */
	protected Session doReadSession(Serializable sessionId) {
		 return getShiroSessionRepository().getSession(sessionId);  
	}

}
