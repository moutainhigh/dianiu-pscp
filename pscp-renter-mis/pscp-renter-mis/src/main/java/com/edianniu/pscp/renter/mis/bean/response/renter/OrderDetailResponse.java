package com.edianniu.pscp.renter.mis.bean.response.renter;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.renter.mis.bean.renter.CountItem;
import com.edianniu.pscp.renter.mis.bean.renter.UseType;
import com.edianniu.pscp.renter.mis.bean.renter.vo.OrderVO;
import com.edianniu.pscp.renter.mis.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 租客账单详情
 * @author zhoujianjian
 * @date 2018年4月8日 下午5:34:54
 */
@JSONMessage(messageCode = 2002305)
public class OrderDetailResponse extends BaseResponse{

	// 计费方式：0:统一单价   1:分时计费
	private Integer subChargeMode;
	// 账单详情
	private OrderVO bill;
	// 计费项目
	private List<CountItem> countItems;
	// 分项用电
	private List<UseType> useTypes;

	public OrderVO getBill() {
		return bill;
	}

	public void setBill(OrderVO bill) {
		this.bill = bill;
	}

	public List<CountItem> getCountItems() {
		return countItems;
	}

	public void setCountItems(List<CountItem> countItems) {
		this.countItems = countItems;
	}

	public List<UseType> getUseTypes() {
		return useTypes;
	}

	public void setUseTypes(List<UseType> useTypes) {
		this.useTypes = useTypes;
	}

	public Integer getSubChargeMode() {
		return subChargeMode;
	}

	public void setSubChargeMode(Integer subChargeMode) {
		this.subChargeMode = subChargeMode;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
