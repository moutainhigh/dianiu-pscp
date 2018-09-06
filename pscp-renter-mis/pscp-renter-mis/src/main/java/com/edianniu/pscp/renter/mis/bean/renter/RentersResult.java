package com.edianniu.pscp.renter.mis.bean.renter;

import java.util.List;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.renter.mis.bean.renter.vo.RenterVO;

public class RentersResult extends Result{
	private static final long serialVersionUID = 1L;
	// 所有租客业主关系
	private List<RenterVO> renters;
	public List<RenterVO> getRenters() {
		return renters;
	}
	public void setRenters(List<RenterVO> renters) {
		this.renters = renters;
	}
}
