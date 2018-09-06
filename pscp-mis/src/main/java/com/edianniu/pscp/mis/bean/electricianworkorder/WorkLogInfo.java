package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: WorkLogInfo
 * Author: tandingbo
 * CreateTime: 2017-04-17 11:43
 */
public class WorkLogInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工作记录ID
     */
    private Long id;
    /**
     * 工作内容
     */
    private String content;
    /**
     * 工作记录添加时间(yyyy-MM-dd HH:mm:ss)
     */
    private String createTime;
    /**
     * 工作记录附件
     */
    private List<FileInfo> files;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<FileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfo> files) {
        this.files = files;
    }
}
