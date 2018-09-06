package com.edianniu.pscp.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.message.bean.DynamicMessageInfo;
import com.edianniu.pscp.message.bean.MessageInfo;
import com.edianniu.pscp.message.commons.CacheKey;
import com.edianniu.pscp.message.commons.DefaultResult;
import com.edianniu.pscp.message.commons.Result;
import com.edianniu.pscp.message.commons.ResultCode;
import com.edianniu.pscp.message.kafka.producer.DefaultKafkaProducerService;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.result.SendMessageResult;
import com.edianniu.pscp.message.service.MessageService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import stc.skymobi.cache.redis.JedisUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Repository("messageService")
public class DefaultMessageService implements MessageService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageService.class);
    @Autowired
    private JedisUtil jedisUtil;
    @Autowired
    private DefaultKafkaProducerService kafkaProducerService;

    @Override
    public Result clearCache(Long companyId, String msgId) {
        Result result = new DefaultResult();
        try {
            String field = msgId + CacheKey.SPLIT + companyId;
            if (jedisUtil.exists(CacheKey.SMS_CONTEXT_HASH_KEY)) {
                // 存在
                long rs = jedisUtil.hdel(CacheKey.SMS_CONTEXT_HASH_KEY, field);
            }
        } catch (Exception e) {
            logger.error("clearCache:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统错误");

        } finally {
        }
        return result;
    }

    /**
     * 发送短信和PUSH消息
     *
     * @param companyId 公司ID
     * @param mobile    接收短信的手机号码
     * @param messageId  短信ID(可以认为是类型)
     * @param params    参数, 请查看wiki中 短信的msg_id 和参数对应.
     * @return 说明 请查看{@link SendMessageResult}
     */
    @Override
    public SendMessageResult sendSmsAndPushMessage(Long uid, Long companyId, String mobile, MessageId messageId, Map<String, String> params) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMobile(mobile);
        messageInfo.setCompanyId(companyId);
        messageInfo.setMsgId(messageId.getValue());
        messageInfo.setParams(params);
        messageInfo.setUid(uid);
        messageInfo.setCompanyId(companyId);
        messageInfo.setPushTime(new Date());
        return this.sendSmsAndPushMessage(messageInfo);
    }

    @Override
    public SendMessageResult sendSmsAndPushMessage(Long uid, String mobile, MessageId messageId, Map<String, String> params) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMobile(mobile);
        messageInfo.setMsgId(messageId.getValue());
        messageInfo.setParams(params);
        messageInfo.setUid(uid);
        messageInfo.setPushTime(new Date());
        return this.sendSmsAndPushMessage(messageInfo);
    }
    private boolean validMessageInfo(MessageInfo messageInfo){
    	if(messageInfo==null||StringUtils.isBlank(messageInfo.getMsgId())){
			return false;
		}
    	return true;
    }
	@Override
	public SendMessageResult sendPushMessage(MessageInfo messageInfo) {
		if(!validMessageInfo(messageInfo)){
			throw new RuntimeException("messageInfo is null!");
		}
		MessageId msgId=MessageId.parse(messageInfo.getMsgId());
		kafkaProducerService.sendMsg(msgId.getValue(), JSONObject.toJSONString(messageInfo));
        return new SendMessageResult();
	}
	@Override
	public SendMessageResult sendSmsMessage(MessageInfo messageInfo) {
		if(!validMessageInfo(messageInfo)){
			throw new RuntimeException("messageInfo is null!");
		}
		MessageId msgId=MessageId.parse(messageInfo.getMsgId());
		kafkaProducerService.sendSms(msgId.getValue(), JSONObject.toJSONString(messageInfo));
        return new SendMessageResult();
	}

	@Override
	public SendMessageResult sendSmsAndPushMessage(MessageInfo messageInfo) {
		if(!validMessageInfo(messageInfo)){
			throw new RuntimeException("messageInfo is null!");
		}
		MessageId msgId=MessageId.parse(messageInfo.getMsgId());
		kafkaProducerService.sendSms(msgId.getValue(), JSONObject.toJSONString(messageInfo));
		kafkaProducerService.sendMsg(msgId.getValue(), JSONObject.toJSONString(messageInfo));
        return new SendMessageResult();
	}

	@Override
	public SendMessageResult sendGeTuiClientMessage(String key, String value) {
		kafkaProducerService.sendGeTuiClient(key, value);
		return new SendMessageResult();
	}

	@Override
	public SendMessageResult sendSmsMessage(Long uid, String mobile,
			MessageId messageId, Map<String, String> params) {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setMobile(mobile);
		messageInfo.setMsgId(messageId.getValue());
		messageInfo.setParams(params);
		messageInfo.setUid(uid);
		messageInfo.setPushTime(new Date());
        kafkaProducerService.sendSms(messageId.getValue(), JSONObject.toJSONString(messageInfo));
        return new SendMessageResult();
	}

	@Override
	public SendMessageResult sendSmsAndPushMessage(Long uid, Long companyId,
			String mobile, MessageId messageId, Map<String, String> params,
			Map<String, String> exts) {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setMobile(mobile);
		messageInfo.setMsgId(messageId.getValue());
		messageInfo.setParams(params);
		messageInfo.setExts(exts);
		messageInfo.setUid(uid);
		messageInfo.setCompanyId(companyId);
		messageInfo.setPushTime(new Date());
		return this.sendSmsAndPushMessage(messageInfo);
	}

	@Override
	public SendMessageResult sendSmsAndPushMessage(Long uid, String mobile,
			MessageId messageId, Map<String, String> params,
			Map<String, String> exts) {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setMobile(mobile);
		messageInfo.setMsgId(messageId.getValue());
		messageInfo.setParams(params);
		messageInfo.setExts(exts);
		messageInfo.setUid(uid);
		messageInfo.setPushTime(new Date());
		return this.sendSmsAndPushMessage(messageInfo);
	}

	@Override
	public SendMessageResult sendPushMessage(Long uid, String mobile,
			MessageId messageId, Map<String, String> params) {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setMobile(mobile);
		messageInfo.setMsgId(messageId.getValue());
		messageInfo.setParams(params);
		messageInfo.setUid(uid);
		messageInfo.setPushTime(new Date());
		return this.sendPushMessage(messageInfo);
	}

	@Override
	public SendMessageResult sendPushMessage(Long uid, String mobile,
			MessageId messageId, Map<String, String> params,
			Map<String, String> exts) {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setMobile(mobile);
		messageInfo.setMsgId(messageId.getValue());
		messageInfo.setParams(params);
		messageInfo.setExts(exts);
		messageInfo.setUid(uid);
		messageInfo.setPushTime(new Date());
		return this.sendPushMessage(messageInfo);
	}

	@Override
	public List<DynamicMessageInfo> getDynamicMessageInfos() {
		List<DynamicMessageInfo> dyList=new ArrayList<>();
		List<String> list=jedisUtil.lrangeString(CacheKey.CACHE_KEY_LATEST_DYNAMIC_MESSAGE_LIST, 0, CacheKey.LATEST_DYNAMIC_MESSAGE_LIST_MAX_COUNT);
		if(list!=null&&!list.isEmpty()){
			for(String s:list){
				DynamicMessageInfo dynamicMessageInfo=JSON.parseObject(s, DynamicMessageInfo.class);
				dyList.add(dynamicMessageInfo);
			}
		}
		return dyList;
	}

	
}
