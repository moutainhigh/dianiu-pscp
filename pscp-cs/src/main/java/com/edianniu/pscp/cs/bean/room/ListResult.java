package com.edianniu.pscp.cs.bean.room;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;

/**
 * 
 * @author zhoujianjian
 * 2017年9月11日下午10:12:48
 */
public class ListResult extends Result {
	private static final long serialVersionUID = 1L;
	
	private int nextOffset;
	private int totalCount;
	private boolean hasNext;
	
	private List<RoomVO> distributionRooms;

	public List<RoomVO> getDistributionRooms() {
		return distributionRooms;
	}

	public void setDistributionRooms(List<RoomVO> distributionRooms) {
		this.distributionRooms = distributionRooms;
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
