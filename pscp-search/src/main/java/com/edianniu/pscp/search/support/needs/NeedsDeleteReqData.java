package com.edianniu.pscp.search.support.needs;

import java.io.Serializable;

/**
 * ClassName: NeedsDeleteReqData
 * Author: tandingbo
 * CreateTime: 2017-10-12 17:34
 */
public class NeedsDeleteReqData implements Serializable {
    private static final long serialVersionUID = -6392199581615776912L;

    /* 需求ID.*/
    private String needsId;

    public String getNeedsId() {
        return needsId;
    }

    public void setNeedsId(String needsId) {
        this.needsId = needsId;
    }
}
