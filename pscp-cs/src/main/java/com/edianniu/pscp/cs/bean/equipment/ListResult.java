package com.edianniu.pscp.cs.bean.equipment;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.equipment.vo.EquipmentVO;

/**
 * 配电房设备列表
 * @author zhoujianjian
 * 2017年9月29日下午4:25:05
 */
public class ListResult extends Result {

	private static final long serialVersionUID = 1L;
	
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
	

}
