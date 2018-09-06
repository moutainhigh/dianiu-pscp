package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

/**
 * ClassName: FileInfo
 * Author: tandingbo
 * CreateTime: 2017-04-16 18:53
 */
public class FileInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 附件ID
     */
    private Long id;
    /**
     * 类型(1图片，2视频)
     */
    private Integer type;
    /**
     * 文件id
     */
    private String fileId;
    /**
     * 排序字段
     */
    private Integer orderNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
