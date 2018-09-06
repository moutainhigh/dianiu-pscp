/**
 *
 */
package com.edianniu.pscp.mis.bean.pay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Map;

/**
 * 支付宝推送请求参数
 *
 * @author AbnerElk
 */
public class AlipayNotifyReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, String> aliPush;

    public Map<String, String> getAliPush() {
        return aliPush;
    }

    public void setAliPush(Map<String, String> aliPush) {
        this.aliPush = aliPush;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
                .toString();
    }
}
