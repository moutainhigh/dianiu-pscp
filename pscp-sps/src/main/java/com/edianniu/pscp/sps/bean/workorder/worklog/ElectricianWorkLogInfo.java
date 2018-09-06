package com.edianniu.pscp.sps.bean.workorder.worklog;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName: WorkLogResponse
 * Author: tandingbo
 * CreateTime: 2017-05-09 14:08
 */
public class ElectricianWorkLogInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键
     */
    private Long id;

    /**
     * 电工ID
     */
    private Long electricianId;
    /**
     * 电工名称
     */
    private String electricianName;
    /**
     * 电工图像
     */
    private String txImg;
    /**
     * 文本内容
     */
    private String content;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 附件信息
     */
    private List<Map<String, Object>> attachmentList;

    public Long getElectricianId() {
        return electricianId;
    }

    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }

    public String getElectricianName() {
        return electricianName;
    }

    public void setElectricianName(String electricianName) {
        this.electricianName = electricianName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Map<String, Object>> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Map<String, Object>> attachmentList) {
        this.attachmentList = attachmentList;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTxImg() {
		return txImg;
	}

	public void setTxImg(String txImg) {
		this.txImg = txImg;
	}
    
}
