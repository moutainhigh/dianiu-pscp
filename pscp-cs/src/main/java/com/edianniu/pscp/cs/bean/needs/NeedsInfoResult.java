package com.edianniu.pscp.cs.bean.needs;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;

/**
 * 需求详情 门户使用
 */
public class NeedsInfoResult extends Result{

	private static final long serialVersionUID = 1L;
	
	/* 需求信息.*/
    private NeedsVO needsVO;
    /* 响应订单信息.*/
    private NeedsOrderInfo needsOrderInfo;
	
    public NeedsVO getNeedsVO() {
		return needsVO;
	}
	public void setNeedsVO(NeedsVO needsVO) {
		this.needsVO = needsVO;
	}
	public NeedsOrderInfo getNeedsOrderInfo() {
		return needsOrderInfo;
	}
	public void setNeedsOrderInfo(NeedsOrderInfo needsOrderInfo) {
		this.needsOrderInfo = needsOrderInfo;
	}
    
}
