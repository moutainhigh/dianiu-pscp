package com.edianniu.pscp.cs.bean.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.vo.LineVO;

/**
 * 客户线路列表
 * @author zhoujianjian
 * @date 2017年12月7日 下午3:20:49
 */
public class CompanyLinesResult extends Result{

	private static final long serialVersionUID = 1L;
	
	private List<LineVO> lines;

	public List<LineVO> getLines() {
		return lines;
	}

	public void setLines(List<LineVO> lines) {
		this.lines = lines;
	}
	

}
