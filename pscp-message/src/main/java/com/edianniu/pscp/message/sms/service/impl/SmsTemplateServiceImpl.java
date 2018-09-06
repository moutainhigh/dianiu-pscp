package com.edianniu.pscp.message.sms.service.impl;

import com.edianniu.pscp.message.sms.dao.SmsTemplateDao;
import com.edianniu.pscp.message.sms.service.SmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: SmsTemplateServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-02 14:43
 */
@Service
@Repository("smsTemplateService")
public class SmsTemplateServiceImpl implements SmsTemplateService {

    @Autowired
    private SmsTemplateDao smsTemplateDao;
}
