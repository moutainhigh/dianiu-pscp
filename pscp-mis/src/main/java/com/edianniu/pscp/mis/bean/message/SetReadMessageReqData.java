package com.edianniu.pscp.mis.bean.message;

import java.io.Serializable;

/**
 * ClassName: SetReadMessageReqData
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:49
 */
public class SetReadMessageReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 消息ID
     */
    private Long id;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
