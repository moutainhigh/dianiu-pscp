package com.edianniu.pscp.cs.bean.needs;

import java.io.Serializable;

import com.edianniu.pscp.cs.commons.Constants;

/**
 * 后台需求列表
 * @author zhoujianjian
 * 2017年9月21日下午7:41:47
 */
public class NeedsViewListReqData implements Serializable{
	private static final long serialVersionUID = 1L;

    private Long uid;

    private int offset;
    
    private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
    
    private String status;
    
    private String memberName;
    
    private String loginId;
    
    private String needsName;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getNeedsName() {
		return needsName;
	}

	public void setNeedsName(String needsName) {
		this.needsName = needsName;
	}
    
    
    
}
