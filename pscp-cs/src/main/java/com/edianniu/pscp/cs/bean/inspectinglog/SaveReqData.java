package com.edianniu.pscp.cs.bean.inspectinglog;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 设备检视日志保存
 * @author zhoujianjian
 * @date 2017年11月1日 下午10:47:39
 */
public class SaveReqData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//用户ID
	private Long uid;
	
	private Long equipmentId;
	
	private Integer type;
	
	private String content;
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
