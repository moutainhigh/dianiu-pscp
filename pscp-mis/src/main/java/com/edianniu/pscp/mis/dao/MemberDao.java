package com.edianniu.pscp.mis.dao;

import org.apache.ibatis.annotations.Param;
import com.edianniu.pscp.mis.domain.Member;

public interface MemberDao{
	public Member getByCompanyIdAndMobile(@Param("companyId")Long companyId,@Param("mobile")String mobile);

	public Member getByUid(Long uid);

	public Member getSimpleByMobile(@Param("companyId")Long companyId,@Param("mobile")String mobile);

	public Member getSimpleByUid(Long uid);

	public void saveUser(Member member);
	
	public void updateUser(Member member);

	public void updateUserPwd(Member member);
	
	public void updateUserMobile(Member member);

	public Integer checkExistMobile(@Param("mobile")String mobile);
	//审核会员信息用到
	public void editUser (Member member);
	
	public Member getByMobile(@Param("mobile")String mobile);
	
	public Member getByLoginName(@Param("loginName")String loginName);
	
	public Member getByCompanyName(@Param("companyName")String companyName);
	
	public Member getFacilitatorAdminByCompanyId(@Param("companyId") Long companyId);
	
	public Long getFacilitatorAdminUidByCompanyId(@Param("companyId") Long companyId);

	public Member getCustomerAdminByCompanyId(@Param("companyId") Long companyId);

	public void updateSwitchpwd(Member member);

	

	
	
}
