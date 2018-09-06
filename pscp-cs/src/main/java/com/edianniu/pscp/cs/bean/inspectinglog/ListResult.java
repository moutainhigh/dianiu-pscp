package com.edianniu.pscp.cs.bean.inspectinglog;

import java.util.List;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.inspectinglog.vo.InspectingLogVO;

/**
 * 客户设备检视列表
 * @author zhoujianjian
 * @date 2017年11月2日 上午11:58:20
 */
public class ListResult extends Result {

	private static final long serialVersionUID = 1L;
	
	private int nextOffset;
	private int totalCount;
	private boolean hasNext;
	private List<InspectingLogVO> logs;
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
	public List<InspectingLogVO> getLogs() {
		return logs;
	}
	public void setLogs(List<InspectingLogVO> logs) {
		this.logs = logs;
	}
	

}
