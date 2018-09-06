package com.edianniu.pscp.sps.bean.electrician.vo;

import java.io.Serializable;

/**
 * ClassName: ElectricianVO
 * Author: tandingbo
 * CreateTime: 2017-05-17 10:40
 */
public class ElectricianVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
