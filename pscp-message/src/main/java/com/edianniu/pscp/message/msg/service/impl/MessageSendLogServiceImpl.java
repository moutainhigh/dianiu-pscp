package com.edianniu.pscp.message.msg.service.impl;

import com.edianniu.pscp.message.msg.dao.MessageSendLogDao;
import com.edianniu.pscp.message.msg.domain.MessageSendLog;
import com.edianniu.pscp.message.msg.service.MessageSendLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName: MessageSendLogServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-02 11:39
 */
@Service
@Repository("messageSendLogService")
public class MessageSendLogServiceImpl implements MessageSendLogService {
    private static final Logger logger = LoggerFactory.getLogger(MemberMessageServiceImpl.class);

    @Autowired
    private MessageSendLogDao messageSendLogDao;

    @Override
    @Transactional
    public int saveEntity(MessageSendLog entity) {
        return messageSendLogDao.saveEntity(entity);
    }
}
