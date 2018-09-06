package com.edianniu.pscp.cs.bean.response.safetyequipment;

import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import com.edianniu.pscp.cs.bean.safetyequipment.vo.SafetyEquipmentVO;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房安全用具清单
 * @author zhoujianjian
 * @date 2017年10月31日 下午8:27:06
 */
@JSONMessage(messageCode = 2002131)
public class ListResponse extends BaseResponse{
	
	private int nextOffset;
    private int totalCount;
    private boolean hasNext;
    private List<SafetyEquipmentVO> safetyEquipments;

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

	public List<SafetyEquipmentVO> getSafetyEquipments() {
		return safetyEquipments;
	}

	public void setSafetyEquipments(List<SafetyEquipmentVO> safetyEquipments) {
		this.safetyEquipments = safetyEquipments;
	}

	public String toString(){
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
    
}
