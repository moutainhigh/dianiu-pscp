package com.edianniu.pscp.renter.mis.bean.renter;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.renter.mis.bean.Result;
import com.edianniu.pscp.renter.mis.bean.renter.vo.RenterVO;

/**
 * 门户租客管理--租客详情
 * @author zhoujianjian
 * @date 2018年4月4日 下午3:54:16
 */
public class HomeResult extends Result{

	private static final long serialVersionUID = 1L;
	// 所有租客业主关系
	private List<RenterVO> renters;
	// 当前选择的租客ID
	private Long renterId;
	// 当前选择的租客名称
	private String renterName;
	// 当前选择的租客对应的业主公司ID（指客户公司）
	private Long companyId;
	// 当前选择的租客对应的业主公司名称
	private String companyName;
	
	// 当月分项电费
	private List<UseType> charges;
	// 余额
	private String balance;
	// 当月电费
	private String totalCharge;
	// 预估剩余天数
	private Integer days;
	// 缴费方式  1:预缴费(默认)   2:月结算
	private Integer chargeMode;
	
	public List<UseType> getCharges() {
		return charges;
	}

	public void setCharges(List<UseType> charges) {
		this.charges = charges;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(String totalCharge) {
		this.totalCharge = totalCharge;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Long getRenterId() {
		return renterId;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}

	public List<RenterVO> getRenters() {
		return renters;
	}

	public void setRenters(List<RenterVO> renters) {
		this.renters = renters;
	}

	public String getRenterName() {
		return renterName;
	}

	public void setRenterName(String renterName) {
		this.renterName = renterName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
}
