package com.edianniu.pscp.mis.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.mis.bean.company.CompanyCustomerStatus;
import com.edianniu.pscp.mis.bean.user.invitation.CompanyInvitationInfo;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.dao.CompanyCustomerDao;
import com.edianniu.pscp.mis.domain.CompanyCustomer;
import com.edianniu.pscp.mis.query.CompanyInvitationQuery;
import com.edianniu.pscp.mis.service.CompanyInvitationService;
/**
 * CompanyInvitationService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月28日 下午12:07:34 
 * @version V1.0
 */
@Service
@Repository("companyInvitationService")
public class DefaultCompanyInvitationService implements
		CompanyInvitationService {
	@Autowired
    private CompanyCustomerDao companyCustomerDao;
	@Override
	public CompanyCustomer getByMobileAndCompanyId(String mobile, Long companyId) {
		
		return companyCustomerDao.getByMobileAndCompanyId(mobile, companyId);
	}
	@Override
	public CompanyCustomer getById(Long id) {
		
		return companyCustomerDao.getById(id);
	}
	@Override
	@Transactional
	public boolean invite(CompanyCustomer companyCustomer) {
		if(companyCustomer==null){
			return false;
		}
		int rs=0;
		if(companyCustomer.getId()!=null){
			rs=companyCustomerDao.update(companyCustomer);
		}
		else{
			companyCustomerDao.save(companyCustomer);
			rs=1;
		}
		return rs>0?true:false;
		
	}
	
	@Override
	@Transactional
	public boolean agree(CompanyCustomer companyCustomer) {
		companyCustomer.setStatus(CompanyCustomerStatus.BOUND.getValue());
		companyCustomer.setInvitationAgreeTime(new Date());
		companyCustomerDao.update(companyCustomer);
		return true;
	}
	@Override
	@Transactional
	public boolean reject(CompanyCustomer companyCustomer) {
		companyCustomer.setStatus(CompanyCustomerStatus.REJECT.getValue());
		companyCustomer.setInvitationRejectTime(new Date());
		companyCustomerDao.update(companyCustomer);
		return true;
	}
	@Override
	public int updateMemberId(Long id, Long memberId,String modifiedUser) {
		
		return companyCustomerDao.updateMemberId(id, memberId,modifiedUser);
	}
	@Override
	public PageResult<CompanyInvitationInfo> list(
			CompanyInvitationQuery companyInvitationQuery) {
		PageResult<CompanyInvitationInfo>  result=new PageResult<CompanyInvitationInfo>();
		int total=companyCustomerDao.queryCompanyInvitationInfoCount(companyInvitationQuery);
	    int nextOffset = 0;
	    if (total > 0) {
	            List<CompanyInvitationInfo> list = companyCustomerDao.queryCompanyInvitationInfoList(companyInvitationQuery);
	            result.setData(list);
	            nextOffset = companyInvitationQuery.getOffset() + list.size();
	            result.setNextOffset(nextOffset);
	    }
	    if (nextOffset > 0 && nextOffset < total) {
	            result.setHasNext(true);
	    }
	    result.setOffset(companyInvitationQuery.getOffset());
	    result.setNextOffset(nextOffset);
	    result.setTotal(total);
	    return result;
	}
	@Override
	public Long getCustomerId(Long memberId, Long companyId) {
		
		return companyCustomerDao.getCustomerIdByMemberIdAndCompanyId(memberId, companyId);
	}

	

}
