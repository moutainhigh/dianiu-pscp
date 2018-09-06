package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

/**
 * ClassName: DispatchUserInfo
 * Author: tandingbo
 * CreateTime: 2017-11-01 09:40
 */
public class DispatchUserInfo implements Serializable {
    private static final long serialVersionUID = -3479152291222091700L;

    /**
     * 派单人姓名
     */
    private String userName;
    /**
     * 联系电话
     */
    private String contactTel;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }
}
