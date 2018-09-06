package com.edianniu.pscp.cs.bean.needs;

import java.util.List;

import com.edianniu.pscp.cs.bean.needs.vo.NeedsViewVO;
import com.edianniu.pscp.sps.bean.Result;

public class NeedsViewListResult extends Result {

	private static final long serialVersionUID = 1L;
	
	private int nextOffset;
	private int totalCount;
	private boolean hasNext;
	
	private List<NeedsViewVO> needsViewVOList;

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

	public List<NeedsViewVO> getNeedsViewVOList() {
		return needsViewVOList;
	}

	public void setNeedsViewVOList(List<NeedsViewVO> needsViewVOList) {
		this.needsViewVOList = needsViewVOList;
	}


}
