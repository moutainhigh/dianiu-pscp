package com.edianniu.pscp.sps.bean.project;

import java.io.Serializable;

/**
 * ClassName: ListProjectsReqData
 * Author: tandingbo
 * CreateTime: 2017-05-16 09:53
 */
public class ListProjectsReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private Long companyId;
    private Long customerId;
    private boolean includeExpire = true;//是否包含失效的项目（项目过期了）

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public boolean isIncludeExpire() {
        return includeExpire;
    }

    public void setIncludeExpire(boolean includeExpire) {
        this.includeExpire = includeExpire;
    }
}
