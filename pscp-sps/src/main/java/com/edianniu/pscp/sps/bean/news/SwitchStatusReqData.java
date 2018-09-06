package com.edianniu.pscp.sps.bean.news;

import java.io.Serializable;

/**
 * ClassName: SwitchStatusReqData
 * Author: tandingbo
 * CreateTime: 2017-08-17 14:24
 */
public class SwitchStatusReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private Long newsId;
    private Integer status;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
