package com.edianniu.pscp.message.sms.dao;

import com.edianniu.pscp.message.sms.domain.SmsTemplate;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ClassName: SmsTemplateDao
 * Author: tandingbo
 * CreateTime: 2017-05-02 14:44
 */
@Mapper
public interface SmsTemplateDao {
    int saveEntity(SmsTemplate entity);

    int queryCount(@Param("msgId") String msgId);

    String queryContext(@Param("msgId") String msgId);
}
