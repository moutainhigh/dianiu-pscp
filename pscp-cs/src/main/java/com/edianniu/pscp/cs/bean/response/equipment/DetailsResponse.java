package com.edianniu.pscp.cs.bean.response.equipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.equipment.vo.EquipmentVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房设备详情
 * @author zhoujianjian
 * 2017年9月29日下午9:43:24
 */
@JSONMessage(messageCode = 2002147)
public class DetailsResponse extends BaseResponse{
	
	private EquipmentVO equipment;
	
	public EquipmentVO getEquipment() {
		return equipment;
	}

	public void setEquipment(EquipmentVO equipment) {
		this.equipment = equipment;
	}

	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	

}
