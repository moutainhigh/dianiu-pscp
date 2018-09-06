package com.edianniu.pscp.sps.bean.socialworkorder.recruit;

import com.edianniu.pscp.sps.bean.Result;

import java.util.List;

/**
 * ClassName: RecruitResult
 * Author: tandingbo
 * CreateTime: 2017-05-18 14:28
 */
public class RecruitResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 社会工单ID
     */
    private List<Long> socialWorkOrderIds;
    /**
     * 社会工单编号（多个以,隔开）
     */
    private String socialOrderIdStr;

    public List<Long> getSocialWorkOrderIds() {
        return socialWorkOrderIds;
    }

    public void setSocialWorkOrderIds(List<Long> socialWorkOrderIds) {
        this.socialWorkOrderIds = socialWorkOrderIds;
    }

    public String getSocialOrderIdStr() {
        return socialOrderIdStr;
    }

    public void setSocialOrderIdStr(String socialOrderIdStr) {
        this.socialOrderIdStr = socialOrderIdStr;
    }
}
