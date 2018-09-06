package com.edianniu.pscp.mis.bean.wallet;

import java.io.Serializable;

public class BankType implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String icon;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


}
