package com.edianniu.pscp.mis.bean.history.vo;

import java.io.Serializable;

/**
 * ClassName: FacilitatorHistoryVO
 * Author: tandingbo
 * CreateTime: 2017-10-30 10:59
 */
public class FacilitatorHistoryVO implements Serializable {
    private static final long serialVersionUID = -220127944305405793L;

    /**
     * 服务商公司ID
     */
    private Long id;
    /**
     * 服务商公司名称
     */
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
