/**
 *
 */
package com.edianniu.pscp.message.msg.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cyl
 */
public class AuthSubmitMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private Long companyId;
    private Date pushTime;

    public Long getUid() {
        return uid;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

}
