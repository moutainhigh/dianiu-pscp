package com.edianniu.pscp.cs.bean.safetyequipment;

import java.util.List;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.safetyequipment.vo.SafetyEquipmentVO;

/**
 * 配电房安全用具清单
 * @author zhoujianjian
 * @date 2017年10月31日 下午8:34:00
 */
public class ListResult extends Result {

	private static final long serialVersionUID = 1L;
	
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

}
