package com.edianniu.pscp.search.support.meter;

import java.util.List;

import com.edianniu.pscp.search.support.Result;

/**
 * StatListResult
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月6日 下午12:18:47 
 * @version V1.0
 */
public class StatListResult<T> extends Result {
    private static final long serialVersionUID = 284670681106601109L;
    private List<T> list;
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}    
}
