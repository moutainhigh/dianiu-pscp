package com.edianniu.pscp.sps.bean.news;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.domain.News;

/**
 * ClassName: DetailsResult
 * Author: tandingbo
 * CreateTime: 2017-08-11 09:54
 */
public class DetailsResult extends Result {
    private static final long serialVersionUID = 1L;

    private News news;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
