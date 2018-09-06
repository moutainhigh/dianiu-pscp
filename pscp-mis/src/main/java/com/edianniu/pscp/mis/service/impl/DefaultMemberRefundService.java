package com.edianniu.pscp.mis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.mis.bean.pay.MemberRefundStatus;
import com.edianniu.pscp.mis.dao.MemberRefundDao;
import com.edianniu.pscp.mis.domain.MemberRefund;
import com.edianniu.pscp.mis.service.MemberRefundService;
/**
 * DefaultMemberRefundService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月3日 下午4:05:55 
 * @version V1.0
 */
@Service
@Repository("memberRefundService")
public class DefaultMemberRefundService implements MemberRefundService {
    @Autowired
	private MemberRefundDao memberRefundDao;
	@Override
	public List<MemberRefund> getFailList(int limit) {
		
		return memberRefundDao.queryList(limit,MemberRefundStatus.FAIL.getValue());
	}
	@Override
	public List<MemberRefund> getProcessingList(int limit) {
		
		return memberRefundDao.queryList(limit, MemberRefundStatus.PROCESSING.getValue());
	}

	@Override
	public MemberRefund getById(Long id) {
		
		return memberRefundDao.getById(id);
	}

	@Override
	public int update(MemberRefund memberRefund) {
		
		return memberRefundDao.update(memberRefund);
	}

	@Override
	public void save(MemberRefund memberRefund) {
		memberRefundDao.save(memberRefund);
	}

}
