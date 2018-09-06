package com.edianniu.pscp.mis.bean.worksheetreport;

import java.io.Serializable;

/**
 * ClassName: DetailsReqData
 * Author: tandingbo
 * CreateTime: 2017-09-13 10:54
 */
public class DetailsReqData implements Serializable {
    private static final long serialVersionUID = 9146984100240319838L;

    private Long id;
    private int type;

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
