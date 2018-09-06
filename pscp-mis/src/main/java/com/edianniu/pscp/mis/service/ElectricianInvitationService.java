package com.edianniu.pscp.mis.service;

import java.util.List;

import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.user.invitation.ElectricianInvitationInfo;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.domain.CompanyElectrician;
import com.edianniu.pscp.mis.domain.Electrician;
import com.edianniu.pscp.mis.query.ElectricianInvitationQuery;

/**
 * ElectricianInvitationService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 下午3:19:31 
 * @version V1.0
 */
public interface ElectricianInvitationService {
	public PageResult<ElectricianInvitationInfo> list(ElectricianInvitationQuery electricianInvitationQuery);
	public int updateMemberId(Long id,Long memberId,String modifiedUser);
	public CompanyElectrician getById(Long id);
	public CompanyElectrician getByMobileAndCompanyId(String mobile,Long companyId);
	public CompanyElectrician getByMemberIdAndCompanyId(Long memberId,Long companyId);
	/**
	 * 邀请
	 * @param companyElectrician
	 * @return
	 */
	public boolean invite(CompanyElectrician companyElectrician);
	/**
	 * 同意
	 * @param companyElectrician
	 * @param electrician
	 * @param imgs
	 * @return
	 */
	public boolean agree(CompanyElectrician companyElectrician,Electrician electrician,List<CertificateImgInfo> imgs);
	/**
	 * 拒绝
	 * @param companyElectrician
	 * @return
	 */
	public boolean reject(CompanyElectrician companyElectrician);
	/**
	 * 申请解绑
	 * @param companyElectrician
	 * @return
	 */
	public boolean applyUnBund(CompanyElectrician companyElectrician);
	/**
	 * 同意解绑
	 * @param companyElectrician
	 * @return
	 */
	public boolean agreeUnBund(CompanyElectrician companyElectrician);
	/**
	 * 拒绝解绑
	 * @param companyElectrician
	 * @return
	 */
	public boolean rejectUnBund(CompanyElectrician companyElectrician);
}
