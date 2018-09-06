package com.edianniu.pscp.mis.bean.attachment.common;

import com.edianniu.pscp.mis.commons.BaseQuery;

/**
 * ClassName: AttachmentQuery
 * Author: tandingbo
 * CreateTime: 2017-09-19 14:01
 */
public class AttachmentQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long foreignKey;
    private Long companyId;
    private Long memberId;
    private Integer type;
    private Integer businessType;

    public Long getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Long foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }
}
