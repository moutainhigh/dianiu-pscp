package com.edianniu.pscp.mis.domain;

/**
 * ClassName: ElectricianEvaluateAttachment
 * Author: tandingbo
 * CreateTime: 2017-05-12 10:24
 */
public class ElectricianEvaluateAttachment extends BaseDo {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 电工评价信息ID(pscp_electrician_evaluate的ID)
     */
    private Long electricianEvaluateId;
    /**
     * 类型(1图片，2语音，3视频)
     */
    private Long type;
    /**
     * 文件ID
     */
    private String fid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getElectricianEvaluateId() {
        return electricianEvaluateId;
    }

    public void setElectricianEvaluateId(Long electricianEvaluateId) {
        this.electricianEvaluateId = electricianEvaluateId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
