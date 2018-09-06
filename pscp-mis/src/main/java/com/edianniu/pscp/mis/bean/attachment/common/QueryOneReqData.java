package com.edianniu.pscp.mis.bean.attachment.common;

import java.io.Serializable;

/**
 * ClassName: QueryOneReqData
 * Author: tandingbo
 * CreateTime: 2017-09-19 11:42
 */
public class QueryOneReqData implements Serializable {
    private static final long serialVersionUID = 6418855374700934921L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
