package com.edianniu.pscp.renter.mis.bean.response.renter;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.renter.mis.bean.renter.vo.RenterVO;
import com.edianniu.pscp.renter.mis.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 获取所有租客-房东关系对
 * @author zhoujianjian
 * @date 2018年5月2日 下午7:55:37
 */
@JSONMessage(messageCode = 2002307)
public class RentersResponse extends BaseResponse{
	
	// 所有租客业主关系
	private List<RenterVO> renters;
	public List<RenterVO> getRenters() {
		return renters;
	}
	public void setRenters(List<RenterVO> renters) {
		this.renters = renters;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
