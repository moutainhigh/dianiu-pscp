package com.edianniu.pscp.renter.mis.bean.response.renter;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.renter.mis.bean.renter.vo.OrderVO;
import com.edianniu.pscp.renter.mis.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 租客账单列表
 * @author zhoujianjian
 * @date 2018年4月8日 下午5:34:54
 */
@JSONMessage(messageCode = 2002304)
public class OrderListResponse extends BaseResponse{

	private int nextOffset;
	
	private boolean hasNext;
	
	private int totalCount;
	// 	当前用量（两位小数）,即当前计费周期的用量
	private String quantityOfNow;
	// 结算日
	private String countDate;
	// 余额（两位小数）
	private String balance;
	// 预估剩余天数
	private int days;
	
	private List<OrderVO> billList;
	
	public int getNextOffset() {
		return nextOffset;
	}

	public void setNextOffset(int nextOffset) {
		this.nextOffset = nextOffset;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getQuantityOfNow() {
		return quantityOfNow;
	}

	public void setQuantityOfNow(String quantityOfNow) {
		this.quantityOfNow = quantityOfNow;
	}

	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public List<OrderVO> getBillList() {
		return billList;
	}

	public void setBillList(List<OrderVO> billList) {
		this.billList = billList;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
