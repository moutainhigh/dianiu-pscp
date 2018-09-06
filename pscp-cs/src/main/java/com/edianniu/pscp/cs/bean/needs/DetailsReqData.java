package com.edianniu.pscp.cs.bean.needs;

import java.io.Serializable;

/**
 * 需求详情
 *
 * @author zhoujianjian
 * 2017年9月17日下午11:46:03
 */
public class DetailsReqData implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户ID
    private Long uid;
    // 需求ID
    private Long needsId;
    //需求编号
    private String orderId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getNeedsId() {
        return needsId;
    }

    public void setNeedsId(Long needsId) {
        this.needsId = needsId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


}
