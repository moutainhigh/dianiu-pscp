package com.edianniu.pscp.mis.bean.workorder.room;

import java.io.Serializable;

/**
 * ClassName: RoomInfo
 * Author: tandingbo
 * CreateTime: 2017-09-15 16:50
 */
public class RoomInfo implements Serializable {
    private static final long serialVersionUID = 6096567134563506619L;

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
