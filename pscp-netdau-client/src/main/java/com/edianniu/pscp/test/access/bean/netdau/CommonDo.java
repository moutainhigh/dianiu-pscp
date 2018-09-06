package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CommonDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String building_id;
	private String gateway_id;
	private String type;

	public String getBuilding_id() {
		return building_id;
	}

	public String getGateway_id() {
		return gateway_id;
	}

	public String getType() {
		return type;
	}

	public void setBuilding_id(String building_id) {
		this.building_id = building_id;
	}

	public void setGateway_id(String gateway_id) {
		this.gateway_id = gateway_id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
