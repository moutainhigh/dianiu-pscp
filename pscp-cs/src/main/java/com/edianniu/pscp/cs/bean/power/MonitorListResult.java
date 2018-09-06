package com.edianniu.pscp.cs.bean.power;

import java.util.List;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.vo.MonitorVO;

/**
 * 监控列表
 * @author zhoujianjian
 * @date 2017年12月12日 下午2:11:04
 */
public class MonitorListResult extends Result{

	private static final long serialVersionUID = 1L;
	
	private List<MonitorVO> monitorList;
	
	private int nextOffset;
    private int totalCount;
    private boolean hasNext;

	public List<MonitorVO> getMonitorList() {
		return monitorList;
	}

	public void setMonitorList(List<MonitorVO> monitorList) {
		this.monitorList = monitorList;
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
