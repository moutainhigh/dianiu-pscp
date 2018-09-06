package com.edianniu.pscp.mis.domain;

/**
 * 电工工作日志附件信息
 * ClassName: ElectricianWorkLogAttachment
 * Author: tandingbo
 * CreateTime: 2017-04-16 18:44
 */
public class ElectricianWorkLogAttachment extends BaseDo {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 工作日志ID(pscp_electrician_work_log id)
     */
    private Long workLogId;
    /**
     * 类型(1 图片，2 视频，3其他附件)
     */
    private Integer type;
    /**
     * 文件id
     */
    private String fid;
    /**
     * 企业ID
     */
    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkLogId() {
        return workLogId;
    }

    public void setWorkLogId(Long workLogId) {
        this.workLogId = workLogId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
