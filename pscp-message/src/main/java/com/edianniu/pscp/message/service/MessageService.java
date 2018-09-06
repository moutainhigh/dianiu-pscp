/**
 * 
 */
package com.edianniu.pscp.message.service;

import com.edianniu.pscp.message.bean.DynamicMessageInfo;
import com.edianniu.pscp.message.bean.MessageInfo;
import com.edianniu.pscp.message.commons.Result;
import com.edianniu.pscp.message.commons.MessageId;





import com.edianniu.pscp.message.result.SendMessageResult;

import java.util.List;
import java.util.Map;

/**
 * @author cyl
 *
 */
public interface MessageService {

	/**
	 * 发送短信和PUSH消息
	 * @param uid       用户Id
	 * @param companyId 公司ID
	 * @param mobile    接收短信的手机号码
	 * @param messageId  消息ID(可以认为是类型)
	 * @param params     参数, 请查看wiki中 短信的msg_id 和参数对应.
	 * @return 说明 请查看{@link SendMessageResult}
	 */
	SendMessageResult sendSmsAndPushMessage(Long uid,Long companyId, String mobile, MessageId messageId, Map<String, String> params);
	/**
	 * 发送短信和PUSH消息
	 * @param uid       用户Id
	 * @param companyId 公司ID
	 * @param mobile    接收短信的手机号码
	 * @param messageId 消息ID(可以认为是类型)
	 * @param params    参数, 请查看wiki中 短信的msg_id 和参数对应.
	 * @param exts      消息扩展信息
	 * @return
	 */
	SendMessageResult sendSmsAndPushMessage(Long uid,Long companyId, String mobile, MessageId messageId, Map<String, String> params,Map<String, String> exts);
	/**
	 * 发送PUSH消息
	 * @param smsMsg
	 * @return
	 */
	SendMessageResult sendPushMessage(MessageInfo messageInfo);
	/**
	 * 发送SMS消息
	 * @param messageInfo
	 * @return
	 */
	SendMessageResult sendSmsMessage(MessageInfo messageInfo);
	/**
	 * 发送SMS和PUSH消息
	 * @param messageInfo
	 * @return
	 */
	SendMessageResult sendSmsAndPushMessage(MessageInfo messageInfo);
	/**
	 * 根据公司Id和短信id清除缓存
	 * @param companyId
	 * @param msgId
	 * @return
	 */
	Result clearCache(Long companyId,String msgId);
	
	/**
	 * 发送短信和PUSH消息
	 * @param uid       用户Id	
	 * @param mobile    接收短信的手机号码
	 * @param messageId 消息ID(可以认为是类型)
	 * @param params     参数, 请查看wiki中 短信的msg_id 和参数对应.
	 * @return 说明 请查看{@link SendMessageResult}
	 */
	SendMessageResult sendSmsAndPushMessage(Long uid,String mobile, MessageId messageId, Map<String, String> params);
	/**
	 * 发送短信和PUSH消息
	 * @param uid     用户Id
	 * @param mobile  接收短信的手机号码
	 * @param messageId 消息ID(可以认为是类型)
	 * @param params 参数, 请查看wiki中 短信的msg_id 和参数对应.
	 * @param exts   消息扩展信息
	 * @return
	 */
	SendMessageResult sendSmsAndPushMessage(Long uid,String mobile, MessageId messageId, Map<String, String> params,Map<String, String> exts);
	/**
	 * 发送短信消息
	 * @param uid
	 * @param mobile
	 * @param smsMsgId
	 * @param params
	 * @return
	 */
	SendMessageResult sendSmsMessage(Long uid,String mobile, MessageId messageId, Map<String, String> params);
	/**
	 * 发送PUSH消息
	 * @param uid
	 * @param mobile
	 * @param messageId
	 * @param params
	 * @return
	 */
	SendMessageResult sendPushMessage(Long uid,String mobile, MessageId messageId, Map<String, String> params);
	/**
	 * 发送PUSH消息
	 * @param uid
	 * @param mobile
	 * @param messageId
	 * @param params
	 * @param exts
	 * @return
	 */
	SendMessageResult sendPushMessage(Long uid,String mobile, MessageId messageId, Map<String, String> params,Map<String, String> exts);
	/**
	 * 发送个推客户端信息
	 * @param key
	 * @param value
	 * @return
	 */
	SendMessageResult sendGeTuiClientMessage(String key,String value);
	/**
	 * 获取平台动态交易信息
	 * @return
	 */
	List<DynamicMessageInfo> getDynamicMessageInfos();
}
