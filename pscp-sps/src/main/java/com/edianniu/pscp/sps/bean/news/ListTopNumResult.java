package com.edianniu.pscp.sps.bean.news;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.news.VO.NewsListInfoVO;

import java.util.List;

/**
 * ClassName: ListTopNumResult
 * Author: tandingbo
 * CreateTime: 2017-08-10 16:48
 */
public class ListTopNumResult extends Result {
    private static final long serialVersionUID = 1L;

    private List<NewsListInfoVO> newsList;

    public List<NewsListInfoVO> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsListInfoVO> newsList) {
        this.newsList = newsList;
    }
}
