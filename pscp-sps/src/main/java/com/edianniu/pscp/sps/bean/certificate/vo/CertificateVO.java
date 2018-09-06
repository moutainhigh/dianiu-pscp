package com.edianniu.pscp.sps.bean.certificate.vo;

import java.io.Serializable;

/**
 * ClassName: CertificateVO
 * Author: tandingbo
 * CreateTime: 2017-05-17 14:05
 */
public class CertificateVO implements Serializable {
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
