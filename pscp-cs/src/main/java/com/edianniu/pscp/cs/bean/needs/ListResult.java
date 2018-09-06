package com.edianniu.pscp.cs.bean.needs;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;

/**
 * 需求列表
 * @author zhoujianjian
 * 2017年9月17日下午9:38:50
 */
public class ListResult extends Result {

	private static final long serialVersionUID = 1L;
	
	private int nextOffset;
	private int totalCount;
	private boolean hasNext;
	
	private List<NeedsVO> needsList;

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

	public List<NeedsVO> getNeedsList() {
		return needsList;
	}

	public void setNeedsList(List<NeedsVO> needsList) {
		this.needsList = needsList;
	}
	
	
	

}
