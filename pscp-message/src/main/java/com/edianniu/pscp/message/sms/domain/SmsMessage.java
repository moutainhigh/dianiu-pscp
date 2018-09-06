/**
 *
 */
package com.edianniu.pscp.message.sms.domain;

import java.io.Serializable;

/**
 * @author cyl
 */
public class SmsMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long companyId;
    private String mobile;
    private String content;

    public SmsMessage(String mobile, String content) {
        this.mobile = mobile;
        this.content = content;
    }

    public SmsMessage() {

    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
