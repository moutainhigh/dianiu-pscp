package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.message.MessageInfo;
import com.edianniu.pscp.mis.bean.query.message.MemberMessageQuery;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.domain.MemberMessage;

import java.util.List;
import java.util.Map;

/**
 * ClassName: MemberMessageService
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:30
 */
public interface MemberMessageService {
    /**
     * 根据主键ID获取消息
     *
     * @param id
     * @return
     */
    MemberMessage getEntityById(Long id);

    /**
     * 修改用户消息
     *
     * @param memberMessage
     * @return
     */
    Integer updateMessage(MemberMessage memberMessage);

    /**
     * 分页获取消息
     *
     * @param memberMessageQuery
     * @return
     */
    PageResult<MessageInfo> queryList(MemberMessageQuery memberMessageQuery);

    /**
     * 获取用户未读取消息总记录数
     *
     * @param uid
     * @return Integer
     */
    public Integer getMemberNotReadMessagesCount(Long uid);

    /**
     * 获取所有消息
     *
     * @param param
     * @return
     */
    List<MessageInfo> queryAllList(Map<String, Object> param);
}
