package com.edianniu.pscp.renter.mis.bean.renter;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.renter.mis.bean.Result;
import com.edianniu.pscp.renter.mis.bean.renter.vo.RenterVO;

/**
 * 租客列表
 * @author zhoujianjian
 * @date 2018年4月4日 下午3:51:36
 */
public class ListResult extends Result{

	private static final long serialVersionUID = 1L;
	
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
