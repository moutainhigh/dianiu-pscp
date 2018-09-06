package com.edianniu.pscp.cs.bean.response.needs;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsViewVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 需求列表    后台使用
 * @author zhoujianjian
 * 2017年9月21日下午11:41:30
 */
@JSONMessage(messageCode = 2002155)
public class NeedsViewListResponse extends BaseResponse{
	
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

	public String ToString(){
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
    
}
