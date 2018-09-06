package com.edianniu.pscp.mis.bean.response;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.AreaInfo;

import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 2002058)
public class GetAreasResponse extends BaseResponse {
    private List<AreaInfo>  areas;

    public List<AreaInfo> getAreas() {
		return areas;
	}

	public void setAreas(List<AreaInfo> areas) {
		this.areas = areas;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}


}
