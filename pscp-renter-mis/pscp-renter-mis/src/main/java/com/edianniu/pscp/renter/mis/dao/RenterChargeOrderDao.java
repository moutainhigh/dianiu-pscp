package com.edianniu.pscp.renter.mis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.renter.mis.bean.renter.NopayInfo;
import com.edianniu.pscp.renter.mis.domain.RenterChargeOrder;

public interface RenterChargeOrderDao extends BaseDao<RenterChargeOrder> {

	NopayInfo getNopayInfo(@Param("id") Long id);

	RenterChargeOrder getByRenterIdAndFromDateAndToDate(
			@Param("renterId") Long renterId,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	int getCountByRenterIdAndFromDateAndToDate(
			@Param("renterId") Long renterId,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	RenterChargeOrder getByOrderId(
			@Param("orderId") String orderId);
	
	List<String> getPrePaymentUnsettledOrders(@Param("limit")int limit);
}
