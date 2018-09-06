package com.edianniu.pscp.message.sms.service.impl;

import com.edianniu.pscp.message.sms.dao.SmsSendLogDao;
import com.edianniu.pscp.message.sms.service.SmsSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: SmsSendLogServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-02 14:48
 */
@Service
@Repository("smsSendLogService")
public class SmsSendLogServiceImpl implements SmsSendLogService {

    @Autowired
    private SmsSendLogDao smsSendLogDao;
}
