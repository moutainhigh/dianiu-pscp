package com.edianniu.pscp.sps.bean.news;

import java.io.Serializable;

/**
 * ClassName: ListTopNumReqData
 * Author: tandingbo
 * CreateTime: 2017-08-10 17:33
 */
public class ListTopNumReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 新闻类型.*/
    private Integer type;
    /* 指定获取数量.*/
    private Integer topn;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTopn() {
        return topn;
    }

    public void setTopn(Integer topn) {
        this.topn = topn;
    }
}
