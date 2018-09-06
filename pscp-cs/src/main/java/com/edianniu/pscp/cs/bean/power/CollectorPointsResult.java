package com.edianniu.pscp.cs.bean.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.vo.CollectorPointVO;

/**
 * 分页获取监测点数据
 * 门户：功率因数---获取监测点功率因数
 * 门户：峰谷利用---获取监测点用电量
 * @author zhoujianjian
 * @date 2017年12月16日 下午6:44:32
 */
public class CollectorPointsResult extends Result{

	private static final long serialVersionUID = 1L;
	
	private List<CollectorPointVO> collectorPoints;
	
	private int nextOffset;
	private int totalCount;
	private boolean hasNext;
	
	public List<CollectorPointVO> getCollectorPoints() {
		return collectorPoints;
	}
	public void setCollectorPoints(List<CollectorPointVO> collectorPoints) {
		this.collectorPoints = collectorPoints;
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
