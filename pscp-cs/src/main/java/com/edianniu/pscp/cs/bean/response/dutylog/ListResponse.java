package com.edianniu.pscp.cs.bean.response.dutylog;

import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.dutylog.vo.DutyLogVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房值班日志列表
 * @author zhoujianjian
 * @date 2017年10月30日 下午4:15:37
 */
@JSONMessage(messageCode = 2002144)
public class ListResponse extends BaseResponse{
	
	private int nextOffset;
    private int totalCount;
    private boolean hasNext;
    private List<DutyLogVO> dutyLogs;

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

	public List<DutyLogVO> getDutyLogs() {
		return dutyLogs;
	}

	public void setDutyLogs(List<DutyLogVO> dutyLogs) {
		this.dutyLogs = dutyLogs;
	}

	public String ToString(){
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
    
}
