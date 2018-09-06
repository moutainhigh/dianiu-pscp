package com.edianniu.pscp.cs.bean.engineeringproject;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.engineeringproject.vo.EngineeringProjectVO;

/**
 * 项目列表
 * @author zhoujianjian
 * 2017年9月19日下午2:31:32
 */
public class ListResult extends Result {
	
	private static final long serialVersionUID = 1L;
	
	private int nextOffset;
	private int totalCount;
	private boolean hasNext;
	private List<EngineeringProjectVO> projects;
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
	public List<EngineeringProjectVO> getProjects() {
		return projects;
	}
	public void setProjects(List<EngineeringProjectVO> projects) {
		this.projects = projects;
	}
	
	

}
