package com.edianniu.pscp.sps.bean.payment.paypage;

import com.edianniu.pscp.sps.bean.Result;

/**
 * ClassName: AliPayCallbackResult
 * Author: tandingbo
 * CreateTime: 2017-06-02 17:29
 */
public class AliPayCallbackResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 跳转页面地址
     */
    private String pageUrl;

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }
}
