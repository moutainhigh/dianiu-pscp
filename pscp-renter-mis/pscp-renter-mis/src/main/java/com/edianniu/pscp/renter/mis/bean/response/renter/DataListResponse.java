package com.edianniu.pscp.renter.mis.bean.response.renter;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.renter.mis.bean.renter.vo.RenterDataVO;
import com.edianniu.pscp.renter.mis.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 2002302)
public class DataListResponse extends BaseResponse{

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

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
