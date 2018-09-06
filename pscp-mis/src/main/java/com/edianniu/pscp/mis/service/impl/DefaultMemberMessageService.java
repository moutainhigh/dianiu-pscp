package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.bean.message.MessageInfo;
import com.edianniu.pscp.mis.bean.query.message.MemberMessageQuery;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.dao.MemberMessageDao;
import com.edianniu.pscp.mis.domain.MemberMessage;
import com.edianniu.pscp.mis.service.MemberMessageService;
import com.edianniu.pscp.mis.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DefaultMemberMessageService
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:30
 */
@Service
@Repository("memberMessageService")
public class DefaultMemberMessageService implements MemberMessageService {

    @Autowired
    private MemberMessageDao memberMessageDao;

    /**
     * 根据主键ID获取消息
     *
     * @param id
     * @return
     */
    @Override
    public MemberMessage getEntityById(Long id) {
        return memberMessageDao.getEntityById(id);
    }

    /**
     * 修改用户消息
     *
     * @param memberMessage
     * @return
     */
    @Override
    public Integer updateMessage(MemberMessage memberMessage) {
        return memberMessageDao.updateMessage(memberMessage);
    }

    /**
     * 分页获取消息
     *
     * @param memberMessageQuery
     * @return
     */
    @Override
    public PageResult<MessageInfo> queryList(MemberMessageQuery memberMessageQuery) {
        PageResult<MessageInfo> result = new PageResult<MessageInfo>();
        int total = memberMessageDao.queryCount(memberMessageQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<MemberMessage> list = memberMessageDao.queryList(memberMessageQuery);
            List<MessageInfo> data = new ArrayList<MessageInfo>();
            for (MemberMessage memberMessage : list) {
                MessageInfo messageInfo = getMemberMessageInfo(memberMessage);
                data.add(messageInfo);
            }
            result.setData(data);
            nextOffset = memberMessageQuery.getOffset() + data.size();
            result.setNextOffset(nextOffset);
        }
        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }
        result.setOffset(memberMessageQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }

    private MessageInfo getMemberMessageInfo(MemberMessage memberMessage) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setId(memberMessage.getId());
        messageInfo.setTitle(memberMessage.getTitle());// 消息标题
        messageInfo.setContent(memberMessage.getContent());// 消息内容
        messageInfo.setCategory(memberMessage.getCategory());// 类别
        messageInfo.setExt(memberMessage.getExt());// 扩展数据
        messageInfo.setIsRead(memberMessage.getIsRead());// 是否已读(0:未读;1已读)
        messageInfo.setPushTime(DateUtil.getFormatDate(memberMessage.getPushTime(), DateUtil.YYYY_MM_DD_HH_MM_SS_FORMAT));// 推送时间
        return messageInfo;
    }

    @Override
    public Integer getMemberNotReadMessagesCount(Long uid) {
        Integer count = memberMessageDao.getMemberNotReadMessagesCount(uid);
        return count;
    }

    /**
     * 获取所有消息
     *
     * @param param
     * @return
     */
    @Override
    public List<MessageInfo> queryAllList(Map<String, Object> param) {
        List<MessageInfo> data = new ArrayList<MessageInfo>();
        List<MemberMessage> list = memberMessageDao.queryAllList(param);
        if (CollectionUtils.isNotEmpty(list)) {
            for (MemberMessage memberMessage : list) {
                MessageInfo messageInfo = getMemberMessageInfo(memberMessage);
                data.add(messageInfo);
            }
        }
        return data;
    }
}
