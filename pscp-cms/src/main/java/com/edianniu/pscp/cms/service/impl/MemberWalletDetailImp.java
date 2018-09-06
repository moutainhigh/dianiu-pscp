package com.edianniu.pscp.cms.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cms.dao.MemberWalletDetailDao;
import com.edianniu.pscp.cms.dao.MemberWithdrawalsDetailDao;
import com.edianniu.pscp.cms.entity.MemberWalletDetail;
import com.edianniu.pscp.cms.service.MemberWalletDetailService;
@Service("memberWalletDetailService")
public class MemberWalletDetailImp implements MemberWalletDetailService{
	
	@Autowired
	private MemberWalletDetailDao memberWalletDetailDao ;
	
	@Autowired
	private MemberWithdrawalsDetailDao memberWithdrawalsDetailDao ;
	
	@Override
	public MemberWalletDetail getWithdrawalsById(Long id) {
		
		return memberWithdrawalsDetailDao.queryObject(id);
	}
	@Override
	public MemberWalletDetail getById(Long id) {
		return memberWalletDetailDao.queryObject(id);
	}

	@Override
	public List<MemberWalletDetail> queryList(Map<String, Object> map) {
		return memberWalletDetailDao.queryList(map);
	}
	@Override
	public int queryTotal(Map<String, Object> map) {
		return memberWalletDetailDao.queryTotal(map);
	}

	@Override
	public List<MemberWalletDetail> queryWithdrawalsList(Map<String, Object> map) {
		return memberWithdrawalsDetailDao.queryList(map);
	}

	@Override
	public int queryWithdrawalsTotal(Map<String, Object> map) {
		return memberWithdrawalsDetailDao.queryTotal(map);
	}

	

	
	

}
