package com.edianniu.pscp.cs.bean.response.operationrecord;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.operationrecord.vo.OperationRecordVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房操作记录列表
 * @author zhoujianjian
 * @date 2017年10月29日 下午8:17:38
 */
@JSONMessage(messageCode = 2002149)
public class ListResponse extends BaseResponse{
	
	private int nextOffset;
    private int totalCount;
    private boolean hasNext;
    private List<OperationRecordVO> operations;

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

	public List<OperationRecordVO> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationRecordVO> operations) {
		this.operations = operations;
	}

	public String ToString(){
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
    
}
