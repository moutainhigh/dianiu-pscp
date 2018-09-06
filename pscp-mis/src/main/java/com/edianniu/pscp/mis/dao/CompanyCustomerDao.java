package com.edianniu.pscp.mis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.mis.bean.user.invitation.CompanyInvitationInfo;
import com.edianniu.pscp.mis.domain.CompanyCustomer;
import com.edianniu.pscp.mis.query.CompanyInvitationQuery;

/**
 * ClassName: CompanyCustomerDao
 * Author: tandingbo
 * CreateTime: 2017-04-16 15:00
 */
public interface CompanyCustomerDao extends BaseDao<CompanyCustomer>{
	
    CompanyCustomer getById(Long id);
    
    CompanyCustomer getByMobileAndCompanyId(@Param("mobile")String mobile,
  		  @Param("companyId")Long companyId);
    
    Long getCustomerIdByMemberIdAndCompanyId(@Param("memberId")Long memberId,@Param("companyId") Long companyId);
    
    int updateMemberId(@Param("id")Long id,@Param("memberId") Long memberId,@Param("modifiedUser") String modifiedUser);
    
    List<CompanyInvitationInfo> queryCompanyInvitationInfoList(CompanyInvitationQuery companyInvitationQuery);
    
    int queryCompanyInvitationInfoCount(CompanyInvitationQuery companyInvitationQuery);
}
