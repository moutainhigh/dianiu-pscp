package com.edianniu.pscp.search.support.needs;

import java.io.Serializable;

/**
 * ClassName: NeedsSaveReqData
 * Author: tandingbo
 * CreateTime: 2017-10-12 10:29
 */
public class NeedsSaveReqData implements Serializable {
    private static final long serialVersionUID = 8765065369729398522L;

    /**
     * 需求ID
     */
    private String id;
    /**
     * 需求编号
     */
    private String orderId;
    /**
     * 需求名称
     */
    private String name;
    /**
     * 需求描述
     */
    private String describe;
    /**
     * 需求审核通过(发布)时间戳
     */
    private Long publishTime;
    /**
     * 发布截止时间戳
     */
    private Long publishCutoffTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public Long getPublishCutoffTime() {
        return publishCutoffTime;
    }

    public void setPublishCutoffTime(Long publishCutoffTime) {
        this.publishCutoffTime = publishCutoffTime;
    }
}
