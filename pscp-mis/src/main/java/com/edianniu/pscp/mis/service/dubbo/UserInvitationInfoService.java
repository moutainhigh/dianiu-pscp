package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.user.invitation.AgreeElectricianInvitationReq;
import com.edianniu.pscp.mis.bean.user.invitation.ApplyElectricianUnBindReq;
import com.edianniu.pscp.mis.bean.user.invitation.CompanyInvitationListReq;
import com.edianniu.pscp.mis.bean.user.invitation.CompanyInvitationListResult;
import com.edianniu.pscp.mis.bean.user.invitation.CompanyInvitationReq;
import com.edianniu.pscp.mis.bean.user.invitation.ConfirmCompanyInvitationReq;
import com.edianniu.pscp.mis.bean.user.invitation.ConfirmElectricianUnBindReq;
import com.edianniu.pscp.mis.bean.user.invitation.ElectricianInvitationListReq;
import com.edianniu.pscp.mis.bean.user.invitation.ElectricianInvitationListResult;
import com.edianniu.pscp.mis.bean.user.invitation.ElectricianInvitationReq;
import com.edianniu.pscp.mis.bean.user.invitation.GetElectricianInvitationResult;
import com.edianniu.pscp.mis.bean.user.invitation.RejectElectricianInvitationReq;

/**
 * 用户邀请服务
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午10:34:37
 * @version V1.0
 */
public interface UserInvitationInfoService {
	/**
	 * 查询公司邀请信息
	 * @param companyInvitationListReq
	 * @return
	 */
	public CompanyInvitationListResult list(CompanyInvitationListReq companyInvitationListReq);
	/**
	 * 查询电工邀请信息
	 * @param electricianInvitationListReq
	 * @return
	 */
	public ElectricianInvitationListResult list(ElectricianInvitationListReq electricianInvitationListReq);
	
	/**
	 * 企业邀请
	 * @param companyInvitationReq
	 * @return
	 */
	public Result companyInvitation(
			CompanyInvitationReq companyInvitationReq);
    /**
     * 确认企业邀请
     * @param confirmCompanyInvitationReq
     * @return
     */
	public Result confirmCompanyInvitation(
			ConfirmCompanyInvitationReq confirmCompanyInvitationReq);
	/**
	 * 申请电工解绑
	 * @param applyElectricianUnBindReq
	 * @return
	 */
	public Result applyElectricianUnBind(
			ApplyElectricianUnBindReq applyElectricianUnBindReq);
	/**
	 * 确认电工解绑(同意/拒绝)
	 * @param confirmElectricianUnBindReq
	 * @return
	 */
	public Result confirmElectricianUnBind(
			ConfirmElectricianUnBindReq confirmElectricianUnBindReq);
	/**
	 * 电工邀请
	 * @param electricianInvitationReq
	 * @return
	 */
	public Result electricianInvitation(
			ElectricianInvitationReq electricianInvitationReq);
	/**
	 * 获取电工邀请信息
	 * @param uid
	 * @return
	 */
	public GetElectricianInvitationResult getElectricianInvitation(Long uid);
	/**
	 * 同意电工邀请
	 * @param agreeElectricianInvitationReq
	 * @return
	 */
	public Result agreeElectricianInvitation(
			AgreeElectricianInvitationReq agreeElectricianInvitationReq);
	/**
	 * 拒绝电工邀请
	 * @param rejectElectricianInvitationReq
	 * @return
	 */
	public Result rejectElectricianInvitation(
			RejectElectricianInvitationReq rejectElectricianInvitationReq);
}
