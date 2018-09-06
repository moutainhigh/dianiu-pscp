package com.edianniu.pscp.portal.shiro.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
/**
 * Session 的创建、删除、查询 
 * @author zhoujianjian
 */
public interface ShiroSessionRepository {

	//存储Session
	void saveSession(Session session);
	
	//删除session
	void deleteSession(Serializable id);

	//获取所有session
	Collection<Session> getAllSessions();

	//获取sessoin
	Session getSession(Serializable sessionId);
}
