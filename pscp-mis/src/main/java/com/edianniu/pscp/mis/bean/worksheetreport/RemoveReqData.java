package com.edianniu.pscp.mis.bean.worksheetreport;

import java.io.Serializable;

/**
 * ClassName: RemoveReqData
 * Author: tandingbo
 * CreateTime: 2017-09-13 10:54
 */
public class RemoveReqData implements Serializable {
    private static final long serialVersionUID = 1150791660813772653L;

    private Long uid;
    private Long id;
    private int type;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
