package com.edianniu.pscp.renter.mis.bean.response.renter;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.renter.mis.bean.renter.vo.RenterVO;
import com.edianniu.pscp.renter.mis.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 租客列表
 * @author zhoujianjian
 * @date 2018年4月4日 上午9:17:27
 */
@JSONMessage(messageCode = 2002301)
public class ListResponse extends BaseResponse{
	
	private int nextOffset;
	
	private int totalCount;
	
	private boolean hasNext;
	
	private List<RenterVO> renterList;

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
	
	public List<RenterVO> getRenterList() {
		return renterList;
	}

	public void setRenterList(List<RenterVO> renterList) {
		this.renterList = renterList;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
