package com.edianniu.pscp.portal.bean.vo;

import com.edianniu.pscp.cs.bean.needs.NeedsOrderInfo;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;

/**
 * 需求详情 门户使用
 */
public class NeedsInfoVO{
	
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
