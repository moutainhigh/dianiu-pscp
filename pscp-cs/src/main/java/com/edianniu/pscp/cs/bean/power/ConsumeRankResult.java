package com.edianniu.pscp.cs.bean.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.vo.BuildingVO;

/**
 * 门户：智能监控>监控列表>用能排行
 * @author zhoujianjian
 * @date 2017年12月15日 下午2:06:02
 */
public class ConsumeRankResult extends Result{

	private static final long serialVersionUID = 1L;
	
	private List<BuildingVO> buildings;

	private int nextOffset;
    private int totalCount;
    private boolean hasNext;
	public List<BuildingVO> getBuildings() {
		return buildings;
	}
	public void setBuildings(List<BuildingVO> buildings) {
		this.buildings = buildings;
	}
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
    
    
}
