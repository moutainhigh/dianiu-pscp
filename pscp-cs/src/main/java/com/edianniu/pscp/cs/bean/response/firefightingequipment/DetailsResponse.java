package com.edianniu.pscp.cs.bean.response.firefightingequipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.firefightingequipment.vo.FirefightingEquipmentVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 消防设施详情
 * @author zhoujianjian
 * @date 2017年11月1日 下午9:21:08
 */
@JSONMessage(messageCode = 2002137)
public class DetailsResponse extends BaseResponse{
	
	private FirefightingEquipmentVO firefightingEquipment;

	public FirefightingEquipmentVO getFirefightingEquipment() {
		return firefightingEquipment;
	}

	public void setFirefightingEquipment(FirefightingEquipmentVO firefightingEquipment) {
		this.firefightingEquipment = firefightingEquipment;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
