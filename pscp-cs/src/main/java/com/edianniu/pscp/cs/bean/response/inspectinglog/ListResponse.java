package com.edianniu.pscp.cs.bean.response.inspectinglog;

import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.inspectinglog.vo.InspectingLogVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 客户设备检视日志列表
 * @author zhoujianjian
 * @date 2017年11月2日 上午11:44:21
 */
@JSONMessage(messageCode = 2002136)
public class ListResponse extends BaseResponse{
	
	private int nextOffset;
    private int totalCount;
    private boolean hasNext;
    private List<InspectingLogVO> logs;

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

	public List<InspectingLogVO> getLogs() {
		return logs;
	}

	public void setLogs(List<InspectingLogVO> logs) {
		this.logs = logs;
	}

	public String toString(){
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
    
}
