package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.message.*;
import com.edianniu.pscp.mis.bean.query.message.MemberMessageQuery;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.MemberMessage;
import com.edianniu.pscp.mis.service.MemberMessageService;
import com.edianniu.pscp.mis.service.dubbo.MemberMessageInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MemberMessageServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:32
 */
@Service
@Repository("memberMessageInfoService")
public class MemberMessageServiceImpl implements MemberMessageInfoService {
    private static final Logger logger = LoggerFactory.getLogger(MemberMessageServiceImpl.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("memberMessageService")
    private MemberMessageService memberMessageService;

    /**
     * 消息列表
     *
     * @param listMessageReqData
     * @return
     */
    @Override
    public ListMessageResult listMessage(ListMessageReqData listMessageReqData) {
        ListMessageResult result = new ListMessageResult();
        try {
            if (listMessageReqData.getOffset() < 0) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("offset 参数必须大于等于0");
                return result;
            }

            MemberMessageQuery memberMessageQuery = new MemberMessageQuery();
            memberMessageQuery.setUid(listMessageReqData.getUid());
            memberMessageQuery.setOffset(listMessageReqData.getOffset());
            if (listMessageReqData.getPageSize() != null && listMessageReqData.getPageSize() > 0) {
                memberMessageQuery.setPageSize(listMessageReqData.getPageSize());
            }
            memberMessageQuery.setIsRead(listMessageReqData.getIsRead());

            PageResult<MessageInfo> pageResult = memberMessageService.queryList(memberMessageQuery);
            if (pageResult.getData() != null) {
                result.setMessages(pageResult.getData());
            }
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 获取所有消息
     *
     * @param listMessageReqData
     * @return
     */
    @Override
    public ListMessageResult listAllMessage(ListMessageReqData listMessageReqData) {
        ListMessageResult result = new ListMessageResult();
        try {
            if (listMessageReqData.getUid() == null) {
                result.setMessages(new ArrayList<MessageInfo>());
                return result;
            }

            Map<String, Object> param = new HashMap<>();
            param.put("uid", listMessageReqData.getUid());
            param.put("isRead", listMessageReqData.getIsRead());
            List<MessageInfo> messageInfoList = memberMessageService.queryAllList(param);
            result.setMessages(messageInfoList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 消息设置已读
     *
     * @param setReadMessageReqData
     * @return
     */
    @Override
    public SetReadMessageResult setReadMessage(SetReadMessageReqData setReadMessageReqData) {
        SetReadMessageResult result = new SetReadMessageResult();
        try {
            if (setReadMessageReqData.getId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("id不能为空");
                return result;
            }

            MemberMessage memberMessage = memberMessageService.getEntityById(setReadMessageReqData.getId());
            if (memberMessage == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("消息不存在");
                return result;
            }

            if (!memberMessage.getIsRead().equals(Constants.TAG_NO)) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("消息已经设置已读过了，无须重复操作");
                return result;
            }

            memberMessage.setIsRead(Constants.TAG_YES);// 已读
            memberMessage.setId(setReadMessageReqData.getId());
            memberMessage.setUid(setReadMessageReqData.getUid());

            memberMessageService.updateMessage(memberMessage);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 清空全部消息
     *
     * @param clearAllMessageReqData
     * @return
     */
    @Override
    public ClearAllMessageResult clearAllMessage(ClearAllMessageReqData clearAllMessageReqData) {
        ClearAllMessageResult result = new ClearAllMessageResult();
        try {
            MemberMessage memberMessage = new MemberMessage();
//            memberMessage.setIsRead(Constants.TAG_YES);// 已读
            memberMessage.setIsDeleted(Constants.TAG_YES);
            memberMessage.setUid(clearAllMessageReqData.getUid());

            memberMessageService.updateMessage(memberMessage);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

}
