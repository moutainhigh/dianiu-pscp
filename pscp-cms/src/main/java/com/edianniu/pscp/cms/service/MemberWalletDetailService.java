package com.edianniu.pscp.cms.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.entity.MemberWalletDetail;


public interface MemberWalletDetailService {
	
	MemberWalletDetail getById(Long id);
	
	MemberWalletDetail getWithdrawalsById(Long id);
	
	List<MemberWalletDetail> queryList(Map<String, Object> map);
	
	List<MemberWalletDetail> queryWithdrawalsList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	int queryWithdrawalsTotal(Map<String, Object> map);
}
