package com.edianniu.pscp.renter.mis.bean.renter;

import java.util.List;

import com.edianniu.pscp.renter.mis.bean.Result;
import com.edianniu.pscp.renter.mis.bean.renter.vo.RenterDataVO;

public class DataListResult extends Result{

	private static final long serialVersionUID = 1L;
	
	private int nextOffset;
	
	private int totalCount;
	
	private boolean hasNext;
	
	private List<RenterDataVO> dataList;

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

	public List<RenterDataVO> getDataList() {
		return dataList;
	}

	public void setDataList(List<RenterDataVO> dataList) {
		this.dataList = dataList;
	}

}
