package com.edianniu.pscp.sps.bean.workorder.chieforder.vo;

import java.io.Serializable;

/**
 * ClassName: ElectricianVO
 * Author: tandingbo
 * CreateTime: 2017-05-16 16:53
 */
public class ElectricianVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 用户姓名
     */
    private String name;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
