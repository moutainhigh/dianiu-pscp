package com.edianniu.pscp.cs.bean.power;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.vo.WarningVO;

/**
 * 报警详情
 * @author zhoujianjian
 * @date 2017年12月11日 下午5:36:16
 */
public class WarningDetailResult extends Result {
	private static final long serialVersionUID = 1L;
	
	private WarningVO warning;

	public WarningVO getWarning() {
		return warning;
	}

	public void setWarning(WarningVO warning) {
		this.warning = warning;
	}
	
}
