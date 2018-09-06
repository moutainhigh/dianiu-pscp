package com.edianniu.pscp.renter.mis.bean.renter;

import java.util.List;

import com.edianniu.pscp.renter.mis.bean.Result;
import com.edianniu.pscp.renter.mis.bean.renter.vo.OrderVO;

public class OrderListResult extends Result{

	private static final long serialVersionUID = 1L;
	
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

}
