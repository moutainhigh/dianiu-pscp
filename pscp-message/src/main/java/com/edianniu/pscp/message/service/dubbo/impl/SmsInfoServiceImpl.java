/**
 *
 */
package com.edianniu.pscp.message.service.dubbo.impl;

import com.edianniu.pscp.message.commons.Result;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.result.SmsResult;
import com.edianniu.pscp.message.service.dubbo.SmsInfoService;
import com.edianniu.pscp.message.sms.service.SmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author cyl
 */
@Service
@Repository("smsInfoService")
public class SmsInfoServiceImpl implements SmsInfoService {
    @Autowired
    private SmsService bwSmsService;

    /**
     * 执行发送短信
     *
     * @param mobile    接收短信的手机号码
     * @param smsMsgId  短信ID(可以认为是类型)
     * @param param     参数, 请查看wiki中 短信的msg_id 和参数对应.
     * @return 说明 请查看{@link SmsResult}
     */
    @Override
    public SmsResult sendSms(String mobile, MessageId smsMsgId, Map<String, String> param) {
        return bwSmsService.sendSms(mobile, smsMsgId, param);
    }

    @Override
    public Result clearCache(String msgId) {
        return bwSmsService.clearCache(msgId);
    }

    @Override
    public SmsResult smsReport() {
        return bwSmsService.smsReport();
    }

	@Override
	public String getContent(String mobile, MessageId smsMsgId,
			Map<String, String> params) {
		
		return bwSmsService.getContent(mobile, smsMsgId, params);
	}


}
