package com.edianniu.pscp.cs.bean.response.equipment;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.equipment.vo.EquipmentVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房设备列表
 * @author zhoujianjian
 * 2017年9月29日下午4:17:25
 */
@JSONMessage(messageCode = 2002145)
public class ListResponse extends BaseResponse{
	
	private int nextOffset;
    private int totalCount;
    private boolean hasNext;
    
    private List<EquipmentVO> equipments;
    
    public int getNextOffset() {
		return nextOffset;
	}

	public void setNextOffset(int nextOffset) {
		this.nextOffset = nextOffset;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public List<EquipmentVO> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<EquipmentVO> equipments) {
		this.equipments = equipments;
	}

	public String ToString(){
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
    
}
