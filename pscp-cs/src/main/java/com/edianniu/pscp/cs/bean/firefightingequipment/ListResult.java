package com.edianniu.pscp.cs.bean.firefightingequipment;

import java.util.List;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.firefightingequipment.vo.FirefightingEquipmentVO;

/**
 * 配电房消防设施
 * @author zhoujianjian
 * @date 2017年11月1日 下午6:58:54
 */
public class ListResult extends Result {

	private static final long serialVersionUID = 1L;
	
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

}
