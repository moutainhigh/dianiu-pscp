package com.edianniu.pscp.mis.dao;

import java.util.List;

import com.edianniu.pscp.mis.bean.pay.PayList;

public interface ThirdPayListDao {
	public List<PayList> getPayList();
	
	public List<PayList> getPayListDelWalletPay();
}
