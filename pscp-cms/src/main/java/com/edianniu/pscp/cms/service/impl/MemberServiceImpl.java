package com.edianniu.pscp.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.cms.bean.req.ChangeMemberStatusReq;
import com.edianniu.pscp.cms.commons.ResultCode;
import com.edianniu.pscp.cms.commons.SaveOrUpdateUserResult;
import com.edianniu.pscp.cms.dao.MemberDao;
import com.edianniu.pscp.cms.dao.MemberWalletDao;
import com.edianniu.pscp.cms.entity.MemberEntity;
import com.edianniu.pscp.cms.entity.MemberWallet;
import com.edianniu.pscp.cms.service.MemberMemberRoleService;
import com.edianniu.pscp.cms.service.MemberService;
import com.edianniu.pscp.cms.utils.JsonResult;
import com.edianniu.pscp.cms.utils.PasswordUtil;
import com.edianniu.pscp.cms.utils.SessionInfo;
import com.edianniu.pscp.cms.utils.ShiroUtils;
import com.edianniu.pscp.cms.utils.WebUtils;
import com.edianniu.pscp.mis.bean.DefaultResult;
import com.edianniu.pscp.mis.bean.GetMsgCodeResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.user.RegisterReqData;
import com.edianniu.pscp.mis.bean.user.RegisterResult;
import com.edianniu.pscp.mis.bean.user.UserStatus;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 系统用户
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 上午15:20:09
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {
	private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberMemberRoleService memberMemberRoleService;
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private MemberWalletDao memberWalletDao;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return memberDao.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return memberDao.queryAllMenuId(userId);
	}

	@Override
	public MemberEntity queryByLoginName(String loginName) {
		return memberDao.queryByLoginName(loginName);
	}
	
	@Override
	public MemberEntity queryObject(Long userId) {
		return memberDao.queryObject(userId);
	}

	@Override
	public List<MemberEntity> queryList(Map<String, Object> map){
		return memberDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return memberDao.queryTotal(map);
	}

	@Override
	@Transactional
	public SaveOrUpdateUserResult save(MemberEntity user) {
		SaveOrUpdateUserResult result=new SaveOrUpdateUserResult();
		/*if(isExistLoginName(user.getLoginName())){
			result.set(ResultCode.EXIST_LOGIN_NAME, "登录名重复");
			return result;
		}*/
		if(isExistMobile(user.getMobile())){
			result.set(ResultCode.EXIST_MOBILE, "手机号重复");
			MemberEntity existUser=this.queryByMobile(user.getMobile());
			if(user.getIsFacilitator()==MemberEntity.TAG_YES){//新增服务商帐号
				if(existUser.getCompanyId().compareTo(0L)==0){//服务商帐号
					if(existUser.isFacilitator()||existUser.isAdmin()){
						return result;
					}
					else{
						existUser.setIsFacilitator(MemberEntity.TAG_YES);
						existUser.setRoleIdList(user.getRoleIdList());
						existUser.setPasswd(null);//不修改密码
						return update(existUser);
					}
				}
				else if(existUser.getCompanyId().compareTo(new Long(0))==0){//平台帐号
					if(!existUser.isElectrician()){//社会电工
						existUser.setIsFacilitator(MemberEntity.TAG_YES);
						existUser.setPasswd(null);//不修改密码
						existUser.setRoleIdList(user.getRoleIdList());
						existUser.setCompanyId(0L);
						return update(existUser);
					}
				}
			}
			else if(user.getIsElectrician()==MemberEntity.TAG_YES){//新增企业电工帐号
				if(!existUser.isElectrician()){//不是电工
					if(existUser.getCompanyId().compareTo(0L)==0){//服务商用户
						//服务商用户，包括管理员和子帐号，添加的时候不修改密码和登录名
						existUser.setPasswd(null);
						existUser.setAge(user.getAge());
						existUser.setSex(user.getSex());
						existUser.setIsElectrician(MemberEntity.TAG_YES);
						existUser.setRoleIdList(new ArrayList<Long>());
						return update(existUser);
					}
					else{
						if(user.getCompanyId().compareTo(0L)==0){
							existUser.setPasswd(user.getPasswd());
							existUser.setAge(user.getAge());
							existUser.setSex(user.getSex());
							existUser.setIsElectrician(MemberEntity.TAG_YES);
							existUser.setRoleIdList(new ArrayList<Long>());
							return update(existUser);
						}
					}
					
				}
				else{//是电工
					if(user.getCompanyId().compareTo(0L)==0){
						//已经是企业电工了
						result.setMessage("手机号重复");
						return result;
					}
					else{
						//已经是社会电工了
						result.setMessage("手机号被使用，请更换其他手机号");
						return result;
					}
				}
			}
			else{//新增客户帐号
				
			}
			return result;
		}
		user.setLoginName(user.getMobile());
		user.setCreateTime(new Date());
		//sha256加密
		//user.setPasswd(new Sha256Hash(user.getPasswd()).toHex());
		user.setPasswd(PasswordUtil.encode(user.getPasswd()));
		memberDao.save(user);
		//添加用户资金信息
		MemberWallet wallet=new MemberWallet();
		wallet.setAmount(0D);
		wallet.setFreezingAmount(0D);
		wallet.setUid(user.getUserId());
		wallet.setCreateUser(ShiroUtils.getUserEntity().getLoginName());
		memberWalletDao.save(wallet);
		//保存用户与角色关系
		memberMemberRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
		result.setUser(user);
		return result;
	}

	@Override
	@Transactional
	public SaveOrUpdateUserResult update(MemberEntity user) {
		SaveOrUpdateUserResult result=new SaveOrUpdateUserResult();
		/*
		
		if(StringUtils.isBlank(user.getPasswd())){
			user.setPasswd(null);
		}else{
			user.setPasswd(PasswordUtil.encode(user.getPasswd()));
			//user.setPasswd(new Sha256Hash(user.getPasswd()).toHex());
		}
		memberDao.update(user);
		*/
		memberDao.update(user);
		//保存用户与角色关系
		//memberMemberRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
		result.setUser(user);
		return result;
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] userIds) {
		for(Long userId:userIds){
			MemberEntity user=this.queryObject(userId);
			if(user!=null){
				user.setIsFacilitator(0);
				if(!user.isElectrician()){
					user.setCompanyId(0L);
				}
				user.setPasswd(null);
				user.setRoleIdList(new ArrayList<Long>());
				this.update(user);
			}
		}
	}

	@Override
	public int updatePasswd(Long userId, String passwd, String newPasswd) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("passwd", passwd);
		map.put("newPasswd", newPasswd);
		return memberDao.updatePassword(map);
	}

	@Override
	public MemberEntity queryByMobile(String mobile) {
		
		return memberDao.queryByMobile(mobile);
	}

	@Override
	public boolean isExistLoginName(String loginName) {
		
		return memberDao.getCountByLoginName(loginName)>0?true:false;
	}

	@Override
	public boolean isExistMobile(String mobile) {
		
		return memberDao.getCountByMobile(mobile)>0?true:false;
	}

	

	@Override
	public  RegisterResult register(String userName, String passwd, String msgId, String msgCode) {
		RegisterReqData data=new RegisterReqData();
		data.setMobile(userName);
		data.setPasswd(passwd);
		data.setMsgcode(msgCode);
		data.setMsgcodeid(msgId);
		RegisterResult result=userInfoService.register(data);
		
		JsonResult json=new JsonResult();
		if(result.isSuccess()){
			UserInfo userInfo=result.getUserInfo();
			SessionInfo session=new SessionInfo();
			BeanUtils.copyProperties(userInfo, session);
			WebUtils.setSessionInfo(session);
			json.setSuccess(true);
		}else{
			json.setMsg(result.getResultMessage());
			json.setSuccess(false);
			json.setObject(result);
		}
		return result;
	}

	@Override
	public Result resetPwd(String mobile, String password, String msgCodeId, String msgCode) {
		Result result=userInfoService.resetPwd(mobile, password, msgCodeId, msgCode);
		return result;
	}

	@Override
	public GetMsgCodeResult getMsgCode(String mobile, Integer type) {
		GetMsgCodeResult  result=userInfoService.getMsgCode(mobile, type);
		return result;
	}

	@Override
	public MemberEntity getCompanyAdmin(Long companyId) {
		MemberEntity bean=memberDao.getCompanyAdmin(companyId);
		return bean;
	}

	@Override
	public Result changeMemberStatus(ChangeMemberStatusReq req) {
		Result result=new DefaultResult();
		
		try{
			Integer status=req.getStatus();
			Long userId=req.getUserId();
			if(userId==null){
				result.set(ResultCode.ERROR, "userId 不能为空");
				return result;
			}
			if(status==null){
				result.set(ResultCode.ERROR, "status 不能为空");
				return result;
			}
			if(!(status==UserStatus.DISABLE.getValue()||status==UserStatus.ENABLE.getValue())){
				result.set(ResultCode.ERROR, "status只能是0禁用，1启用");
				return result;
			}
			MemberEntity bean=this.queryObject(userId);
			if(bean==null){
				result.set(ResultCode.ERROR, "userId 不存在");
				return result;
			}
			if(bean.getStatus()!=status){
				bean.setStatus(status);
				memberDao.update(bean);
			}
		}
		catch(Exception e){
			logger.error("changeStatus",e);
			result.set(ResultCode.ERROR, "系统异常");
		}
		
		return result;
	}
}
