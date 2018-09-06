package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.bean.query.message.MemberMessageQuery;
import com.edianniu.pscp.mis.domain.MemberMessage;

import java.util.List;
import java.util.Map;

/**
 * ClassName: MemberMessageDao
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:31
 */
public interface MemberMessageDao {
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
    List<MemberMessage> queryList(MemberMessageQuery memberMessageQuery);

    /**
     * 获取总记录数
     *
     * @param memberMessageQuery
     * @return
     */
    Integer queryCount(MemberMessageQuery memberMessageQuery);

    /**
     * 获取用户未读取消息总记录数
     *
     * @param uid
     * @return Integer
     */
    public Integer getMemberNotReadMessagesCount(Long uid);

    List<MemberMessage> queryAllList(Map<String, Object> param);
}
