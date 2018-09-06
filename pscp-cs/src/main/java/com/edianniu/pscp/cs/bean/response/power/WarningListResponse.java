package com.edianniu.pscp.cs.bean.response.power;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.power.vo.WarningVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 报警列表 
 * @author zhoujianjian
 * @date 2017年12月11日 下午4:14:14
 */
@JSONMessage(messageCode = 2002188)
public final class WarningListResponse extends BaseResponse{
	
	private int nextOffset;
    private int totalCount;
    private boolean hasNext;
	
    private List<WarningVO> warnings;

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

	public List<WarningVO> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<WarningVO> warnings) {
		this.warnings = warnings;
	}

    
}
