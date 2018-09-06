package com.edianniu.pscp.cs.bean.response.safetyequipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import com.edianniu.pscp.cs.bean.safetyequipment.vo.SafetyEquipmentVO;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房安全用具详情
 * @author zhoujianjian
 * @date 2017年10月31日 下午9:49:42
 */
@JSONMessage(messageCode = 2002133)
public class DetailsResponse extends BaseResponse{
	
	private SafetyEquipmentVO safetyEquipment;

	public SafetyEquipmentVO getSafetyEquipment() {
		return safetyEquipment;
	}

	public void setSafetyEquipment(SafetyEquipmentVO safetyEquipment) {
		this.safetyEquipment = safetyEquipment;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
