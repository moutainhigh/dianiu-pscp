package com.edianniu.pscp.cs.bean.safetyequipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.safetyequipment.vo.SafetyEquipmentVO;

/**
 * 配电房安全用具详情
 * @author zhoujianjian
 * @date 2017年10月31日 下午9:57:27
 */
public class DetailsResult extends Result {
	private static final long serialVersionUID = 1L;
	
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
