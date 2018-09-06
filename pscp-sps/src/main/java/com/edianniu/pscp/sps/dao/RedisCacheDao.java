package com.edianniu.pscp.sps.dao;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * ClassName: JedisCacheDao
 * Author: tandingbo
 * CreateTime: 2017-06-06 15:12
 */
@Repository
public class RedisCacheDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;
    /*
    public JedisCacheDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }*/

    public RedisCacheDao() {
        jedisPool = new JedisPool(redisHost, redisPort);
    }

    private RuntimeSchema<String> schema = RuntimeSchema.createFrom(String.class);

    public String getCache(String key) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    String str = schema.newMessage();
                    ProtobufIOUtil.mergeFrom(bytes, str, schema);
                    return str;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putCache(String key, String value) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                byte[] bytes = ProtobufIOUtil.toByteArray(value, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                // 超时缓存
//                int timeout = 60 * 60;// 1小时
                String result = jedis.set(key.getBytes(), bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Long delCache(String key) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                Long result = jedis.del(key.getBytes());
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private int redisPort;
    private String redisHost;

    @Value(value = "${redis.cache.port}")
    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    @Value(value = "${redis.cache.host}")
    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public static void main(String[] args) {
        RedisCacheDao jedisCacheDao = new RedisCacheDao();
        String key = "tanbobo:123456";
        jedisCacheDao.delCache(key);
//        String str = jedisCacheDao.putCache(key, "测试");
//        System.out.println("put cache result:" + str);
        String r = jedisCacheDao.getCache(key);
        System.out.println("get cache result:" + r);
    }


}
