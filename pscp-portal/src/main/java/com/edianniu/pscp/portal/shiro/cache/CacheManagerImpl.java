package com.edianniu.pscp.portal.shiro.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import stc.skymobi.cache.redis.JedisUtil;

/**
 * 自定义CacheManager
 * @author zhoujainjian
 *
 */
public class CacheManagerImpl implements CacheManager {

	@SuppressWarnings("rawtypes")
	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

	@Autowired
	private JedisUtil jedisUtil;

	/**
	 * getCache
	 */
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		@SuppressWarnings("unchecked")
		Cache<K, V> c = caches.get(name);
		if (c == null) {
			c = new RedisCache<K, V>(jedisUtil);
			caches.put(name, c);
		}
		return c;
	}

	public JedisUtil getRedisManager() {
		return jedisUtil;
	}

	public void setRedisManager(JedisUtil jedisUtil) {
		this.jedisUtil = jedisUtil;
	}

}
