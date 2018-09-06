package com.edianniu.pscp.mis.bean.user.invitation;

import java.util.List;

import com.edianniu.pscp.mis.bean.Result;

public class ElectricianInvitationListResult extends Result{
	private static final long serialVersionUID = 1L;
	private int nextOffset;
    private int totalCount;
    private boolean hasNext = false;

    private List<ElectricianInvitationInfo> list;

	public int getNextOffset() {
		return nextOffset;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public List<ElectricianInvitationInfo> getList() {
		return list;
	}

	public void setNextOffset(int nextOffset) {
		this.nextOffset = nextOffset;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public void setList(List<ElectricianInvitationInfo> list) {
		this.list = list;
	}
}
