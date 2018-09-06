package com.edianniu.pscp.mis.bean.query.message;

import com.edianniu.pscp.mis.commons.BaseQuery;

/**
 * ClassName: MemberMessageQuery
 * Author: tandingbo
 * CreateTime: 2017-04-18 10:57
 */
public class MemberMessageQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 是否已读(0:未读;1已读)
     */
    private Integer isRead;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}
