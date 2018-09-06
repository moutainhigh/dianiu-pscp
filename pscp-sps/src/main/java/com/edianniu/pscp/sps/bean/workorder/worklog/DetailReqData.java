package com.edianniu.pscp.sps.bean.workorder.worklog;

import java.io.Serializable;

/**
 * ClassName: ListReqData
 * Author: tandingbo
 * CreateTime: 2017-05-12 14:43
 */
public class DetailReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    
    private Long id;
    
    /**
     * 来源-解决图片地址域名（区分:APP,PC）
     */
    private String source;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
    
}
