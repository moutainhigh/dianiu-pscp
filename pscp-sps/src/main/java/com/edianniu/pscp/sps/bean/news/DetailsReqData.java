package com.edianniu.pscp.sps.bean.news;

import java.io.Serializable;

/**
 * ClassName: DetailsReqData
 * Author: tandingbo
 * CreateTime: 2017-08-17 14:45
 */
public class DetailsReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private Long newsId;

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
}
