/**
 *
 */
package com.edianniu.pscp.mis.bean.pay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author cyl
 */
public class WxpayNotifyReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String pushXml;

    public String getPushXml() {
        return pushXml;
    }

    public void setPushXml(String pushXml) {
        this.pushXml = pushXml;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).toString();
    }
}
