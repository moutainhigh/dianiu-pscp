package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 添加客户设备告警
 * @author zhoujianjian
 * @date 2017年12月28日 上午11:07:21
 */
public class WarningSaveReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 客户ID
	private Long uid;
	// 告警类型
	private String warningType;
	// 告警对象
	private String warningObject;
	// 发生时间
	private String occurTime;
	// 告警描述
	private String description;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getWarningType() {
		return warningType;
	}
	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}
	public String getWarningObject() {
		return warningObject;
	}
	public void setWarningObject(String warningObject) {
		this.warningObject = warningObject;
	}
	public String getOccurTime() {
		return occurTime;
	}
	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
