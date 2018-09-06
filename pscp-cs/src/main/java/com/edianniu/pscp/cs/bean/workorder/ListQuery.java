package com.edianniu.pscp.cs.bean.workorder;

import com.edianniu.pscp.cs.commons.BaseQuery;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;

/**
 * ClassName: ListQuery
 * Author: tandingbo
 * CreateTime: 2017-08-07 17:11
 */
public class ListQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Integer[] status;
    /**
     * 工单名称
     */
    private String name;
    private Long uid;
    /**
     * 不包含类型(默认:勘察订单)
     */
    private Integer exclusiveType = WorkOrderType.PROSPECTING.getValue();

    public Integer[] getStatus() {
        return status;
    }

    public void setStatus(Integer[] status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getExclusiveType() {
        return exclusiveType;
    }

    public void setExclusiveType(Integer exclusiveType) {
        this.exclusiveType = exclusiveType;
    }
}
