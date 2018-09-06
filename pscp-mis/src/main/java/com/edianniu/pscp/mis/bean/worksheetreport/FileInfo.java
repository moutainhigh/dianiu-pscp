package com.edianniu.pscp.mis.bean.worksheetreport;

import java.io.Serializable;

/**
 * @author tandingbo
 * @create 2017-11-13 11:43
 */
public class FileInfo implements Serializable {
    private static final long serialVersionUID = 5374710381262687457L;

    private String fid;
    private Integer type;
    private Integer orderNum;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
