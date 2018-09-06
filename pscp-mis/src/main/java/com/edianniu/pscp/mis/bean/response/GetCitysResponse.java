package com.edianniu.pscp.mis.bean.response;

import java.util.List;



import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.CityInfo;

import stc.skymobi.bean.json.annotation.JSONMessage;
@JSONMessage(messageCode = 2002057)
public class GetCitysResponse extends BaseResponse{
	
	private List<CityInfo> citys;
	public List<CityInfo> getCitys() {
		return citys;
	}
	public void setCitys(List<CityInfo> citys) {
		this.citys = citys;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
	
	
}
