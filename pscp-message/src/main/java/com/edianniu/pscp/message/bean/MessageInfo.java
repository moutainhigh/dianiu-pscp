/**
 *
 */
package com.edianniu.pscp.message.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author cyl
 */
public class MessageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long companyId;
    private String mobile;
    private String msgId;
    private Long uid;
    private Map<String, String> params;//消息模版参数数据，用来替换模版数据
    private Map<String,String> exts;//消息扩展数据
    private Date pushTime;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

	public Map<String, String> getExts() {
		return exts;
	}

	public void setExts(Map<String, String> exts) {
		this.exts = exts;
	}
}
