package com.edianniu.pscp.mis.bean.workorder;

import java.io.Serializable;

/**
 * ClassName: WorkOrderTypeInfo
 * Author: tandingbo
 * CreateTime: 2017-09-14 11:47
 */
public class WorkOrderTypeInfo implements Serializable {
    private static final long serialVersionUID = -2699509897639579446L;
    
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
