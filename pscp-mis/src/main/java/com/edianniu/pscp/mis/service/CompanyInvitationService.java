package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.user.invitation.CompanyInvitationInfo;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.domain.CompanyCustomer;
import com.edianniu.pscp.mis.query.CompanyInvitationQuery;

/**
 * CompanyInvitationService
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 下午3:20:00
 * @version V1.0
 */
public interface CompanyInvitationService {
	/**
	 * 获取客户Id
	 * @param memberId
	 * @param companyId
	 * @return
	 */
	public Long getCustomerId(Long memberId,Long companyId);
	public PageResult<CompanyInvitationInfo> list(CompanyInvitationQuery companyInvitationQuery);
	public int updateMemberId(Long id,Long memberId,String modifiedUser);
	/**
	 * 获取企业客户信息
	 * @param id
	 * @return
	 */
	public CompanyCustomer getById(Long id);
	/**
	 * 获取企业客户信息
	 * @param mobile
	 * @param companyId
	 * @return
	 */
	public CompanyCustomer getByMobileAndCompanyId(String mobile, Long companyId);
	/**
	 * 邀请
	 * @param companyCustomer
	 * @return
	 */
	public boolean invite(CompanyCustomer companyCustomer);
	/**
	 * 同意
	 * @param companyCustomer
	 * @return
	 */
	public boolean agree(CompanyCustomer companyCustomer);
	/**
	 * 拒绝
	 * @param companyCustomer
	 * @return
	 */
	public boolean reject(CompanyCustomer companyCustomer);
}
