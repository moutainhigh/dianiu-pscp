package com.edianniu.pscp.mis.bean.query.history;

import com.edianniu.pscp.mis.commons.BaseQuery;

/**
 * ClassName: FacilitatorHistoryQuery
 * Author: tandingbo
 * CreateTime: 2017-10-30 11:21
 */
public class FacilitatorHistoryQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
