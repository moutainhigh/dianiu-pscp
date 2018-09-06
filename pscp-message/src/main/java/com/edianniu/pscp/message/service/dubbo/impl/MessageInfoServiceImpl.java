package com.edianniu.pscp.message.service.dubbo.impl;

import com.edianniu.pscp.message.bean.DynamicMessageInfo;
import com.edianniu.pscp.message.bean.MessageInfo;
import com.edianniu.pscp.message.commons.Result;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.result.SendMessageResult;
import com.edianniu.pscp.message.service.MessageService;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Repository("messageInfoService")
public class MessageInfoServiceImpl implements MessageInfoService {
    @Autowired
    private MessageService messageService;

    @Override
    public SendMessageResult sendSmsAndPushMessage(Long uid, Long companyId,
                                         String mobile, MessageId smsMsgId, Map<String, String> params) {
        return messageService.sendSmsAndPushMessage(uid, companyId, mobile, smsMsgId, params);
    }

    @Override
    public Result clearCache(Long companyId, String msgId) {
        return messageService.clearCache(companyId, msgId);
    }

    @Override
    public SendMessageResult sendSmsAndPushMessage(Long uid, String mobile,
                                         MessageId smsMsgId, Map<String, String> params) {
        return messageService.sendSmsAndPushMessage(uid, mobile, smsMsgId, params);
    }

	@Override
	public SendMessageResult sendPushMessage(MessageInfo messageInfo) {
		return messageService.sendPushMessage(messageInfo);
	}

	@Override
	public SendMessageResult sendGeTuiClientMessage(String key, String value) {
		return messageService.sendGeTuiClientMessage(key, value);
	}

	@Override
	public SendMessageResult sendSmsMessage(Long uid, String mobile,
			MessageId messageId, Map<String, String> params) {
		
		return messageService.sendSmsMessage(uid, mobile, messageId, params);
	}

	@Override
	public SendMessageResult sendSmsAndPushMessage(Long uid, Long companyId,
			String mobile, MessageId messageId, Map<String, String> params,
			Map<String, String> exts) {
		return messageService.sendSmsAndPushMessage(uid, companyId, mobile, messageId, params,exts);
	}

	@Override
	public SendMessageResult sendSmsAndPushMessage(Long uid, String mobile,
			MessageId messageId, Map<String, String> params,
			Map<String, String> exts) {
		
		return messageService.sendSmsAndPushMessage(uid, mobile, messageId, params,exts);
	}

	@Override
	public SendMessageResult sendPushMessage(Long uid, String mobile,
			MessageId messageId, Map<String, String> params,
			Map<String, String> exts) {
		
		return messageService.sendPushMessage(uid, mobile, messageId, params, exts);
	}

	@Override
	public SendMessageResult sendSmsMessage(Long uid, String mobile,
			MessageId messageId, Map<String, String> params,
			Map<String, String> exts) {
		return messageService.sendSmsAndPushMessage(uid, mobile, messageId, params,exts);
	}
	@Override
	public List<DynamicMessageInfo> getDynamicMessageInfos() {
		return messageService.getDynamicMessageInfos();
	}

}
