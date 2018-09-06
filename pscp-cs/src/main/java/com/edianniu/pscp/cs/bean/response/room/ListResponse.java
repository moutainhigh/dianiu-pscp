package com.edianniu.pscp.cs.bean.response.room;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 
 * @author zhoujianjian
 * 2017年9月11日下午9:33:09
 */
@JSONMessage(messageCode = 2002120)
public final class ListResponse extends BaseResponse{
	
	private int nextOffset;
    private int totalCount;
    private boolean hasNext;
	
    private List<RoomVO> distributionRooms;

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
    

	public String toString(){
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

	public List<RoomVO> getDistributionRooms() {
		return distributionRooms;
	}

	public void setDistributionRooms(List<RoomVO> distributionRooms) {
		this.distributionRooms = distributionRooms;
	}

}
