package com.edianniu.pscp.cs.bean.request.inspectinglog;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 客户设备巡检日志保存
 * @author zhoujianjian
 * @date 2017年11月1日 下午10:42:04
 */
@JSONMessage(messageCode = 1002135)
public class SaveRequest extends TerminalRequest{
	
	private Long equipmentId;
	
	private Integer type;
	
	private String content;

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
