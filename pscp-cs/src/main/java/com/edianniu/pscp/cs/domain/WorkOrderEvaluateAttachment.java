package com.edianniu.pscp.cs.domain;

import java.io.Serializable;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-09 10:11:05
 */
public class WorkOrderEvaluateAttachment extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long workOrderEvaluateId;
    //$column.comments
    private Long type;
    //$column.comments
    private String fid;

    /**
     * 设置：${column.comments}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：${column.comments}
     */
    public void setWorkOrderEvaluateId(Long workOrderEvaluateId) {
        this.workOrderEvaluateId = workOrderEvaluateId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getWorkOrderEvaluateId() {
        return workOrderEvaluateId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setType(Long type) {
        this.type = type;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getType() {
        return type;
    }

    /**
     * 设置：${column.comments}
     */
    public void setFid(String fid) {
        this.fid = fid;
    }

    /**
     * 获取：${column.comments}
     */
    public String getFid() {
        return fid;
    }
}
