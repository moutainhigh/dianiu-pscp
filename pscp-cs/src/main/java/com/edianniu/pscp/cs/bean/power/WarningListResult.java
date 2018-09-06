package com.edianniu.pscp.cs.bean.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.vo.WarningVO;

/**
 * 报警列表
 * @author zhoujianjian
 * @date 2017年12月11日 下午4:21:46
 */
public class WarningListResult extends Result {
	private static final long serialVersionUID = 1L;
	
	private int nextOffset;
	private int totalCount;
	private boolean hasNext;
	
	private List<WarningVO> warnings;

	public List<WarningVO> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<WarningVO> warnings) {
		this.warnings = warnings;
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
