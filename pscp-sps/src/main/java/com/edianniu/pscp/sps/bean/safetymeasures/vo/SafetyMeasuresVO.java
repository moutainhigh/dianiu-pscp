package com.edianniu.pscp.sps.bean.safetymeasures.vo;

import java.io.Serializable;

/**
 * ClassName: SafetyMeasuresVO
 * Author: tandingbo
 * CreateTime: 2017-05-17 17:17
 */
public class SafetyMeasuresVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Integer checked = 0;

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

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }
}
