package com.edianniu.pscp.message.msg.service;

import com.edianniu.pscp.message.msg.domain.MessageSendLog;

/**
 * ClassName: MessageSendLogService
 * Author: tandingbo
 * CreateTime: 2017-05-02 11:39
 */
public interface MessageSendLogService {

    int saveEntity(MessageSendLog entity);
}
