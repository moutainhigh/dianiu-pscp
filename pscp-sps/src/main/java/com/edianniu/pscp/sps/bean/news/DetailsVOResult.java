package com.edianniu.pscp.sps.bean.news;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.news.VO.NewsDetailsInfoVO;

/**
 * ClassName: DetailsVOResult
 * Author: tandingbo
 * CreateTime: 2017-08-10 16:46
 */
public class DetailsVOResult extends Result {
    private static final long serialVersionUID = 1L;

    private NewsDetailsInfoVO newsDetails;

    public NewsDetailsInfoVO getNewsDetails() {
        return newsDetails;
    }

    public void setNewsDetails(NewsDetailsInfoVO newsDetails) {
        this.newsDetails = newsDetails;
    }
}
