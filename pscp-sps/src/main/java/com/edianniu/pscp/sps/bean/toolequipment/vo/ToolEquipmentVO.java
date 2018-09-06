package com.edianniu.pscp.sps.bean.toolequipment.vo;

import java.io.Serializable;

/**
 * ClassName: ToolEquipmentVO
 * Author: tandingbo
 * CreateTime: 2017-05-17 15:30
 */
public class ToolEquipmentVO implements Serializable {
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
