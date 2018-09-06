package com.edianniu.pscp.cs.bean.needs;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Attachment implements Serializable{

	private static final long serialVersionUID = 1L;
	//附件ID
    private Long id;
    //文件
    private String fid;
    //拼接后的fid
    @JSONField(serialize = false)
    private String urlAndFid;
    //排序字段
    private Integer orderNum;
    //类型(1图片，2视频)
    private Integer type;
    /* 是否开放(0:是(默认)；1：否(满足一定条件开放)).*/
    @JSONField(serialize = false)
    private Integer isOpen;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

	public String getUrlAndFid() {
		return urlAndFid;
	}

	public void setUrlAndFid(String urlAndFid) {
		this.urlAndFid = urlAndFid;
	}
    
}
