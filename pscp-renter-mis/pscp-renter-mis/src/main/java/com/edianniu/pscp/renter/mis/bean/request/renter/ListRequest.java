package com.edianniu.pscp.renter.mis.bean.request.renter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.renter.mis.bean.request.TerminalRequest;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 租客列表
 * @author zhoujianjian
 * @date 2018年4月4日 上午9:17:09
 */
@JSONMessage(messageCode = 1002301)
public class ListRequest extends TerminalRequest{
	
	private Integer offset;
	
	// 1.预欠费租客列表     2.待缴费租客列表    3.所有租客列表
	private Integer type;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}

}
