package com.edianniu.pscp.cs.bean.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.vo.CollectorPointVO;

/**
 * 门户：智能监控>监控列表>总览>（综合、动力、照明、空调、特殊）
 * 分类获取设备最大负荷和用电量
 * @author zhoujianjian
 * @date 2017年12月14日 上午10:29:12
 */
public class GeneralByTypeResult extends Result{

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
