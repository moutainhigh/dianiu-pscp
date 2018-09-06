package com.edianniu.pscp.cs.bean.response.firefightingequipment;

import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.firefightingequipment.vo.FirefightingEquipmentVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 消防设施列表
 * @author zhoujianjian
 * @date 2017年11月1日 下午6:52:41
 */
@JSONMessage(messageCode = 2002139)
public class ListResponse extends BaseResponse{
	
	private int nextOffset;
    private int totalCount;
    private boolean hasNext;
    private List<FirefightingEquipmentVO> firefightingEquipments;

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

	public List<FirefightingEquipmentVO> getFirefightingEquipments() {
		return firefightingEquipments;
	}

	public void setFirefightingEquipments(List<FirefightingEquipmentVO> firefightingEquipments) {
		this.firefightingEquipments = firefightingEquipments;
	}

	public String toString(){
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
    
}
