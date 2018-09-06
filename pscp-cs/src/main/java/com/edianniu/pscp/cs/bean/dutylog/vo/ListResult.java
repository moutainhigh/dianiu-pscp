package com.edianniu.pscp.cs.bean.dutylog.vo;

import java.util.List;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.operationrecord.vo.OperationRecordVO;

/**
 * 配电房操作记录列表
 * @author zhoujianjian
 * @date 2017年10月29日 下午8:27:29
 */
public class ListResult extends Result {

	private static final long serialVersionUID = 1L;
	
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

}
