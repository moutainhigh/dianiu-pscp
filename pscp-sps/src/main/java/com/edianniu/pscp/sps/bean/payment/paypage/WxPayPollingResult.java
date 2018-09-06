package com.edianniu.pscp.sps.bean.payment.paypage;

import com.edianniu.pscp.sps.bean.Result;

/**
 * ClassName: WxPayPollingResult
 * Author: tandingbo
 * CreateTime: 2017-06-02 17:44
 */
public class WxPayPollingResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 支付状态
     * 0:未支付
     * 1:支付确认中(客户端)
     * 2:支付成功(服务端异步通知)
     * 3:支付失败
     * 4:用户取消
     */
    private Integer payState;
    /**
     * 跳转页面
     */
    private String pageUrl;

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }
}
