package com.edianniu.pscp.sps.bean.evaluate.electrician.VO;

import java.io.Serializable;

/**
 * ClassName: EvaluateAttachmentVO
 * Author: tandingbo
 * CreateTime: 2017-07-31 15:12
 */
public class EvaluateAttachmentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String fid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
