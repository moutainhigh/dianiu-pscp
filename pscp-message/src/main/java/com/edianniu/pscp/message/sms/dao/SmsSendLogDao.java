package com.edianniu.pscp.message.sms.dao;

import org.apache.ibatis.annotations.Mapper;

import com.edianniu.pscp.message.sms.domain.SmsSendLog;

/**
 * ClassName: SmsSendLogDao
 * Author: tandingbo
 * CreateTime: 2017-05-02 14:48
 */
@Mapper
public interface SmsSendLogDao {
    int saveEntity(SmsSendLog entity);

    SmsSendLog getByMsgId(String msgId);

    int updateEntity(SmsSendLog entity);
}
