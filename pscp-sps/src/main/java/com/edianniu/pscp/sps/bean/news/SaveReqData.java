package com.edianniu.pscp.sps.bean.news;

import com.edianniu.pscp.sps.domain.News;

import java.io.Serializable;

/**
 * ClassName: SaveReqData
 * Author: tandingbo
 * CreateTime: 2017-08-17 14:41
 */
public class SaveReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private News news;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
