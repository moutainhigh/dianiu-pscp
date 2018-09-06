package com.edianniu.pscp.search.support.meter;

import java.util.List;

import com.edianniu.pscp.search.support.Result;

public class AvgListResult<T> extends Result{
private static final long serialVersionUID = 1L;
	
	private List<T> list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
