package com.edianniu.pscp.renter.mis.bean.renter;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.renter.mis.bean.Result;

/**
 * 租客列表
 * @author zhoujianjian
 * @date 2018年4月4日 下午3:51:36
 */
public class RenterMetersResult extends Result{

	private static final long serialVersionUID = 1L;
	
	private int nextOffset;
	
	private int totalCount;
	
	private boolean hasNext;
	
	private List<RenterMeterInfo> renterMeters;

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

	public List<RenterMeterInfo> getRenterMeters() {
		return renterMeters;
	}

	public void setRenterMeters(List<RenterMeterInfo> renterMeters) {
		this.renterMeters = renterMeters;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
