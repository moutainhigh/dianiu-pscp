package com.edianniu.pscp.cs.bean.equipment;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.equipment.vo.EquipmentVO;

/**
 * 配电房设备详情
 * @author zhoujianjian
 * 2017年9月29日下午9:46:51
 */
public class DetailsResult extends Result {
	private static final long serialVersionUID = 1L;
	
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
