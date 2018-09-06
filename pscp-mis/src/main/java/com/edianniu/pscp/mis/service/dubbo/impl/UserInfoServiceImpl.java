
package com.edianniu.pscp.mis.service.dubbo.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.result.SendMessageResult;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.*;
import com.edianniu.pscp.mis.bean.company.CompanyAuthStatus;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.company.CompanyMemberType;
import com.edianniu.pscp.mis.bean.electrician.ElectricianAuthStatus;
import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;
import com.edianniu.pscp.mis.bean.user.*;
import com.edianniu.pscp.mis.bean.user.invitation.InvitationInfo;
import com.edianniu.pscp.mis.commons.CacheKey;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.Company;
import com.edianniu.pscp.mis.domain.Electrician;
import com.edianniu.pscp.mis.domain.UserFeedback;
import com.edianniu.pscp.mis.domain.UserWallet;
import com.edianniu.pscp.mis.exception.BusinessException;
import com.edianniu.pscp.mis.service.*;
import com.edianniu.pscp.mis.service.dubbo.PermissionInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.MoneyUtils;
import com.edianniu.pscp.mis.util.MsgCodeUtils;
import com.edianniu.pscp.mis.util.PasswordUtil;
import com.edianniu.pscp.mis.util.PropertiesUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import stc.skymobi.cache.redis.JedisUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author cyl
 */
@Service
@Repository("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger logger = LoggerFactory
            .getLogger(UserInfoServiceImpl.class);

    @Autowired
    @Qualifier("cachedService")
    private CachedService cachedService;
    @Autowired
	private JedisUtil jedisUtil;
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;

    @Autowired
    @Qualifier("memberMessageService")
    private MemberMessageService memberMessageService;

    @Autowired
    @Qualifier("electricianService")
    private ElectricianService electricianService;

    @Autowired
    @Qualifier("messageInfoService")
    private MessageInfoService messageInfoService;

    @Autowired
    @Qualifier("companyCertificateImageService")
    private CompanyCertificateImageService companyCertificateImageService;
    @Autowired
	private ElectricianInvitationService electricianInvitationService;// 电工邀请服务
	@Autowired
	private CompanyInvitationService companyInvitationService;// 企业邀请服务
	@Autowired
	private UserWalletService userWalletService;
	@Autowired
	private PermissionInfoService permissionInfoService;


    /**
     * 设置用户登录状态信息
     *
     * @param userInfo
     * @return
     */
    private LoginInfo setLoginStatus(UserInfo userInfo, BaseTerminalData baseTerminalData) {
        LoginInfo loginInfo = cachedService.setLoginInfo(userInfo.getUid(), baseTerminalData);
        userInfo.setPasswd(null);
        if (StringUtils.isNotBlank(baseTerminalData.getOsType()) && StringUtils.isNotBlank(baseTerminalData.getClientId())) {
            GeTuiClient getuiClient = new GeTuiClient();
            getuiClient.setClientId(baseTerminalData.getClientId());
            getuiClient.setDeviceToken(baseTerminalData.getDeviceToken());
            getuiClient.setOsType(baseTerminalData.getOsType());
            getuiClient.setReportTime(new Date());
            getuiClient.setUid(userInfo.getUid());
            getuiClient.setCompanyId(userInfo.getCompanyId());
            getuiClient.setAppPkg(baseTerminalData.getAppPkg());
            getuiClient.setAppType(loginInfo.getAppType());
            messageInfoService.sendGeTuiClientMessage(userInfo.getUid() + "", JSONObject.toJSONString(getuiClient));
        }
        return loginInfo;
    }
    private void offlineMessage(UserInfo userInfo){
    	String content=jedisUtil.get("invitation#"+userInfo.getMobile());
    	if(StringUtils.isNotBlank(content)){
    		List<InvitationInfo> notDoList=new ArrayList<InvitationInfo>();
    		List<InvitationInfo> list=JSON.parseArray(content, InvitationInfo.class);
    		for(InvitationInfo invitationInfo:list){
    			int type=invitationInfo.getType();
    			Long invitationId=invitationInfo.getInvitationId();
    			MessageId messageId=(type==1)?MessageId.ELECTRICIAN_INVITATION:MessageId.COMPANY_INVITATION;
    			int rs=0;
    			if(messageId.equals(MessageId.ELECTRICIAN_INVITATION)){
    				rs=electricianInvitationService.updateMemberId(invitationId, userInfo.getUid(),"系统");
    			}
    			else if(messageId.equals(MessageId.COMPANY_INVITATION)){
    				rs=companyInvitationService.updateMemberId(invitationId, userInfo.getUid(),"系统");
    			}
    			if(rs==1){
    				Map<String,String> params=new HashMap<>();
        			params.put("name", invitationInfo.getName());
        			Map<String,String> exts=new HashMap<>();
        			exts.put("invitationId", ""+invitationId);
        			exts.put("actionType", "none");
        			messageInfoService.sendPushMessage(userInfo.getUid(), userInfo.getMobile(), messageId,params, exts);
				}
    			else{
    				notDoList.add(invitationInfo);
    			}
    		}
    		if(notDoList.isEmpty()){
    			jedisUtil.del("invitation#"+userInfo.getMobile());
    		}
    		else{
    			jedisUtil.set("invitation#"+userInfo.getMobile(), JSON.toJSONString(notDoList));
    		}
    	}
    }


    @Override
    public LoginResult login(LoginReqData loginReqData) {
        LoginResult result = new LoginResult();
        String loginName = loginReqData.getLoginName();
        String passwd = loginReqData.getPasswd();
        try {
            if (StringUtils.isBlank(loginReqData.getAppPkg())) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("appPkg不能为空");
                return result;
            }
            if (!loginReqData.checkAppPkg()) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("appPkg不正确");
                return result;
            }
            if (StringUtils.isBlank(loginName)) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("用户名不能为空!");
                return result;
            }
            if (StringUtils.isBlank(passwd)) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("密码不能为空!");
                return result;
            }
            if (!BizUtils.checkLength(loginName, 20)) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("用户名过长,请重新输入!");
                return result;
            }
            if (!BizUtils.checkLength(passwd, 30)) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("密码过长,请重新输入!");
                return result;
            }
            UserInfo userInfo = userService.getUserInfoByMobile(loginName);
            if (null == userInfo) {
                if (loginReqData.isFacilitatorApp() || loginReqData.isCustomerApp()) {//电力运维-服务商APP,支持公司名称和手机号码登录
                    userInfo = userService.getUserInfoByCompanyName(loginName);
                    if (userInfo == null) {
                        result.setResultCode(ResultCode.ERROR_401);
                        result.setResultMessage("用户名不存在");
                        return result;
                    }
                } else {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("用户名不存在");
                    return result;
                }

            }
            if (loginReqData.isElectricianApp()) {//电工APP
                //普通用户,社会电工，企业电工可以登录电工APP，其他用户登录给出提示，
                //认证中的服务商，认证中的客户
                if (!userInfo.isElectrician()) {
                    if (userInfo.isNormalMember()) {
                        Company company = companyService.getByMemberId(userInfo.getUid());
                        if (company != null && company.getStatus() == CompanyAuthStatus.AUTHING.getValue()) {//服务商或者客户认证中，无法登录
                            result.setResultCode(ResultCode.ERROR_401);
                            result.setResultMessage("用户禁止登录");
                            return result;
                        }
                    } else {
                        result.setResultCode(ResultCode.ERROR_401);
                        result.setResultMessage("用户禁止登录");
                        return result;
                    }
                }
            }
            if (loginReqData.isFacilitatorApp()) {//服务商APP
                //普通用户（不包括认证中的电工），服务商帐号（包括子帐号）可以登录运维APP（服务商版）
                if (!userInfo.isFacilitator()) {
                    if (userInfo.isNormalMember()) {
                        Electrician electrician = electricianService.getByUid(userInfo.getUid());
                        if (electrician != null && electrician.getStatus() == ElectricianAuthStatus.AUTHING.getValue()) {//电工认证中，无法登录
                            result.setResultCode(ResultCode.ERROR_401);
                            result.setResultMessage("用户禁止登录");
                            return result;
                        }
                        Company company = companyService.getByMemberId(userInfo.getUid());
                        if (company != null &&
                                company.getStatus() == CompanyAuthStatus.AUTHING.getValue() &&
                                CompanyMemberType.parse(company.getMemberType()).equals(CompanyMemberType.CUSTOMER)) {//客户认证中，无法登录
                            result.setResultCode(ResultCode.ERROR_401);
                            result.setResultMessage("用户禁止登录");
                            return result;
                        }
                    } else {
                        result.setResultCode(ResultCode.ERROR_401);
                        result.setResultMessage("用户禁止登录");
                        return result;
                    }
                }
            }
            if (loginReqData.isCustomerApp()) {
                //普通用户(不包括认证中的电工，不包括认证中的服务商)，客户账号(包括子账号)可以登录运维APP(客户版)
                if (!userInfo.isCustomer()) {
                    if (userInfo.isNormalMember()) {
                        Electrician electrician = electricianService.getByUid(userInfo.getUid());
                        if (electrician != null && electrician.getStatus() == ElectricianAuthStatus.AUTHING.getValue()) {//电工认证中，无法登录
                            result.setResultCode(ResultCode.ERROR_401);
                            result.setResultMessage("用户禁止登录");
                            return result;
                        }
                        Company company = companyService.getByMemberId(userInfo.getUid());
                        if (company != null && company.getStatus() == CompanyAuthStatus.AUTHING.getValue() &&
                                CompanyMemberType.parse(company.getMemberType()).equals(CompanyMemberType.FACILITATOR)) {//服务商认证中，无法登录
                            result.setResultCode(ResultCode.ERROR_401);
                            result.setResultMessage("用户禁止登录");
                            return result;
                        }
                    } else {
                        result.setResultCode(ResultCode.ERROR_401);
                        result.setResultMessage("用户禁止登录");
                        return result;
                    }
                }
            }
            String pwd = PasswordUtil.encode(passwd.trim());
            if (!userInfo.getPasswd().equals(pwd)) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("密码不正确");
                return result;
            }
            if (userInfo.getStatus() == UserStatus.DISABLE.getValue()) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("用户禁用");
                return result;
            }
            LoginInfo loginInfo = setLoginStatus(userInfo, loginReqData);
            offlineMessage(userInfo);
            UserExtInfo userExtInfo = getUserExtInfo(userInfo);
            result.setCompanyInfo(userExtInfo.getCompanyInfo());
            result.setElectricianInfo(userExtInfo.getElectricianInfo());
            result.setUserInfo(userInfo);
            result.setToken(loginInfo.getToken());
        } catch (Exception e) {
            logger.error("login biz:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
        }
        return result;
    }

    private UserExtInfo getUserExtInfo(UserInfo userInfo) {
        UserExtInfo userExtInfo = new UserExtInfo();
        if (userInfo.isElectrician()) {
            ElectricianInfo info = electricianService.getInfoByUid(userInfo.getUid());
            userExtInfo.setElectricianInfo(info);
        } else {
            ElectricianInfo info = new ElectricianInfo();
            info.setAuthStatus(ElectricianAuthStatus.NOTAUTH.getValue());
            userExtInfo.setElectricianInfo(info);
        }
        CompanyInfo companyInfo = companyService.getInfoByUserInfo(userInfo);
        if (companyInfo == null) {
            companyInfo = new CompanyInfo();
        }
        /*if(userInfo.isCompanyElectrician()){//如果是企业电工，获取绑定信息
        	//邀请ID
        	//邀请状态
        	//邀请类型
        	CompanyElectrician companyElectrician=electricianInvitationService.
        			getByMemberIdAndCompanyId(userInfo.getUid(), userInfo.getCompanyId());
        	
        }*/
        userExtInfo.setCompanyInfo(companyInfo);
        return userExtInfo;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.edianniu.pscp.mis.service.dubbo.UserInfoService#resetPwd(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Result resetPwd(String mobile, String passwd, String msgCodeId,
                           String msgCode) {
        Result result = new DefaultResult();
        try {
            if (!BizUtils.checkMobile(mobile)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("手机号码输入错误！");
                return result;
            }
            UserInfo userInfo = userService.getUserInfoByMobile(mobile.trim());
            if (null == userInfo) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("手机号码不存在");
                return result;
            }
            if (!BizUtils.checkPassword(passwd)) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("密码输入格式不正确(6-20个字符,不区分大小写)！");
                return result;
            }
            if (!cachedService.checkMsgCode(msgCodeId, msgCode)) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("验证码不正确");
                return result;
            }

            if (!userService
                    .changePwd(userInfo.getUid(), mobile, PasswordUtil.encode(passwd.trim()))) {
                result.setResultCode(ResultCode.ERROR_500);
                result.setResultMessage("系统异常");
                return result;
            }
        } catch (Exception e) {
            logger.error("reset pwd by msgCode:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
        }
        return result;
    }

    /**
     * 驗證操作密碼是否存在
     * @param uid
     * @return
     */
    @Override
    public CheckSwitchPwdResult checkSwitchPwd(Long uid){
    	CheckSwitchPwdResult result = new CheckSwitchPwdResult();
    	try {
    		if (null == uid) {
				result.set(ResultCode.ERROR_201, "客户uid不能为空");
				return result;
			}
    		UserInfo userInfo = userService.getUserInfo(uid);
			Long companyId = userInfo.getCompanyId();
			if (null == userInfo || null == companyId) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			if (null != userInfo.getSwitchpwd()) {
				result.setIsExist(Constants.TAG_YES);
			}
		} catch (Exception e) {
			logger.error("check switchpwd:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
		}
    	return result;
    }
    
    /**
	 * 设置开关闸操作密码
	 * @param uid         用户memberid
	 * @param pwd         操作密码
	 * @param rePwd       确认操作密码
	 * @param msgcodeid
	 * @param msgcode
	 */
    @Override
    public Result setSwitchPwd(Long uid, String pwd, String rePwd, String msgcodeid, String msgcode){
    	Result result = new DefaultResult();
    	try {
    		UserInfo userInfo = userService.getUserInfo(uid);
        	if (null == userInfo) {
    			result.set(ResultCode.ERROR_201, "uid不能为空");
    			return result;
    		}
        	if (!BizUtils.checkPassword(pwd)) {
        		result.set(ResultCode.ERROR_201, "密码格式不正确(6-20个字符，包括数字和字母以及特殊字符)");
    			return result;
			}
        	if (!BizUtils.checkPassword(rePwd)) {
        		result.set(ResultCode.ERROR_201, "密码格式不正确(6-20个字符，包括数字和字母以及特殊字符)");
        		return result;
        	}
        	if (!pwd.equals(rePwd)) {
        		result.set(ResultCode.ERROR_201, "两次输入不一致，请重新输入");
        		return result;
			}
        	if (!cachedService.checkMsgCode(msgcodeid, msgcode)) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("验证码不正确");
                return result;
            }
        	userService.setSwitchPwd(uid, PasswordUtil.encode(pwd));
		} catch (Exception e) {
			logger.error("set switch pwd by msgCode:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
		}
    	return result;
    }
    
    /**
     * 更改绑定手机号码
     */
    @Override
    public Result changeMobile(String mobile, String newMobile, String msgCodeId, String msgCode, String password){
    	Result result = new DefaultResult();
    	try {
    		final String oldMobileTrim = mobile.trim();
    		final String newMobileTrim = newMobile.trim();
    		if (!BizUtils.checkMobile(oldMobileTrim)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("旧手机号码错误！");
                return result;
            }
            final UserInfo userInfo = userService.getUserInfoByMobile(oldMobileTrim);
            if (null == userInfo) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("旧手机号码不存在");
                return result;
            }
            if (!BizUtils.checkMobile(newMobileTrim)) {
            	result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("新手机号码错误！");
                return result;
			}
            if (oldMobileTrim.compareTo(newMobileTrim) == 0) {
            	result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("新旧手机号码相同！");
                return result;
			}
            if (userService.existByMobile(newMobileTrim)) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("新手机号码已被注册过了");
                return result;
            }
            if (!BizUtils.checkPassword(password)) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("密码输入不合法！");
                return result;
            }
            password = PasswordUtil.encode(password);
            if (userInfo.getPasswd().compareTo(password) != 0) {
            	result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("密码不正确！");
                return result;
			}
            if (!cachedService.checkMsgCode(msgCodeId, msgCode)) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("验证码不正确");
                return result;
            }
            if (! userService.changeMobile(userInfo.getUid(), oldMobileTrim, newMobileTrim)) {
            	result.setResultCode(ResultCode.ERROR_500);
                result.setResultMessage("系统异常");
                return result;
			}
            
            // TODO 给旧号码发送短信通知
            EXEXUTOR_SERVICE.submit(new Runnable() {
				@Override
				public void run() {
					Long companyId = userInfo.getCompanyId();
		            if (null != companyId && 0L != companyId) {
		            	Company company = companyService.getById(companyId);
		            	if (null != company) {
		            		MessageId messageId = MessageId.CHANGE_MOBILE_SUCCESS;
		            		Map<String, String> param = new HashMap<>();
		                    param.put("company_name", company.getName());
		                    param.put("new_mobile", newMobileTrim);
		                    messageInfoService.sendSmsMessage(null, oldMobileTrim, messageId, param);
						}
					}
				}
			});
            
		} catch (Exception e) {
			logger.error("change mobile by msgCode:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
		}
    	return result;
    }
    
    /**
     * 创建一个固定线程池
     */
    public static final ExecutorService EXEXUTOR_SERVICE = new ThreadPoolExecutor(
    		1, Runtime.getRuntime().availableProcessors(), 
    		60, TimeUnit.SECONDS, 
    		// 工作队列
    		new SynchronousQueue<Runnable>(), 
    		// 线程池饱和处理策略
    		new ThreadPoolExecutor.CallerRunsPolicy());
    
    
    /*
     * (non-Javadoc)
     * @see
     * com.edianniu.pscp.mis.service.dubbo.UserInfoService#getUserInfo(java.lang.
     * Long)
     */
    @Override
    public GetUserInfoResult getUserInfo(Long uid) {
        GetUserInfoResult result = new GetUserInfoResult();
        try {
            if (uid == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("用户信息为空");
                return result;
            }
            UserExtInfo userExtInfo = getUserExtInfo(userInfo);
            result.setCompanyInfo(userExtInfo.getCompanyInfo());
            result.setElectricianInfo(userExtInfo.getElectricianInfo());
            result.setMemberInfo(userInfo);

        } catch (Exception e) {
            logger.error("getUserInfo:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }

    @Override
    public GetUserCenterResult getUserCenter(GetUserCenterReq req) {
        GetUserCenterResult result = new GetUserCenterResult();
        try {
            if (req.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(req.getUid());
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息为空");
                return result;
            }
            LoginInfo loginInfo = getLoginInfo(req.getUid());
            if (loginInfo == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("用户信息为空");
                return result;
            }
            SimpleMemberInfo simpleMemberInfo = new SimpleMemberInfo();
            BeanUtils.copyProperties(userInfo, simpleMemberInfo);
            if (loginInfo.isElectricianApp()) {
                Electrician electrician = electricianService.getByUid(userInfo.getUid());
                if (electrician != null) {//判断是否有电工信息
                    if (userInfo.isElectrician()) {//判断是否电工
                        simpleMemberInfo.setUserName(electrician.getUserName());
                    }
                    simpleMemberInfo.setElectricianAuthStatus(electrician.getStatus());
                    if (userInfo.isCompanyElectrician()) {
                        Company company = companyService.getById(userInfo.getCompanyId());
                        if (company != null) {
                            simpleMemberInfo.setCompanyAuthStatus(company.getStatus());
                            simpleMemberInfo.setCompanyName(company.getName());
                            simpleMemberInfo.setCompanyId(company.getId());
                        }
                    }
                }
            } else if (loginInfo.isFacilitatorApp() || loginInfo.isCustomerApp()) {
                Company company = null;
                if (userInfo.isFacilitator() || userInfo.isCustomer()) {
                    company = companyService.getById(userInfo.getCompanyId());
                } else {
                    company = companyService.getByMemberId(userInfo.getUid());
                }
                if (company != null) {
                    simpleMemberInfo.setCompanyAuthStatus(company.getStatus());
                    simpleMemberInfo.setUserName(company.getUserName());
                    simpleMemberInfo.setCompanyName(company.getName());
                    simpleMemberInfo.setCompanyId(company.getId());
                }
            }
            Long userId=userInfo.getUid();
            if (userInfo.isFacilitator()) {// 服务商
                if (userInfo.isNotFacilitatorAdmin()) {
                	userId = userService.getFacilitatorAdminUid(userInfo
                            .getCompanyId());
                }
            }
            UserWallet userwalle = this.userWalletService.getByUid(userId);
            if (userwalle == null) {
                throw new BusinessException(String.format("用户 [%s] 钱包信息不存在!",
                        userId));
            }
            result.setWalletAmount(MoneyUtils.format(userwalle.getAmount()));
            result.setSimpleMemberInfo(simpleMemberInfo);
            Integer num = memberMessageService.getMemberNotReadMessagesCount(req.getUid());
            result.setMessageNum(num);

        } catch (Exception e) {
            logger.error("getUserInfo:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }


    /*
     * (non-Javadoc)
     *
     * @see
     * com.edianniu.pscp.mis.service.dubbo.UserInfoService#logout(java.lang.Long,
     * java.lang.String)
     */
    @Override
    public Result logout(Long uid, String token) {
        Result result = new DefaultResult();
        if (uid == null) {
            result.setResultCode(ResultCode.ERROR_201);
            result.setResultMessage("uid不能为空");
            return result;
        }
        if (token == null) {
            result.setResultCode(ResultCode.ERROR_202);
            result.setResultMessage("token不能为空");
            return result;
        }
        UserInfo userInfo = userService.getUserInfo(uid);
        if (userInfo == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("uid不存在");
            return result;
        }
        if (!cachedService.clearLoginInfo(userInfo.getUid(), token.trim())) {
            result.setResultCode(ResultCode.ERROR_499);
            result.setResultMessage("用户未登录 / 用户登录超时");
        }
        return result;
    }
    @Override
    public Result isLogin(Long uid, String token) {
        Result result = new DefaultResult();
        try {
            if (uid == null || StringUtils.isBlank(token) || uid.intValue() <= 0) {
                result.setResultCode(ResultCode.ERROR_495);
                result.setResultMessage("您的账号未登录，请登录.");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null || userInfo.getStatus() == UserStatus.DISABLE.getValue()) {
                result.setResultCode(ResultCode.ERROR_496);
                result.setResultMessage("您的帐号被禁用了，请联系管理员。");
                return result;
            }
            if (userInfo.getStatus() != UserStatus.ENABLE.getValue()) {
                result.setResultCode(ResultCode.ERROR_497);
                result.setResultMessage("当前账号被禁用");
                return result;
            }
            if (!cachedService.isLogin(uid)) {//token 不存在
                result.setResultCode(ResultCode.ERROR_498);
                result.setResultMessage("您的账号登录超时，请重新登陆");
                return result;
            }
            if (!cachedService.isLogin(uid, token.trim())) {//token 存在，并且是异地被登录
                result.setResultCode(ResultCode.ERROR_499);
                result.setResultMessage("您的账号异地登陆，请重新登陆");
                return result;
            }

        } catch (Exception e) {
            logger.error("isLogin:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;

    }
    @Override
	public Result isLogin(Long uid, String token, String action) {
	    Result result=isLogin(uid, token);
	    if(result.isSuccess()){
	    	result=permissionInfoService.authc(uid, action);
	    }
		return result;
	}
    @Override
    public Result changePwd(Long uid, String newPwd, String oldPwd) {
        Result result = new DefaultResult();
        try {
            if (uid == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("UID不能为空");
                return result;
            }
            if (newPwd.equals(oldPwd)) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("新旧密码不能相同");
                return result;
            }
            if (StringUtils.isBlank(oldPwd)) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("原始密码不能为空");
                return result;
            }
            if (StringUtils.isBlank(newPwd)) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("新密码不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("UID不存在");
                return result;
            }
            if (!userInfo.getPasswd().equals(PasswordUtil.encode(oldPwd))) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("原始密码不正确");
                return result;
            }
            if (!BizUtils.checkPassword(newPwd)) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("密码格式不正确(6-20个字符，包括数字和字母以及特殊字符)");
                return result;
            }
            if (!userService.changePwd(userInfo.getUid(), userInfo.getMobile(),
                    PasswordUtil.encode(newPwd))) {
                result.setResultCode(ResultCode.ERROR_500);
                result.setResultMessage("系统异常");
                return result;
            }
        } catch (Exception e) {
            logger.error("change pwd:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }

    /**
     * 是否是企业电工
     *
     * @param uid
     * @return
     */
    @Override
    public ElectricianTypeResult isCompanyElectrician(Long uid) {
        ElectricianTypeResult result = new ElectricianTypeResult();
        try {
            if (uid == null || uid.intValue() <= 0) {
                result.setResultCode(ResultCode.ERROR_495);
                result.setResultMessage("您的账号未登录，请登录.");
                return result;
            }

            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null || userInfo.getStatus() == UserStatus.DISABLE.getValue()) {
                result.setResultCode(ResultCode.ERROR_496);
                result.setResultMessage("您的帐号被禁用了，请联系管理员。");
                return result;
            }
            // 默认企业电工
            result.setCompanyElectrician(userInfo.isCompanyElectrician());
        } catch (Exception e) {
            logger.error("isLogin:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }

    @Override
    public Result updateUser(UpdateUserReqData req) {
        Result result = new DefaultResult();
        try {
            if (req.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid 不能为空");
                return result;
            }
            if (StringUtils.isNotBlank(req.getTxImg())) {
                if (!BizUtils.checkLength(req.getTxImg(), 64)) {
                    result.setResultCode(ResultCode.ERROR_202);
                    result.setResultMessage("txImg 不能大于64个字符");
                    return result;
                }
            }
            if (StringUtils.isNotBlank(req.getNickName())) {
                if (!BizUtils.checkLength(req.getNickName(), 64)) {
                    result.setResultCode(ResultCode.ERROR_203);
                    result.setResultMessage("nickName 不能大于64个字符");
                    return result;
                }
            }
            if (req.getSex() != null && !Sex.include(req.getSex())) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("sex 只支持0,1,2");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(req.getUid());
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            userInfo.setNickName(req.getNickName());
            userInfo.setAge(req.getAge());
            userInfo.setSex(req.getSex());
            userInfo.setTxImg(req.getTxImg());
            userService.updateUserInfo(userInfo);
        } catch (Exception e) {
            logger.error("updateUser:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }

    @Override
    public RegisterResult register(RegisterReqData registerReqData) {
        RegisterResult result = new RegisterResult();
        try {
            if (!BizUtils.checkMobile(registerReqData.getMobile())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("手机号码输入错误");
                return result;
            }
            if (userService.existByMobile(registerReqData.getMobile().trim())) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("该手机号码已被注册过了");
                return result;
            }
            if (!BizUtils.checkPassword(registerReqData.getPasswd())) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("密码格式不正确(6-20个字符，包括数字和字母以及特殊字符)");
                return result;
            }
            if (!cachedService.checkMsgCode(registerReqData.getMsgcodeid(), registerReqData.getMsgcode())) {
                result.setResultCode(ResultCode.ERROR_404);
                result.setResultMessage("短信验证码不正确");
                return result;
            }
            CreateUserResult rs = userService.create(registerReqData.getMobile().trim(), PasswordUtil.encode(registerReqData.getPasswd().trim()));
            if (!rs.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_500);
                result.setResultMessage("系统异常");
                return result;
            } else {
                Map<String, String> param = new HashMap<>();
                param.put("mobile", BizUtils.getTailMobile(registerReqData.getMobile()));
                messageInfoService.sendSmsAndPushMessage(rs.getUid(), registerReqData.getMobile(), MessageId.REGISTER_SUCCESS, param);
                if (rs.isCoupon()) {
                    Map<String, String> param2 = new HashMap<>();
                    param2.put("amount", rs.getCouponFee());
                    messageInfoService.sendSmsAndPushMessage(rs.getUid(), registerReqData.getMobile(), MessageId.REGISTER_COUPON, param2);
                }
            }
            UserInfo userInfo = userService.getUserInfoByMobile(registerReqData.getMobile());
            if (userInfo != null) {
                LoginInfo loginInfo = setLoginStatus(userInfo, registerReqData);
                UserExtInfo userExtInfo = getUserExtInfo(userInfo);
                result.setCompanyInfo(userExtInfo.getCompanyInfo());
                result.setElectricianInfo(userExtInfo.getElectricianInfo());
                result.setUserInfo(userInfo);
                result.setToken(loginInfo.getToken());
                offlineMessage(userInfo);
            }
            
        } catch (Exception e) {
            logger.error("register:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }

        return result;
    }
    
    /**
     * 创建用户
     * @param mobile
     * @param passwd
     * @return
     */
    @Override
    public CreateUserResult createUser(String mobile, String passwd){
    	CreateUserResult result = new CreateUserResult();
    	try {
    		if (!BizUtils.checkMobile(mobile)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("手机号码输入错误");
                return result;
            }
            if (userService.existByMobile(mobile)) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("该手机号码已被注册过了");
                return result;
            }
            if (!BizUtils.checkLength(passwd, 64)) {
				result.set(ResultCode.ERROR_201, "密码不得为空或超过64个字");
				return result;
			}
    		result = userService.create(mobile, passwd);
		} catch (Exception e) {
			logger.error("save:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
		}
    	return result;
	}


    @Override
    public GetMsgCodeResult getMsgCode(String mobile, Integer type) {
        GetMsgCodeResult result = new GetMsgCodeResult();
        try {
            if (StringUtils.isBlank(mobile)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("手机号码不能为空");
                return result;
            }
            if (!BizUtils.checkMobile(mobile)) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("手机号码输入错误");
                return result;
            }
            if (type == Constants.GET_MSG_CODE_TYPE_REGISTER) {
                if (userService.existByMobile(mobile)) {
                    result.setResultCode(ResultCode.ERROR_403);
                    result.setResultMessage("手机号码已经注册过了");
                    return result;
                }
            } else if (type == Constants.GET_MSG_CODE_TYPE_RESETPWD) {
                if (!userService.existByMobile(mobile)) {
                    result.setResultCode(ResultCode.ERROR_403);
                    result.setResultMessage("手机号码尚未注册");
                    return result;
                }
            } else if (type == Constants.GET_MSG_CODE_TYPE_WITH_DRAWALS) {
                if (!userService.existByMobile(mobile)) {
                    result.setResultCode(ResultCode.ERROR_403);
                    result.setResultMessage("手机号码尚未注册");
                    return result;
                }
            } else if (type == Constants.GET_MSG_CODE_TYPE_ADD_BANKCARD) {
                /*if (!userService.existByMobile(mobile)) {
                    result.setResultCode(ResultCode.ERROR_403);
                    result.setResultMessage("手机号码尚未注册");
                    return result;
                }*/
            } else if (type == Constants.GET_MSG_CODE_TYPE_DEL_BANKCARD) {
               /* if (!userService.existByMobile(mobile)) {
                    result.setResultCode(ResultCode.ERROR_403);
                    result.setResultMessage("手机号码尚未注册");
                    return result;
                }*/
            } else if (type == Constants.GET_MSG_CODE_TYPE_CHANGE_MOBILE) {
            	if (userService.existByMobile(mobile)) {
                    result.setResultCode(ResultCode.ERROR_403);
                    result.setResultMessage("手机号码已经注册过了");
                    return result;
                }
			} else if (type == Constants.GET_MSG_CODE_TYPE_SET_SWITCH_PWD) {
				if (!userService.existByMobile(mobile)) {
                    result.setResultCode(ResultCode.ERROR_403);
                    result.setResultMessage("手机号码尚未注册");
                    return result;
                }
			} else {
                result.setResultCode(ResultCode.ERROR_406);
                result.setResultMessage("type 目前只支持0[注册]:1[重置密码]:2[提现]:3[绑定银行卡]:4[解绑银行卡]:5[更换绑定手机号码]:6[设置开关闸操作密码]");
                return result;
            }
            if (cachedService.isExist(CacheKey.CACHE_KEY_SMS_USER_GET_MSG_CODE + mobile)) {
                result.setResultCode(ResultCode.ERROR_404);
                result.setResultMessage("您操作过于频繁，请一分钟后再试");
                return result;
            }
            UUID uuids = UUID.randomUUID();
            String msgId = uuids.toString();
            String msgCode = MsgCodeUtils.getCode();

            if (cachedService.setMsgCode(msgId, msgCode)) {
                MessageId smsMsgId;
                if (type == Constants.GET_MSG_CODE_TYPE_REGISTER) {
                    smsMsgId = MessageId.REGISTER_CODE;
                } else if (type == Constants.GET_MSG_CODE_TYPE_RESETPWD) {
                    smsMsgId = MessageId.RETRIEVE_PASSWORD_CODE;
                } else if (type == Constants.GET_MSG_CODE_TYPE_ADD_BANKCARD) {
                    smsMsgId = MessageId.BIND_BANK_CARD_CODE;
                } else if (type == Constants.GET_MSG_CODE_TYPE_DEL_BANKCARD) {
                    smsMsgId = MessageId.UNBIND_BANK_CARD_CODE;
                } else if (type == Constants.GET_MSG_CODE_TYPE_WITH_DRAWALS){
                    smsMsgId = MessageId.WITH_DRAWALS_CODE;
                } else if (type == Constants.GET_MSG_CODE_TYPE_SET_SWITCH_PWD) {
					smsMsgId = MessageId.SET_SWITCH_PWD_CODE;
				} else {
                	smsMsgId = MessageId.CHANGE_MOBILE_CODE;
				}
                Map<String, String> smsParam = new HashMap<String, String>();
                smsParam.put("code", msgCode);
                PropertiesUtil config = new PropertiesUtil("app.properties");
                smsParam.put("contact_number", config.getProperty("contact.number"));
                SendMessageResult smsResult = messageInfoService.sendSmsMessage(null, mobile, smsMsgId, smsParam);
                if (smsResult.getResultCode() == ResultCode.SUCCESS) {
                    result.setMsgCodeId(msgId);
                    cachedService.set(CacheKey.CACHE_KEY_SMS_USER_GET_MSG_CODE + mobile, mobile, 60);
                } else {
                    result.setResultCode(ResultCode.ERROR_405);
                    result.setResultMessage("验证码发送异常");
                }
            } else {
                logger.warn("cache error");
                result.setResultCode(ResultCode.ERROR_500);
                result.setResultMessage("系统异常");
            }

        } catch (Exception e) {
            logger.error("get MsgCode:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }

    @Override
    public FeedbackResult feedback(FeedbackReqData feedbackReqData) {
        FeedbackResult result = new FeedbackResult();
        try {
            if (StringUtils.isBlank(feedbackReqData.getContent())) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("反馈内容不能为空");
                return result;
            }
            if (feedbackReqData.getContent().length() > 500) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("反馈内容最大长度为500字符");
                return result;
            }

            //获取用户信息
            UserInfo userInfo = userService.getUserInfo(feedbackReqData.getUid());
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("用户不存在");
            }

            // 插入用户反馈信息
            UserFeedback feedback = new UserFeedback();
            BeanUtils.copyProperties(feedbackReqData, feedback);
            feedback.setCreateUser(userInfo.getLoginName());
            this.userService.feedback(feedback);

        } catch (Exception e) {
            logger.error("getCenterInfo {}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
        }
        return result;
    }


    @Override
    public GetUserInfoResult getUserInfoByMobile(String mobile) {
        GetUserInfoResult result = new GetUserInfoResult();
        try {
            if (StringUtils.isBlank(mobile)) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("mobile不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfoByMobile(mobile);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("用户信息为空");
                return result;
            }
            UserExtInfo userExtInfo = getUserExtInfo(userInfo);
            result.setCompanyInfo(userExtInfo.getCompanyInfo());
            result.setElectricianInfo(userExtInfo.getElectricianInfo());
            result.setMemberInfo(userInfo);

        } catch (Exception e) {
            logger.error("getUserInfo:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }

    @Override
    public GetUserInfoResult getUserInfoByLoginName(String loginName) {
        GetUserInfoResult result = new GetUserInfoResult();
        try {
            if (StringUtils.isBlank(loginName)) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("loginName 不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfoByLoginName(loginName);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("用户信息为空");
                return result;
            }
            UserExtInfo userExtInfo = getUserExtInfo(userInfo);
            result.setCompanyInfo(userExtInfo.getCompanyInfo());
            result.setElectricianInfo(userExtInfo.getElectricianInfo());
            result.setMemberInfo(userInfo);
        } catch (Exception e) {
            logger.error("getUserInfo:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }


    @Override
    public LoginInfo getLoginInfo(Long uid) {
        return cachedService.getLoginInfo(uid);
    }
	@Override
	public GetMutiUserInfoResult getMutiUserInfoByUid(Long uid) {
		GetMutiUserInfoResult result = new GetMutiUserInfoResult();
        try {
            if (uid==null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid 不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("uid 不存在");
                return result;
            }
            ElectricianInfo electricaninfo = electricianService.getInfoByUid(userInfo.getUid());
            CompanyInfo companyInfo = companyService.getInfoByUserInfo(userInfo);
            MutiUserInfo mutiUserInfo=new MutiUserInfo();
            BeanUtils.copyProperties(userInfo, mutiUserInfo);
            if(electricaninfo!=null){
            	mutiUserInfo.setElectricianAuthStatus(electricaninfo.getAuthStatus());
            }
            if(companyInfo!=null){
            	mutiUserInfo.setCompanyMemberType(companyInfo.getMemberType());
            	mutiUserInfo.setCompanyAuthStatus(companyInfo.getAuthStatus());
            }
            result.setMutiUserInfo(mutiUserInfo);
        } catch (Exception e) {
            logger.error("getMutiUserInfoByUid:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }
	
	@Override
	public Boolean isUserExist(String mobile){
		return userService.existByMobile(mobile.trim());
	}
	
	@Override
	public Boolean checkSwitchpwd(Long uid, String switchpwd){
		if (null == uid) {
			return false;
		}
		if (StringUtils.isBlank(switchpwd)) {
			return false;
		}
		return userService.checkSwitchpwd(uid, PasswordUtil.encode(switchpwd));
	}
	
}
