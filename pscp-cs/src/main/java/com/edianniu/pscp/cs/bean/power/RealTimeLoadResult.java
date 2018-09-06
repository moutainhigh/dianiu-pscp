package com.edianniu.pscp.cs.bean.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.vo.RealTimeLoadVO;

/**
 * 实时负荷，门户使用
 * @author zhoujianjian
 * @date 2017年12月12日 下午8:55:27
 */
public class RealTimeLoadResult extends Result{

	private static final long serialVersionUID = 1L;
	
	private String maxLoadOfToday;
	
	private String maxLoadOfLastDay;
	
	private List<RealTimeLoadVO> loadsOfToday;
	
	private List<RealTimeLoadVO> loadsOfLastDay;

	public String getMaxLoadOfToday() {
		return maxLoadOfToday;
	}

	public void setMaxLoadOfToday(String maxLoadOfToday) {
		this.maxLoadOfToday = maxLoadOfToday;
	}

	public String getMaxLoadOfLastDay() {
		return maxLoadOfLastDay;
	}

	public void setMaxLoadOfLastDay(String maxLoadOfLastDay) {
		this.maxLoadOfLastDay = maxLoadOfLastDay;
	}

	public List<RealTimeLoadVO> getLoadsOfToday() {
		return loadsOfToday;
	}

	public void setLoadsOfToday(List<RealTimeLoadVO> loadsOfToday) {
		this.loadsOfToday = loadsOfToday;
	}

	public List<RealTimeLoadVO> getLoadsOfLastDay() {
		return loadsOfLastDay;
	}

	public void setLoadsOfLastDay(List<RealTimeLoadVO> loadsOfLastDay) {
		this.loadsOfLastDay = loadsOfLastDay;
	}
	
	

}
