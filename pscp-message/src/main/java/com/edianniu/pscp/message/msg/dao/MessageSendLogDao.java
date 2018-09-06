package com.edianniu.pscp.message.msg.dao;

import org.apache.ibatis.annotations.Mapper;

import com.edianniu.pscp.message.msg.domain.MessageSendLog;

/**
 * ClassName: MessageSendLogDao
 * Author: tandingbo
 * CreateTime: 2017-05-02 11:40
 */
@Mapper
public interface MessageSendLogDao {
    int saveEntity(MessageSendLog entity);
}
