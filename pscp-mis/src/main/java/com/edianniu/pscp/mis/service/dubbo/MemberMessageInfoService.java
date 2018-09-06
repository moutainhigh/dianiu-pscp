package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.message.*;

/**
 * ClassName: MemberMessageInfoService
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:32
 */
public interface MemberMessageInfoService {
    /**
     * 消息设置已读
     *
     * @param setReadMessageReqData
     * @return
     */
    SetReadMessageResult setReadMessage(SetReadMessageReqData setReadMessageReqData);

    /**
     * 清空全部消息
     *
     * @param clearAllMessageReqData
     * @return
     */
    ClearAllMessageResult clearAllMessage(ClearAllMessageReqData clearAllMessageReqData);

    /**
     * 消息列表
     *
     * @param listMessageReqData
     * @return
     */
    ListMessageResult listMessage(ListMessageReqData listMessageReqData);

    /**
     * 获取所有消息
     *
     * @param listMessageReqData
     * @return
     */
    ListMessageResult listAllMessage(ListMessageReqData listMessageReqData);
}
