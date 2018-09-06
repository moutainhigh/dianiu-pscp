package com.edianniu.pscp.cs.bean.firefightingequipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.firefightingequipment.vo.FirefightingEquipmentVO;

/**
 * 消防设施详情
 * @author zhoujianjian
 * @date 2017年11月1日 下午9:29:58
 */
public class DetailsResult extends Result {
	private static final long serialVersionUID = 1L;

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
