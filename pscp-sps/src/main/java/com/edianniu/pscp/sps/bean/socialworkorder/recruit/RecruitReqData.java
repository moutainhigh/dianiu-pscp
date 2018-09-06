package com.edianniu.pscp.sps.bean.socialworkorder.recruit;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: RecruitReqData
 * Author: tandingbo
 * CreateTime: 2017-05-18 14:19
 */
public class RecruitReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工单ID
     */
    private Long workOrderId;
    /**
     * 工单编号
     */
    private String orderId;
    /**
     * 客户端IP
     */
    private String clientHost;

    private Long uid;

    private List<RecruitInfo> recruitInfoList;

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public List<RecruitInfo> getRecruitInfoList() {
        return recruitInfoList;
    }

    public void setRecruitInfoList(List<RecruitInfo> recruitInfoList) {
        this.recruitInfoList = recruitInfoList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientHost() {
        return clientHost;
    }

    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }
}
