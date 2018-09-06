package com.edianniu.pscp.mis.service.dubbo.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.DefaultResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.company.CompanyAuthStatus;
import com.edianniu.pscp.mis.bean.company.CompanyCustomerStatus;
import com.edianniu.pscp.mis.bean.company.CompanyElectricianStatus;
import com.edianniu.pscp.mis.bean.company.CompanyMemberType;
import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianAuthStatus;
import com.edianniu.pscp.mis.bean.electricianworkorder.ElectricianWorkOrderStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.user.invitation.*;
import com.edianniu.pscp.mis.commons.CacheKey;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.Company;
import com.edianniu.pscp.mis.domain.CompanyCustomer;
import com.edianniu.pscp.mis.domain.CompanyElectrician;
import com.edianniu.pscp.mis.domain.Electrician;
import com.edianniu.pscp.mis.query.CompanyInvitationQuery;
import com.edianniu.pscp.mis.query.ElectricianInvitationQuery;
import com.edianniu.pscp.mis.service.CompanyInvitationService;
import com.edianniu.pscp.mis.service.CompanyService;
import com.edianniu.pscp.mis.service.ElectricianInvitationService;
import com.edianniu.pscp.mis.service.ElectricianService;
import com.edianniu.pscp.mis.service.ElectricianWorkOrderService;
import com.edianniu.pscp.mis.service.UserService;
import com.edianniu.pscp.mis.service.dubbo.UserInvitationInfoService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.IDCardUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import stc.skymobi.cache.redis.JedisUtil;

import java.util.*;

/**
 * 用户邀请服务
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:06:22
 * @version V1.0
 */
@Service
@Repository("userInvitationInfoService")
public class UserInvitationInfoServiceImpl implements UserInvitationInfoService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserInvitationInfoServiceImpl.class);
	@Autowired
	private ElectricianInvitationService electricianInvitationService;// 电工邀请服务
	@Autowired
	private CompanyInvitationService companyInvitationService;// 企业邀请服务
	@Autowired
	private ElectricianService electricianService;
	@Autowired
	private ElectricianWorkOrderService electricianWorkOrderService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageInfoService messageInfoService;
	@Autowired
	private JedisUtil jedisUtil;

	/**
	 * 企业电工信息表(pscp_company_electrician) 企业客户信息表(pscp_company_customer)
	 */
	@Override
	public Result companyInvitation(CompanyInvitationReq companyInvitationReq) {
		// 服务商发起邀请
		// 不是服务商提醒
		// 手机号码未注册 怎么处理
		// 手机号码已注册 怎么处理
		// 服务商只能邀请非注册用户，普通用户，客户，或者客户认证中的普通用户；但是不能邀请企业电工，社会电工，以及电工认证中的普通用户，以及服务商认证中的普通用户
		// 消息推送
		// 同一个手机号码对应同一个
		Result result = new DefaultResult();
		Long uid = companyInvitationReq.getUid();
		String mobile = companyInvitationReq.getMobile();
		String companyName = companyInvitationReq.getCompanyName();
		try {
			if (uid == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不能为空");
				return result;
			}
			UserInfo userInfo = userService.getUserInfo(uid);
			if (userInfo == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不存在");
				return result;
			}
			if (!userInfo.isFacilitator()) {
				result.setResultCode(ResultCode.ERROR_401);
				result.setResultMessage("没有权限操作");
				return result;
			}
			Company company = companyService.getById(userInfo.getCompanyId());
			if(company==null){
				result.setResultCode(ResultCode.ERROR_401);
				result.setResultMessage("没有权限操作");
				return result;
			}
			String name=company.getName();
			if (StringUtils.isBlank(mobile)) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("mobile 不能为空");
				return result;
			}
			if (!BizUtils.checkMobile(mobile)) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("mobile 格式不正确");
				return result;
			}
			if (StringUtils.isNoneBlank(companyName)) {
				if (!BizUtils.checkLength(companyName, 128)) {
					result.setResultCode(ResultCode.ERROR_203);
					result.setResultMessage("companyName 128个字节以内");
					return result;
				}
			}
			CompanyCustomer companyCustomer = companyInvitationService
					.getByMobileAndCompanyId(mobile, userInfo.getCompanyId());
			if (companyCustomer != null) {
				if (companyCustomer.getStatus() == CompanyCustomerStatus.BOUND
						.getValue()) {// 已绑定
					result.setResultCode(ResultCode.ERROR_402);
					result.setResultMessage("已邀请了，请勿重复邀请");
					return result;
				}
				if (companyCustomer.getStatus() == CompanyCustomerStatus.INVITE
						.getValue()) {
					result.setResultCode(ResultCode.ERROR_402);
					result.setResultMessage("已邀请了，请勿重复邀请");
					return result;
				}
			}

			UserInfo invitationUser = userService.getUserInfoByMobile(mobile);
			if (invitationUser != null) {// 已存在会员
				if (invitationUser.isElectrician()) {// 电工
					if (invitationUser.getCompanyId().equals(
							userInfo.getCompanyId())) {
						result.setResultCode(ResultCode.ERROR_402);
						result.setResultMessage("您已经邀请过了");
						return result;
					} else {
						result.setResultCode(ResultCode.ERROR_402);
						result.setResultMessage("您来迟一步，已被其他企业邀请了");
						return result;
					}
				}
				if (invitationUser.isFacilitator()) {
					result.setResultCode(ResultCode.ERROR_402);
					result.setResultMessage("您来迟一步，已被其他企业邀请了");
					return result;
				}
				if (invitationUser.isNormalMember()) {
					Electrician electrician = electricianService
							.getByUid(invitationUser.getUid());
					if (electrician != null
							&& electrician.getStatus() == ElectricianAuthStatus.AUTHING
									.getValue()) {// 电工认证中，无法邀请
						result.setResultCode(ResultCode.ERROR_402);
						result.setResultMessage("您来迟一步，已被其他企业邀请了");
						return result;
					}
					Company company2 = companyService.getByMemberId(invitationUser
							.getUid());
					if (company2 != null
							&& company2.getStatus() == CompanyAuthStatus.AUTHING
									.getValue()
							&& CompanyMemberType.parse(company2.getMemberType())
									.equals(CompanyMemberType.FACILITATOR)) {// 服务商认证中，无法邀请
						result.setResultCode(ResultCode.ERROR_402);
						result.setResultMessage("您来迟一步，已被其他企业邀请了");
						return result;
					}
				}
			}
			if (companyCustomer == null) {
				companyCustomer = new CompanyCustomer();
			}
			companyCustomer.setMobile(mobile);
			companyCustomer.setMemberId(invitationUser!=null?invitationUser.getUid():0L);
			companyCustomer.setStatus(CompanyCustomerStatus.INVITE.getValue());
			companyCustomer.setCpName(companyName);
			companyCustomer.setCompanyId(userInfo.getCompanyId());
			companyCustomer.setInvitationTime(new Date());
			companyCustomer.setCreateUser(userInfo.getLoginName());
			long rs=jedisUtil.setnx(CacheKey.CACHE_KEY_COMPANY_INVITATION+company.getId()+CacheKey.SPLIT+mobile, mobile, 500L);
			if(rs==0){
				result.setResultCode(ResultCode.ERROR_403);
				result.setResultMessage("正在邀请中，请勿重复操作");
				return result;
			}
			if (!companyInvitationService.invite(companyCustomer)) {
				result.setResultCode(ResultCode.ERROR_500);
				result.setResultMessage("系统异常");
				return result;
			}
			Long invitationId=companyCustomer.getId();
			if(invitationUser==null){//未注册用户，只发送短信，并且将邀请信息保存在缓存中，用户注册登录后，立马PUSH消息给用户
				//给被邀请者PUSH短信和消息
				Map<String,String> params=new HashMap<String,String>();
				params.put("name", name);
				messageInfoService.sendSmsMessage(null, mobile, MessageId.COMPANY_INVITATION, params);
				//给被邀请者PUSH短信和消息
				//'invitationId':invitationId,'mobile':mobile,'name':companyName,type:1/2 
				InvitationInfo invitationInfo=new InvitationInfo();
				invitationInfo.setInvitationId(invitationId);
				invitationInfo.setMobile(mobile);
				invitationInfo.setName(name);
				invitationInfo.setType(2);
				setOffilneMessage(invitationInfo);
			}
			else{
				
				Map<String,String> params=new HashMap<String,String>();
				params.put("name", name);
				Map<String,String> exts=new HashMap<String,String>();
				exts.put("invitationId", ""+invitationId);
				exts.put("actionType", "none");
				messageInfoService.sendSmsAndPushMessage(invitationUser.getUid(), invitationUser.getMobile(), MessageId.COMPANY_INVITATION, params,exts);
			}
			//给自己发送短信和消息
			Map<String,String> params2=new HashMap<String,String>();
			params2.put("name", StringUtils.isNotBlank(companyName)?companyName:BizUtils.getTailMobile(mobile));//手机号码特殊处理
			messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(), MessageId.COMPANY_INVITATION_NOTIFY, params2);
			
		} catch (Exception e) {
			logger.error("companyInvitation:{}", e);
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			return result;
		}
		return result;
	}
	/**
	 * 设置离线消息
	 * 主要是未注册用户消息
	 * @param invitationInfo
	 */
	private void setOffilneMessage(InvitationInfo invitationInfo) {
		List<InvitationInfo> list=null;
		String content=jedisUtil.get("invitation#"+invitationInfo.getMobile());
		if(StringUtils.isBlank(content)){
			list=new ArrayList<InvitationInfo>();
		}
		else{
			list=JSON.parseArray(content, InvitationInfo.class);
		}
		list.add(invitationInfo);
		jedisUtil.set("invitation#"+invitationInfo.getMobile(), JSON.toJSONString(list));
	}

	@Override
	public Result confirmCompanyInvitation(
			ConfirmCompanyInvitationReq confirmCompanyInvitationReq) {
		// 客户确认邀请，客户需要认证
		// 消息推送
		Result result = new DefaultResult();
		Long uid=confirmCompanyInvitationReq.getUid();
		Long invitationId=confirmCompanyInvitationReq.getInvitationId();
		String actionType=confirmCompanyInvitationReq.getActionType();
		try {
			if (uid == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不能为空");
				return result;
			}
			UserInfo userInfo = userService.getUserInfo(uid);
			if (userInfo == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不存在");
				return result;
			}
			if (invitationId == null) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("invitationId 不能为空");
				return result;
			}
			CompanyCustomer companyCustomer = companyInvitationService
					.getById(invitationId);
			if (companyCustomer == null
					|| !companyCustomer.getMemberId().equals(uid)) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("邀请信息不存在");
				return result;
			}
			if(StringUtils.isBlank(actionType)){
				result.setResultCode(ResultCode.ERROR_203);
				result.setResultMessage("actionType不能为空");
				return result;
			}
			if(!(actionType.equals("agree")||actionType.equals("reject"))){
				result.setResultCode(ResultCode.ERROR_203);
				result.setResultMessage("actionType只能是agree或者reject");
				return result;
			}
			if(actionType.equals("agree")&&!userInfo.isCustomer()){
				result.setResultCode(ResultCode.UNAUTHORIZED_ERROR);
				result.setResultMessage("不是企业用户，先进行企业认证");
				return result;
			}
			if (companyCustomer.getStatus() != CompanyElectricianStatus.INVITE
					.getValue()) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("邀请信息不存在");
				return result;
			}
			Company company=companyService.getByMemberId(companyCustomer.getMemberId());
			UserInfo reveiveUser=userService.getFacilitatorAdmin(companyCustomer.getCompanyId());
			Company receiverCompany=companyService.getById(companyCustomer.getCompanyId());
			long rs=jedisUtil.setnx(CacheKey.CACHE_KEY_CONFIRM_COMPANY_INVITATION+CacheKey.SPLIT+companyCustomer.getId(), String.valueOf(companyCustomer.getId()), 500L);
			if(rs==0){
				result.setResultCode(ResultCode.ERROR_403);
				result.setResultMessage("正在操作中，请勿重复操作");
				return result;
			}
			if(actionType.equals("agree")){
				companyCustomer.setCpName(company.getName());
				companyCustomer.setCpAddress(company.getAddress());
				companyCustomer.setCpContact(company.getContacts());
				companyCustomer.setCpContactMobile(company.getMobile());
				companyCustomer.setCpPhone(company.getPhone());
				if(companyInvitationService.agree(companyCustomer)){
					//推送给邀请者(服务商)，xxx(客户)已经同意您的邀请
					Map<String,String> params=new HashMap<>();
					params.put("name", company.getName());
					messageInfoService.sendSmsAndPushMessage(reveiveUser.getUid(), reveiveUser.getMobile(),
							MessageId.AGREE_COMPANY_INVITATION_NOTIFY, params);
					//推送给自己，您已经同意xxx(服务商name)的邀请；
					
					Map<String,String> params2=new HashMap<>();
					params2.put("name", receiverCompany.getName());
					Map<String,String> exts=new HashMap<>();
					exts.put("actionType", actionType);
					exts.put("invitationId", ""+companyCustomer.getId());
					messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(), 
							MessageId.AGREE_COMPANY_INVITATION, params2, exts);
				}
				else{
					result.setResultCode(ResultCode.ERROR_500);
					result.setResultMessage("系统异常");
				}
			}
			else if(actionType.equals("reject")){
				if(companyInvitationService.reject(companyCustomer)){
					//推送给邀请者(服务商)，xxx(客户)已经拒绝您的邀请
					Map<String,String> params=new HashMap<>();
					String name = companyCustomer.getCpName();
					if (company != null && StringUtils.isNotBlank(company.getName())) {
                        name= company.getName();
                    }
					params.put("name", name);
					messageInfoService.sendSmsAndPushMessage(reveiveUser.getUid(), reveiveUser.getMobile(),
							MessageId.REJECT_COMPANY_INVITATION_NOTIFY, params);
					//推送给自己，您已经拒绝xxx(服务商name)的邀请；
					Map<String,String> params2=new HashMap<>();
					params2.put("name", receiverCompany.getName());
					Map<String,String> exts=new HashMap<>();
					exts.put("actionType", actionType);
					exts.put("invitationId", ""+companyCustomer.getId());
					messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(), 
							MessageId.REJECT_COMPANY_INVITATION, params2, exts);
				}	
				else{
					result.setResultCode(ResultCode.ERROR_500);
					result.setResultMessage("系统异常");
				}
				
			}

		} catch (Exception e) {
			logger.error("confirmCompanyInvitation:{}", e);
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			return result;
		}
		return result;
	}

	@Override
	public Result applyElectricianUnBind(
			ApplyElectricianUnBindReq applyElectricianUnBindReq) {
		// 电工或者服务商发起解绑申请
		// 消息推送
		Result result = new DefaultResult();
		Long uid=applyElectricianUnBindReq.getUid();//发起者
		Long invitationId=applyElectricianUnBindReq.getInvitationId();
		try {
			if (uid == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不能为空");
				return result;
			}
			UserInfo userInfo = userService.getUserInfo(uid);
			if (userInfo == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不存在");
				return result;
			}
			if(!(userInfo.isCompanyElectrician()||userInfo.isFacilitator())){
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("没有权限操作");
				return result;
			}
			if (invitationId == null) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("invitationId 不能为空");
				return result;
			}
			
			CompanyElectrician companyElectrician = electricianInvitationService
					.getById(invitationId);
			if (companyElectrician == null) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("邀请信息不存在");
				return result;
			}
			Long adminUid=userService.getFacilitatorAdminUid(companyElectrician.getCompanyId());
			if(companyElectrician.getMemberId().compareTo(adminUid)==0){//主帐号无法申请解绑
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("邀请信息不存在");
				return result;
			}
			boolean isMy=companyElectrician.getCompanyId().equals(userInfo.getCompanyId());
			if(!isMy){
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("无权限操作");
				return result;
			}
			if(companyElectrician.getStatus()!=CompanyElectricianStatus.BOUND.getValue()){
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("未绑定，无法申请解绑");
				return result;
			}
			boolean isElectrician=false;
			//暂时先这样处理，目前因为服务商APP没有申请解绑的功能，目前http接口只支持电工APP调用
			//dubbo接口支持门户和http接口调用
			if(StringUtils.isBlank(applyElectricianUnBindReq.getAppType())){//服务商-门户调用
				isElectrician=false;
				if(!userInfo.isFacilitator()){
					result.setResultCode(ResultCode.ERROR_202);
					result.setResultMessage("无权限操作");
					return result;
				}
				if(userInfo.getUid().compareTo(companyElectrician.getMemberId())==0){//子帐号不能解绑自己
					result.setResultCode(ResultCode.ERROR_202);
					result.setResultMessage("无权限操作");
					return result;
				}
			}
			else if(applyElectricianUnBindReq.getAppType().equals("electrician")){//电工-电工APP调用
				isElectrician=true;
				if(!userInfo.isCompanyElectrician()){
					result.setResultCode(ResultCode.ERROR_202);
					result.setResultMessage("无权限操作");
					return result;
				}
			}
			
			int statuss[]={
					ElectricianWorkOrderStatus.UNCONFIRMED.getValue(),
					ElectricianWorkOrderStatus.CONFIRMED.getValue(),
					ElectricianWorkOrderStatus.EXECUTING.getValue()
			};
			int count=electricianWorkOrderService.getTotalCount(companyElectrician.getMemberId(),statuss);
			if(count>0){
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("有未完成工单，无法申请解绑");
				return result;
			}
			
			companyElectrician.setApplyUnbundTime(new Date());
			if(isElectrician){
				companyElectrician.setStatus(CompanyElectricianStatus.ELECTRICIAN_APPLY_UNBUND.getValue());
			}
			else{
				companyElectrician.setStatus(CompanyElectricianStatus.COMPANY_APPLY_UNBUND.getValue());
			}
			companyElectrician.setApplyUnbundUser(userInfo.getUid());
			long rs=jedisUtil.setnx(CacheKey.CACHE_KEY_APPLY_ELECTRICIAN_UNBIND+companyElectrician.getId(), String.valueOf(companyElectrician.getId()), 500L);
			if(rs==0){
				result.setResultCode(ResultCode.ERROR_403);
				result.setResultMessage("正在操作中，请勿重复操作");
				return result;
			}
			if(!electricianInvitationService.applyUnBund(companyElectrician)){
				result.setResultCode(ResultCode.ERROR_500);
				result.setResultMessage("系统异常");
				return result;
			}
			String reveiverName="";//接收者
			String opName="";//操作者
			//推送给申请解绑者
			UserInfo reveiverUser=null;
			if(!isElectrician){//发起人是服务商
				opName=companyService.getById(userInfo.getCompanyId()).getName();
				reveiverName=electricianService.getByUid(companyElectrician.getMemberId()).getUserName();
				reveiverUser=userService.getUserInfo(companyElectrician.getMemberId());
			}
			else{//发起人是电工
				opName=electricianService.getByUid(userInfo.getUid()).getUserName();
				reveiverName=companyService.getById(userInfo.getCompanyId()).getName();
				reveiverUser=userService.getFacilitatorAdmin(userInfo.getCompanyId());
			}
			//推送给自己，我给'谁谁'申请解除绑定关系，耐心等待对方响应；
			Map<String,String> params=new HashMap<>();
			params.put("name", reveiverName);
			messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(),
					MessageId.APPLY_ELECTRICIAN_UNBUND_NOTIFY, params);
			//推送给接收者,'谁谁'给我申请解除绑定关系，点击同意/取消确认；
			Map<String,String> params2=new HashMap<>();
			params2.put("name", opName);
			Map<String,String> exts=new HashMap<>();
			exts.put("actionType", "none");
			exts.put("invitationId", ""+companyElectrician.getId());
			messageInfoService.sendSmsAndPushMessage(reveiverUser.getUid(), reveiverUser.getMobile(), 
					MessageId.APPLY_ELECTRICIAN_UNBUND, params2, exts);
		} catch (Exception e) {
			logger.error("applyElectricianUnBund:{}", e);
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			return result;
		}
		return result;
	}

	@Override
	public Result confirmElectricianUnBind(
			ConfirmElectricianUnBindReq confirmElectricianUnBindReq) {
		// 电工或者服务商确认解绑申请
		// 消息推送
		Result result = new DefaultResult();
		Long uid=confirmElectricianUnBindReq.getUid();
		Long invitationId=confirmElectricianUnBindReq.getInvitationId();
		String actionType=confirmElectricianUnBindReq.getActionType();
		try {
			if (uid == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不能为空");
				return result;
			}
			UserInfo userInfo = userService.getUserInfo(uid);
			if (userInfo == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不存在");
				return result;
			}
			if(!(userInfo.isCompanyElectrician()||userInfo.isFacilitator())){
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("没有权限操作");
				return result;
			}
			if (invitationId == null) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("invitationId 不能为空");
				return result;
			}
			if(StringUtils.isBlank(actionType)){
				result.setResultCode(ResultCode.ERROR_203);
				result.setResultMessage("actionType不能为空");
				return result;
			}
			if(!(actionType.equals("agree")||actionType.equals("reject"))){
				result.setResultCode(ResultCode.ERROR_203);
				result.setResultMessage("actionType只能是agree或者reject");
				return result;
			}
			CompanyElectrician companyElectrician = electricianInvitationService
					.getById(invitationId);
			if (companyElectrician == null) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("邀请信息不存在");
				return result;
			}
			boolean isMy=companyElectrician.getCompanyId().equals(userInfo.getCompanyId());
			if(!isMy){
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("邀请信息不存在");
				return result;
			}
			boolean isElectrician=companyElectrician.getMemberId().equals(uid)&&
					companyElectrician.getCompanyId().equals(userInfo.getCompanyId());
			
			if(!(companyElectrician.getStatus()==CompanyElectricianStatus.COMPANY_APPLY_UNBUND.getValue()||
					companyElectrician.getStatus()==CompanyElectricianStatus.ELECTRICIAN_APPLY_UNBUND.getValue())){
				result.setResultCode(ResultCode.ERROR_401);
				result.setResultMessage("未申请解绑，无法确认解绑");
				return result;
			}
			if(isElectrician){
				if(companyElectrician.getStatus()!=CompanyElectricianStatus.COMPANY_APPLY_UNBUND.getValue()){
					result.setResultCode(ResultCode.ERROR_401);
					result.setResultMessage("您没有权限操作");
					return result;
				}
			}
			else{
				if(companyElectrician.getStatus()!=CompanyElectricianStatus.ELECTRICIAN_APPLY_UNBUND.getValue()){
					result.setResultCode(ResultCode.ERROR_401);
					result.setResultMessage("您没有权限操作");
					return result;
				}
			}
			if(companyElectrician.getApplyUnbundUser().equals(uid)){
				result.setResultCode(ResultCode.ERROR_401);
				result.setResultMessage("您没有权限操作");
				return result;
			}
			long rs=jedisUtil.setnx(CacheKey.CACHE_KEY_CONFIRM_ELECTRICIAN_UNBIND+companyElectrician.getId(), String.valueOf(companyElectrician.getId()), 500L);
			if(rs==0){
				result.setResultCode(ResultCode.ERROR_403);
				result.setResultMessage("正在操作中，请勿重复操作");
				return result;
			}
			if(actionType.equals("agree")){
				
				UserInfo senderUser=userService.getUserInfo(companyElectrician.getApplyUnbundUser());
				boolean isFacilitatorApply=false;
				if(companyElectrician.getStatus()==CompanyElectricianStatus.COMPANY_APPLY_UNBUND.getValue()){
					//senderUser=服务商
					isFacilitatorApply=true;
				}
				companyElectrician.setApplyUnbundAgreeTime(new Date());
				companyElectrician.setStatus(CompanyElectricianStatus.UNBUNDED.getValue());
				if(!electricianInvitationService.agreeUnBund(companyElectrician)){
					result.setResultCode(ResultCode.ERROR_500);
					result.setResultMessage("系统异常");
					return result;
				}
				String senderName="";//发起者
				String opName="";//操作者
				if(isFacilitatorApply){//服务商申请，
					senderName=companyService.getById(userInfo.getCompanyId()).getName();
					opName=electricianService.getByUid(userInfo.getUid()).getUserName();
				}
				else{
					senderName=electricianService.getByUid(senderUser.getUid()).getUserName();
					opName=companyService.getById(userInfo.getCompanyId()).getName();
				}
				//推送给申请解绑者{name}已同意您的解除关系申请。
				Map<String,String> params=new HashMap<>();
				params.put("name", opName);
				messageInfoService.sendSmsAndPushMessage(senderUser.getUid(), senderUser.getMobile(),
						MessageId.AGREE_ELECTRICIAN_UNBUND_NOTIFY, params);
				//推送给自己-操作者
				//推送给自己(您已经同意{name}的解除关系申请。)
				Map<String,String> params2=new HashMap<>();
				params2.put("name", senderName);
				Map<String,String> exts=new HashMap<>();
				exts.put("actionType", "agree");
				exts.put("invitationId", ""+companyElectrician.getId());
				messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(), 
						MessageId.AGREE_ELECTRICIAN_UNBUND, params2, exts);
				
			}
			else if(actionType.equals("reject")){
				companyElectrician.setApplyUnbundRejectTime(new Date());
				companyElectrician.setStatus(CompanyElectricianStatus.BOUND.getValue());
				if(!electricianInvitationService.rejectUnBund(companyElectrician)){
					result.setResultCode(ResultCode.ERROR_500);
					result.setResultMessage("系统异常");
					return result;
				}
				String senderName="";//发起者
				String opName="";//操作者
				
				UserInfo senderUser=userService.getUserInfo(companyElectrician.getApplyUnbundUser());
				if(!isElectrician){
					senderName=companyService.getById(senderUser.getCompanyId()).getName();
				}
				else{
					senderName=electricianService.getByUid(senderUser.getUid()).getUserName();
				}
				if(!isElectrician){
					opName=companyService.getById(senderUser.getCompanyId()).getName();
				}
				else{
					opName=electricianService.getByUid(senderUser.getUid()).getUserName();
				}
				//推送给申请解绑者{name}已拒绝您的邀请。
				Map<String,String> params=new HashMap<>();
				params.put("name", opName);
				messageInfoService.sendSmsAndPushMessage(senderUser.getUid(), senderUser.getMobile(),
						MessageId.REJECT_ELECTRICIAN_UNBUND_NOTIFY, params);
				//推送给自己(您已经拒绝{name}的解除关系申请。)
				Map<String,String> params2=new HashMap<>();
				params2.put("name", senderName);
				Map<String,String> exts=new HashMap<>();
				exts.put("actionType", "reject");
				exts.put("invitationId", ""+companyElectrician.getId());
				messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(), 
						MessageId.REJECT_ELECTRICIAN_UNBUND, params2, exts);
			}
			
			// 清除 token
			jedisUtil.del(CacheKey.CACHE_KEY_USER_LOGININFO + companyElectrician.getMemberId());
		} catch (Exception e) {
			logger.error("confirmElectricianUnBund:{}", e);
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			return result;
		}
		return result;
	}

	@Override
	public Result electricianInvitation(
			ElectricianInvitationReq electricianInvitationReq) {
		// 服务商邀请电工
		// 消息推送
		Result result = new DefaultResult();
		Long uid = electricianInvitationReq.getUid();
		String mobile = electricianInvitationReq.getMobile();
		String userName = electricianInvitationReq.getUserName();
		try {

			if (uid == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不能为空");
				return result;
			}
			UserInfo userInfo = userService.getUserInfo(uid);
			if (userInfo == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不存在");
				return result;
			}
			if (!userInfo.isFacilitator()) {
				result.setResultCode(ResultCode.ERROR_401);
				result.setResultMessage("没有权限操作");
				return result;
			}
			Company company = companyService.getById(userInfo.getCompanyId());
			if(company==null){
				result.setResultCode(ResultCode.ERROR_401);
				result.setResultMessage("没有权限操作");
				return result;
			}
			String companyName=company.getName();
			if (StringUtils.isBlank(mobile)) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("mobile 不能为空");
				return result;
			}
			if (!BizUtils.checkMobile(mobile)) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("mobile 格式不正确");
				return result;
			}
			if (StringUtils.isNoneBlank(userName)) {
				if (!BizUtils.checkLength(userName, 32)) {
					result.setResultCode(ResultCode.ERROR_203);
					result.setResultMessage("userName 32个字节以内");
					return result;
				}
			}
			CompanyElectrician companyElectrician = electricianInvitationService
					.getByMobileAndCompanyId(mobile, userInfo.getCompanyId());
			if (companyElectrician != null) {
				if (companyElectrician.getStatus() == CompanyElectricianStatus.BOUND
						.getValue()
						|| companyElectrician.getStatus() == CompanyElectricianStatus.INVITE
								.getValue()
						|| companyElectrician.getStatus() == CompanyElectricianStatus.COMPANY_APPLY_UNBUND
								.getValue()
						|| companyElectrician.getStatus() == CompanyElectricianStatus.ELECTRICIAN_APPLY_UNBUND
								.getValue()) {
					// 已邀请，已绑定,企业申请解绑，电工申请解绑
					result.setResultCode(ResultCode.ERROR_402);
					result.setResultMessage("已邀请了，请勿重复邀请");
					return result;
				}
			}
			UserInfo invitationUser = userService.getUserInfoByMobile(mobile);
			if (invitationUser != null) {// 已存在会员
				if (invitationUser.isElectrician()) {// 电工
					if (invitationUser.getCompanyId().equals(
							userInfo.getCompanyId())) {
						result.setResultCode(ResultCode.ERROR_402);
						result.setResultMessage("您已经邀请过了");
						return result;
					} else {
						result.setResultCode(ResultCode.ERROR_402);
						result.setResultMessage("您来迟一步，已被其他企业邀请了");
						return result;
					}
				}
				if (invitationUser.isFacilitator()) {
					result.setResultCode(ResultCode.ERROR_402);
					result.setResultMessage("您来迟一步，已被其他企业邀请了");
					return result;
				}
				if (invitationUser.isCustomer()){
					result.setResultCode(ResultCode.ERROR_402);
					result.setResultMessage("您来迟一步，已被其他企业邀请了");
					return result;
				}
				if (invitationUser.isNormalMember()) {
					Electrician electrician = electricianService
							.getByUid(invitationUser.getUid());
					if (electrician != null
							&& electrician.getStatus() == ElectricianAuthStatus.AUTHING
									.getValue()) {// 电工认证中，无法邀请
						result.setResultCode(ResultCode.ERROR_402);
						result.setResultMessage("您来迟一步，已被其他企业邀请了");
						return result;
					}
					Company company2 = companyService.getByMemberId(invitationUser
							.getUid());
					if (company2 != null
							&& company2.getStatus() == CompanyAuthStatus.AUTHING.getValue()) {
						// 服务商和客户认证中，无法邀请
						result.setResultCode(ResultCode.ERROR_402);
						result.setResultMessage("您来迟一步，已被其他企业邀请了");
						return result;
					}
				}
			}
			if (companyElectrician == null) {
				companyElectrician = new CompanyElectrician();
			}
			companyElectrician.setStatus(CompanyElectricianStatus.INVITE
					.getValue());
			companyElectrician.setName(userName);
			companyElectrician.setMobile(mobile);
			companyElectrician.setCompanyId(userInfo.getCompanyId());
			companyElectrician.setInvitationTime(new Date());
			companyElectrician.setMemberId(invitationUser==null?0L:invitationUser.getUid());//未注册用户member_id=0L,邀请确认后，更新member_id
			companyElectrician.setCreateUser(userInfo.getLoginName());
			long rs=jedisUtil.setnx(CacheKey.CACHE_KEY_ELECTRICIAN_INVITATION+userInfo.getCompanyId()+CacheKey.SPLIT+mobile, mobile, 500L);
			if(rs==0){
				result.setResultCode(ResultCode.ERROR_403);
				result.setResultMessage("正在操作中，请勿重复操作");
				return result;
			}
			if (!electricianInvitationService.invite(companyElectrician)) {
				result.setResultCode(ResultCode.ERROR_500);
				result.setResultMessage("系统异常");
				return result;
			}
			Long invitationId = companyElectrician.getId();

			if(invitationUser==null){//未注册用户，只发送短信，并且将邀请信息保存在缓存中，用户注册登录后，立马PUSH消息给用户
				//给被邀请者PUSH短信和消息
				Map<String,String> params=new HashMap<String,String>();
				params.put("name", companyName);
				messageInfoService.sendSmsMessage(null, mobile, MessageId.ELECTRICIAN_INVITATION, params);
				//给被邀请者PUSH短信和消息
				//'invitationId':invitationId,'mobile':mobile,'name':userName,type:1/2 
				//
				InvitationInfo invitationInfo=new InvitationInfo();
				invitationInfo.setInvitationId(invitationId);
				invitationInfo.setMobile(mobile);
				invitationInfo.setName(companyName);
				invitationInfo.setType(1);
				setOffilneMessage(invitationInfo);
			}
			else{
				
				Map<String,String> params=new HashMap<String,String>();
				params.put("name", companyName);
				Map<String,String> exts=new HashMap<String,String>();
				exts.put("invitationId", ""+invitationId);
				exts.put("actionType", "none");
				messageInfoService.sendSmsAndPushMessage(invitationUser.getUid(),
						invitationUser.getMobile(), MessageId.ELECTRICIAN_INVITATION, params,exts);
			}
			//给邀请者PUSH短信和消息
			Map<String,String> params2=new HashMap<String,String>();
			params2.put("name", StringUtils.isNoneBlank(userName)?userName:mobile);//手机号码需要做处理
			messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(), MessageId.ELECTRICIAN_INVITATION_NOTIFY, params2);

		} catch (Exception e) {
			logger.error("electricianInvitation:{}", e);
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			return result;
		}
		return result;
	}

	@Override
	public Result agreeElectricianInvitation(
			AgreeElectricianInvitationReq agreeElectricianInvitationReq) {
		// 电工同意邀请
		// 消息推送
		Result result = new DefaultResult();
		Long invitationId = agreeElectricianInvitationReq.getInvitationId();
		Long uid = agreeElectricianInvitationReq.getUid();
		try {
			//判断用户信息和邀请信息
			if (uid == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不能为空");
				return result;
			}
			UserInfo userInfo = userService.getUserInfo(uid);
			if (userInfo == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不存在");
				return result;
			}
			if(userInfo.isSocialElectrician()){//社会电工了，邀请消息失效
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("您已经成为社会电工了，无法成为企业电工了");
				return result;
			}
			if(userInfo.isCompanyElectrician()){
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("您已经成为企业电工，无法成为企业电工了");
				return result;
			}
			if(userInfo.isFacilitator()||userInfo.isCustomer()){
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("您已经成为企业会员，无法成为企业电工了");
				return result;
			}
			Electrician electrician = electricianService
					.getByUid(userInfo.getUid());
			if (electrician != null
					&& electrician.getStatus() == ElectricianAuthStatus.AUTHING
							.getValue()) {// 电工认证中
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("社会电工认证中，无法成为企业电工");
				return result;
			}
			Company company = companyService.getByMemberId(userInfo
					.getUid());
			if (company != null
					&& company.getStatus() == CompanyAuthStatus.AUTHING
							.getValue()) {// 企业认证中
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("企业认证中，无法成为企业电工");
				return result;
			}
			if (invitationId == null) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("invitationId 不能为空");
				return result;
			}
			CompanyElectrician companyElectrician = electricianInvitationService
					.getById(invitationId);
			if (companyElectrician == null
					|| !companyElectrician.getMemberId().equals(uid)) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("邀请信息不存在");
				return result;
			}	
			if (companyElectrician.getStatus() != CompanyElectricianStatus.INVITE
					.getValue()) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("邀请信息不存在");
				return result;
			}
			//判断补充信息
			if (StringUtils.isBlank(agreeElectricianInvitationReq.getIdCardNo())) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("身份证号码不能为空");
				return result;
			}
			if (!IDCardUtils.isValidatedAllIdcard(agreeElectricianInvitationReq.getIdCardNo())) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("身份证号码错误");
				return result;
			}
			if (StringUtils.isBlank(agreeElectricianInvitationReq.getUserName())) {
				result.setResultCode(ResultCode.ERROR_203);
				result.setResultMessage("名字不能为空");
				return result;
			}
			if (StringUtils.isBlank(agreeElectricianInvitationReq.getIdCardFrontImg())) {
				result.setResultCode(ResultCode.ERROR_204);
				result.setResultMessage("身份证正面照不能为空");
				return result;
			}
			if (StringUtils.isBlank(agreeElectricianInvitationReq.getIdCardBackImg())) {
				result.setResultCode(ResultCode.ERROR_205);
				result.setResultMessage("身份证反面照不能为空");
				return result;
			}
			List<CertificateImgInfo> imgs = agreeElectricianInvitationReq.getCertificateImgs();
			if (imgs == null || imgs.isEmpty()) {
				result.setResultCode(ResultCode.ERROR_205);
				result.setResultMessage("认证证件照不能为空");
				return result;
			}
			UserInfo senderUser=userService.getFacilitatorAdmin(companyElectrician.getCompanyId());
			Company senderCompany=companyService.getById(companyElectrician.getCompanyId());
			if(electrician==null){
				electrician=new Electrician();
				electrician.setMemberId(uid);
			}//
			electrician.setUserName(agreeElectricianInvitationReq.getUserName());
			electrician.setIdCardNo(agreeElectricianInvitationReq.getIdCardNo());
			electrician.setIdCardFrontImg(agreeElectricianInvitationReq.getIdCardFrontImg());
			electrician.setIdCardBackImg(agreeElectricianInvitationReq.getIdCardBackImg());
			electrician.setCompanyId(senderUser.getCompanyId());
			electrician.setAuditTime(new Date());
			electrician.setAuditUser("系统");
			electrician.setStatus(ElectricianAuthStatus.SUCCESS.getValue());
			long rs=jedisUtil.setnx(CacheKey.CACHE_KEY_AGREE_ELECTRICIAN_INVITATION+uid, String.valueOf(uid), 500L);
			if(rs==0){
				result.setResultCode(ResultCode.ERROR_403);
				result.setResultMessage("正在操作中，请勿重复操作");
				return result;
			}
			if(!electricianInvitationService.agree(companyElectrician,electrician,imgs)){
				result.setResultCode(ResultCode.ERROR_500);
				result.setResultMessage("系统异常");
				return result;
			}
			//推送给邀请者
			Map<String,String> params=new HashMap<>();
			params.put("name", electrician.getUserName());
			messageInfoService.sendSmsAndPushMessage(senderUser.getUid(), senderUser.getMobile(),
					MessageId.AGREE_ELECTRICIAN_INVITATION_NOTIFY, params);
			//推送给自己
			Map<String,String> params2=new HashMap<>();
			params2.put("name", senderCompany.getName());
			Map<String,String> exts=new HashMap<>();
			exts.put("actionType", "agree");
			exts.put("invitationId", ""+companyElectrician.getId());
			messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(), MessageId.AGREE_ELECTRICIAN_INVITATION, params2, exts);
		} catch (Exception e) {
			logger.error("agreeElectricianInvitation:{}", e);
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			return result;
		}
		return result;
	}

	@Override
	public Result rejectElectricianInvitation(
			RejectElectricianInvitationReq rejectElectricianInvitationReq) {
		// 电工拒绝邀请
		// 消息推送
		Result result = new DefaultResult();
		Long invitationId = rejectElectricianInvitationReq.getInvitationId();
		Long uid = rejectElectricianInvitationReq.getUid();
		try {
			if (uid == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不能为空");
				return result;
			}
			UserInfo userInfo = userService.getUserInfo(uid);
			if (userInfo == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不存在");
				return result;
			}
			if(invitationId==null){
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("invitationId 不能为空");
				return result;
			}
			CompanyElectrician companyElectrician = electricianInvitationService
					.getById(invitationId);
			if (companyElectrician == null
					|| !companyElectrician.getMemberId().equals(uid)) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("邀请信息不存在");
				return result;
			}	
			if(companyElectrician.getStatus()==CompanyElectricianStatus.INVITE.getValue()){
				long rs=jedisUtil.setnx(CacheKey.CACHE_KEY_REJECT_ELECTRICIAN_INVITATION+companyElectrician.getId(), String.valueOf(companyElectrician.getId()), 500L);
				if(rs==0){
					result.setResultCode(ResultCode.ERROR_403);
					result.setResultMessage("正在操作中，请勿重复操作");
					return result;
				}
				if(electricianInvitationService.reject(companyElectrician)){
					//推送消息，并且修改前面推送的邀请信息
					//推送给服务商(邀请发起者)
					UserInfo senderUser=userService.getFacilitatorAdmin(companyElectrician.getCompanyId());
					Company senderCompany=companyService.getById(companyElectrician.getCompanyId());
					String electricianName=companyElectrician.getName();
					if(StringUtils.isBlank(electricianName)){
						electricianName=BizUtils.getTailMobile(companyElectrician.getMobile());
					}
					Map<String,String> params=new HashMap<>();
					params.put("name", electricianName);
					messageInfoService.sendSmsAndPushMessage(senderUser.getUid(), senderUser.getMobile(),
							MessageId.REJECT_ELECTRICIAN_INVITATION_NOTIFY, params);
					//推送给电工(自己)
					Map<String,String> params2=new HashMap<>();
					params2.put("name", senderCompany.getName());
					Map<String,String> exts=new HashMap<>();
					exts.put("actionType", "reject");
					exts.put("invitationId", ""+companyElectrician.getId());
					messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(), 
							MessageId.REJECT_ELECTRICIAN_INVITATION, params2, exts);
				}
				else{
					result.setResultCode(ResultCode.ERROR_500);
					result.setResultMessage("系统异常");
					return result;
				}
			}
			else{
				result.setResultCode(ResultCode.ERROR_401);
				result.setResultMessage("邀请信息失效");
				return result;
			}
			
			
		} catch (Exception e) {
			logger.error("rejectElectricianInvitation:{}", e);
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			return result;
		}
		return result;
	}
	@Override
	public CompanyInvitationListResult list(
			CompanyInvitationListReq companyInvitationListReq) {
		CompanyInvitationListResult result=new CompanyInvitationListResult();
		try{
			if(companyInvitationListReq.getCompanyId()==null){
				result.set(ResultCode.ERROR_201, "companyId 不能为空");
				return result;
			}
			if(companyInvitationListReq.getOffset()<0){
				result.set(ResultCode.ERROR_202, "offset 不能小于0");
				return result;
			}
			if(companyInvitationListReq.getPageSize()<Constants.DEFAULT_PAGE_SIZE){
				result.set(ResultCode.ERROR_203, "pageSize 不能小于"+Constants.DEFAULT_PAGE_SIZE);
				return result;
			}
			CompanyInvitationQuery companyInvitationQuery=new CompanyInvitationQuery();
			companyInvitationQuery.setCompanyId(companyInvitationListReq.getCompanyId());
			companyInvitationQuery.setMobile(companyInvitationListReq.getMobile());
			companyInvitationQuery.setName(companyInvitationListReq.getCompanyName());
			companyInvitationQuery.setOffset(companyInvitationListReq.getOffset());
			companyInvitationQuery.setPageSize(companyInvitationListReq.getPageSize());
			PageResult<CompanyInvitationInfo> pageResult=companyInvitationService.list(companyInvitationQuery);
			result.setHasNext(pageResult.isHasNext());
		    result.setList(pageResult.getData());
		    result.setNextOffset(pageResult.getNextOffset());
		    result.setTotalCount(pageResult.getTotal());
		}
		catch (Exception e) {
			logger.error("companyInvitationList:{}", e);
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			return result;
		}
		return result;
	}
	@Override
	public ElectricianInvitationListResult list(
			ElectricianInvitationListReq electricianInvitationListReq) {
		ElectricianInvitationListResult result=new ElectricianInvitationListResult();
		try{
			if(electricianInvitationListReq.getCompanyId()==null){
				result.set(ResultCode.ERROR_201, "companyId 不能为空");
				return result;
			}
			if(electricianInvitationListReq.getOffset()<0){
				result.set(ResultCode.ERROR_202, "offset 不能小于0");
				return result;
			}
			if(electricianInvitationListReq.getPageSize()<Constants.DEFAULT_PAGE_SIZE){
				result.set(ResultCode.ERROR_203, "pageSize 不能小于"+Constants.DEFAULT_PAGE_SIZE);
				return result;
			}
			Long adminUid=userService.getFacilitatorAdminUid(electricianInvitationListReq.getCompanyId());
			ElectricianInvitationQuery electricianInvitationQuery=new ElectricianInvitationQuery();
			electricianInvitationQuery.setCompanyId(electricianInvitationListReq.getCompanyId());
			electricianInvitationQuery.setMobile(electricianInvitationListReq.getMobile());
			electricianInvitationQuery.setName(electricianInvitationListReq.getUserName());
			electricianInvitationQuery.setOffset(electricianInvitationListReq.getOffset());
			electricianInvitationQuery.setPageSize(electricianInvitationListReq.getPageSize());
			PageResult<ElectricianInvitationInfo> pageResult=electricianInvitationService.list(electricianInvitationQuery);
			result.setHasNext(pageResult.isHasNext());
			for(ElectricianInvitationInfo electricianInvitationInfo:pageResult.getData()){
				if(electricianInvitationInfo.getMemberId().compareTo(adminUid)==0){
					electricianInvitationInfo.setMainAccount(true);
					break;
				}
			}
		    result.setList(pageResult.getData());
		    result.setNextOffset(pageResult.getNextOffset());
		    result.setTotalCount(pageResult.getTotal());
		}
		catch (Exception e) {
			logger.error("electricianInvitationList:{}", e);
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			return result;
		}
		return result;
	}
	@Override
	public GetElectricianInvitationResult getElectricianInvitation(Long uid) {
		GetElectricianInvitationResult result=new GetElectricianInvitationResult();
		try{
			if(uid==null){
				result.set(ResultCode.ERROR_201, "uid 不能为空");
				return result;
			}
			UserInfo userInfo = userService.getUserInfo(uid);
			if (userInfo == null) {
				result.set(ResultCode.ERROR_201, "uid 不存在");
				return result;
			}
			if(!userInfo.isCompanyElectrician()){
				result.set(ResultCode.ERROR_201, "非企业电工，无权限");
				return result;
			}
			CompanyElectrician companyElectrician=electricianInvitationService.getByMemberIdAndCompanyId(userInfo.getUid(), userInfo.getCompanyId());
			if(companyElectrician!=null){
				result.setInvitationId(companyElectrician.getId());
				result.setInvitationStatus(companyElectrician.getStatus());
				result.setAdmin(userInfo.isFacilitatorAdmin());
			}
			else{
				result.set(ResultCode.ERROR_401, "没有邀请信息");
			}
			
		}
		catch (Exception e) {
			logger.error("getElectricianInvitation:{}", e);
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			return result;
		}
		return result;
		
	}

}
