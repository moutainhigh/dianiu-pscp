/**
 *
 */
package com.edianniu.pscp.message.service.dubbo;

import com.edianniu.pscp.message.commons.Result;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.result.SmsResult;

import java.util.Map;

/**
 * @author cyl
 */
public interface SmsInfoService {
    /**
     * 短信发送状态同步
     *
     * @return
     */
    public SmsResult smsReport();

    /**
     * 执行发送短信
     *
     * @param mobile    接收短信的手机号码
     * @param smsMsgId  短信ID(可以认为是类型)
     * @param params    参数, 请查看wiki中 短信的msg_id 和参数对应.
     * @return 说明 请查看{@link SmsResult}
     */
    SmsResult sendSms(String mobile, MessageId smsMsgId, Map<String, String> params);

    /**
     * 根据公司Id和短信id清除缓存
     *
     * @param msgId
     * @return
     */
    Result clearCache(String msgId);
    /**
     * 获取短信内容
     * @param mobile
     * @param smsMsgId
     * @param params
     * @return
     */
    public String getContent(String mobile, MessageId smsMsgId, Map<String, String> params);
}
