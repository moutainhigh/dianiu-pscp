package com.edianniu.pscp.portal.shiro.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import stc.skymobi.cache.redis.JedisUtil;


/**
 * 自定义Cache
 * @author zhoujianjian
 *
 * @param <K>
 * @param <V>
 */
public class RedisCache<K, V> implements Cache<K, V> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JedisUtil jedisUtil;
	
	//前缀
	public static final String REDIS_SHIRO_SESSION = "redis_shiro_session:";
	
	public RedisCache(JedisUtil jedisUtil){
		 if (jedisUtil == null) {
			 logger.error("Cache argument cannot be null");
	     }
	     this.jedisUtil = jedisUtil;
	}
	
	/**
	 * 将key转成String
	 */
	private String keyToString(K key){
		String stringKey;
		if(key instanceof String){
			stringKey = REDIS_SHIRO_SESSION + key;
    	}else{
    		stringKey = REDIS_SHIRO_SESSION + key.toString();
    	}
		return stringKey;
	}
 	
	//get
	public V get(K key) throws CacheException{
		V v=null;
		try {
			if (key == null) {
	            return null;
	        }else{
	        	Object object = jedisUtil.getObject(keyToString(key));
	        	v=(V)object;
	        	return v;
	        }
		} catch (Throwable t) {
			logger.error("get cache error");
			throw new CacheException(t);
		}
	}

	//put
	public V put(K key, V value) throws CacheException {
		 try {
			 jedisUtil.setObject(keyToString(key), value, 1800);
	         return value;
        } catch (Throwable t) {
        	logger.error("put cache error");
            throw new CacheException(t);
        }
	}

	//remove
	public V remove(K key) throws CacheException {
		try {
            V previous = get(key);
            jedisUtil.del(keyToString(key));
            return previous;
        } catch (Throwable t) {
        	logger.error("remove cache error");
            throw new CacheException(t);
        }
	}

	//clear
	public void clear() throws CacheException {}

	//获取size
	public int size() {
		return 0;
	}

	//获取所有key
	@SuppressWarnings("unchecked")
	public Set<K> keys() {
		try {
            Set<String> keys = jedisUtil.keys(REDIS_SHIRO_SESSION + "*");
            if (CollectionUtils.isEmpty(keys)) {
            	return Collections.emptySet();
            }else{
            	Set<K> newKeys = new HashSet<K>();
            	for(String key:keys){
            		newKeys.add((K)key);
            	}
            	return newKeys;
            }
        } catch (Throwable t) {
        	logger.error("get all keys error");
            throw new CacheException(t);
        }
	}

	//获取所有Value
	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		try {
			Set<String> keys = jedisUtil.keys(REDIS_SHIRO_SESSION + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<V>(keys.size());
                for (String key : keys) {
					V value = get((K)key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
        	logger.error("get all values error");
            throw new CacheException(t);
        }
	}
	
}