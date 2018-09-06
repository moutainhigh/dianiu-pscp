package com.edianniu.pscp.message.sms.service.impl;

import com.edianniu.pscp.message.commons.CacheKey;
import com.edianniu.pscp.message.commons.DefaultResult;
import com.edianniu.pscp.message.commons.Result;
import com.edianniu.pscp.message.commons.ResultCode;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.result.SmsResult;
import com.edianniu.pscp.message.sms.dao.SmsTemplateDao;
import com.edianniu.pscp.message.sms.domain.SmsMessage;
import com.edianniu.pscp.message.sms.service.SmsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import stc.skymobi.cache.redis.JedisUtil;

import java.util.Map;
@Service
@Repository("bwSmsService")
public class BwSmsService implements SmsService {
    private static final Logger logger = LoggerFactory.getLogger(BwSmsService.class);


    @Autowired
    private SmsTemplateDao smsTemplateDao;
    @Autowired
    private JedisUtil jedisUtil;
    @Autowired
    private BwSmsClient bwSmsClient;

    @Override
    public Result clearCache(String msgId) {
        Result result = new DefaultResult();
        try {
            String field = msgId;
            if (jedisUtil.exists(CacheKey.SMS_CONTEXT_HASH_KEY)) {
                // 存在
                long rs = jedisUtil.hdel(CacheKey.SMS_CONTEXT_HASH_KEY, field);
            }
        } catch (Exception e) {
            logger.error("clearCache:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统错误");

        }
        return result;
    }

    /**
     * 根据公司ID & msg_id 获取短信内容(未处理)
     *
     * @param msgId msg_id
     * @return 短信内容(未格式化)
     */
    private String getContext(String msgId) {
        String context = null;
        Jedis jedis = null;
        try {
            // 从redis 中读取内容.
            if (jedisUtil.exists(CacheKey.SMS_CONTEXT_HASH_KEY)) {
                // 存在
                context = jedisUtil.hget(CacheKey.SMS_CONTEXT_HASH_KEY, msgId);
            }

            if (context == null) {
                // 若没读到.
                // 在库中查询, 当前公司是否设置了短信内容.
                int count = this.smsTemplateDao.queryCount(msgId);
                if (count > 0) {
                    // 设置了, 读取短信内容.
                    context = this.smsTemplateDao.queryContext(msgId);
                    // 将读取到的短信内容放到redis
                    jedisUtil.hset(CacheKey.SMS_CONTEXT_HASH_KEY, msgId, context);

                } else {
                    String adminFiled = msgId + CacheKey.SPLIT + 0L;
                    if (jedisUtil.exists(CacheKey.SMS_CONTEXT_HASH_KEY)) {
                        // 存在
                        context = jedisUtil.hget(CacheKey.SMS_CONTEXT_HASH_KEY, adminFiled);
                    }
                    if (context == null) {
                        // 未设置, 取默认(公司ID为0)的设置
                        context = this.smsTemplateDao.queryContext(msgId);
                        //将读取到的短信内容放到redis
                        jedisUtil.hset(CacheKey.SMS_CONTEXT_HASH_KEY, adminFiled, context);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("getContext:{}", e);
        } 
        return context;
    }

    public String getContent(String mobile, MessageId smsMsgId, Map<String, String> params) {
        String msgId = smsMsgId.getValue();
        // 到库里根据company_id 查询短信内容.
        logger.debug("发送短信[{}]给[{}]", msgId, mobile);

        String context = getContext(msgId);
        logger.debug("短信内容: {} ", context);
        logger.debug("发送短信参数: {} ", params);
        context = context.replace("\n", "");
        if (params != null) {
            // 处理短信内容.
            for (Map.Entry<String, String> paramKV : params.entrySet()) {
                context = context.replace("{" + paramKV.getKey() + "}", paramKV.getValue());
            }
        }
        logger.debug("处理过后的短信内容: {} ", context);
        return context;
    }

    /**
     * 执行发送短信
     *
     * @param mobile   接收短信的手机号码
     * @param smsMsgId 短信ID(可以认为是类型)
     * @param params   参数, 请查看wiki中 短信的msg_id 和参数对应.
     * @return 说明 请查看{@link SmsResult}
     */
    @Override
    public SmsResult sendSms(String mobile, MessageId smsMsgId, Map<String, String> params) {
        String content = this.getContent(mobile, smsMsgId, params);
        logger.info("发送[{} - {}] 给 [{}]", smsMsgId.getValue(), content, mobile);
        return bwSmsClient.sendBwSms(new SmsMessage(mobile, content));
    }

    @Override
    public SmsResult smsReport() {
        return bwSmsClient.smsReport();
    }
}
