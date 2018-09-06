package com.edianniu.pscp.portal.service.impl;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.GetMsgCodeResult;
import com.edianniu.pscp.mis.bean.GetMutiUserInfoResult;
import com.edianniu.pscp.mis.bean.company.CompanyAuthStatus;
import com.edianniu.pscp.mis.bean.electrician.ElectricianAuthStatus;
import com.edianniu.pscp.mis.bean.user.MutiUserInfo;
import com.edianniu.pscp.mis.bean.user.RegisterReqData;
import com.edianniu.pscp.mis.bean.user.RegisterResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.portal.commons.ResultCode;
import com.edianniu.pscp.portal.commons.SaveOrUpdateUserResult;
import com.edianniu.pscp.portal.dao.MemberWalletDao;
import com.edianniu.pscp.portal.dao.SysUserDao;
import com.edianniu.pscp.portal.entity.MemberWalletEntity;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.service.SysMemberRoleService;
import com.edianniu.pscp.portal.service.SysUserService;
import com.edianniu.pscp.portal.utils.JsonResult;
import com.edianniu.pscp.portal.utils.PasswordUtil;
import com.edianniu.pscp.portal.utils.ShiroUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 系统用户
 *
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 上午15:20:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysMemberRoleService sysMemberRoleService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MemberWalletDao memberWalletDao;
    @Autowired
    @Qualifier("messageInfoService")
    private MessageInfoService messageInfoService;
    

    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserDao.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserDao.queryAllMenuId(userId);
    }

    @Override
    public SysUserEntity queryByLoginName(String loginName) {
        return sysUserDao.queryByLoginName(loginName);
    }
    
    @Override
    public SysUserEntity queryByRealName(String realName){
    	return sysUserDao.queryByRealName(realName);
    }
    
    @Override
    public SysUserEntity queryByMobile(String mobile) {
        return sysUserDao.queryByMobile(mobile);
    }
    
    @Override
	public SysUserEntity queryByCompanyName(String companyName) {
		return sysUserDao.queryByCompanyName(companyName);
	}

    @Override
    public SysUserEntity queryObject(Long userId) {
        return sysUserDao.queryObject(userId);
    }

    @Override
    public List<SysUserEntity> queryList(Map<String, Object> map) {
        return sysUserDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysUserDao.queryTotal(map);
    }

    @Override
    @Transactional
    public SaveOrUpdateUserResult save(SysUserEntity user) {
        SaveOrUpdateUserResult result = new SaveOrUpdateUserResult();
        if (isExistMobile(user.getMobile())) {
            SysUserEntity existUser = this.queryByMobile(user.getMobile());
            if (StringUtils.isBlank(existUser.getRealName())) {
                existUser.setRealName(user.getRealName());
            }
            if (existUser.getCompanyId().compareTo(ShiroUtils.getUserEntity().getCompanyId()) == 0) {//已经是服务商帐号
                if (existUser.isFacilitator() || existUser.isAdmin()) {
                	result.set(ResultCode.EXIST_MOBILE, "手机号重复");
                    return result;
                } else {
                    existUser.setIsFacilitator(SysUserEntity.TAG_YES);
                    existUser.setRoleIdList(user.getRoleIdList());
                    existUser.setPasswd(null);//不修改密码
                    return update(existUser);
                }
            } else if (existUser.getCompanyId().compareTo(new Long(0)) == 0) {//平台帐号 社会电工，认证中的电工，认证中的服务商，认证中的客户不能添加
            	GetMutiUserInfoResult getMutiUserInfoResult=userInfoService.getMutiUserInfoByUid(existUser.getUserId());
                if(getMutiUserInfoResult.isSuccess()){
                	MutiUserInfo mutiUserInfo=getMutiUserInfoResult.getMutiUserInfo();
                	if(!(mutiUserInfo.getIsElectrician()==SysUserEntity.TAG_YES||
                	    mutiUserInfo.getCompanyAuthStatus()==CompanyAuthStatus.AUTHING.getValue()||
                	    mutiUserInfo.getElectricianAuthStatus()==ElectricianAuthStatus.AUTHING.getValue())){//社会电工
                		existUser.setIsFacilitator(SysUserEntity.TAG_YES);
                        existUser.setPasswd(null);//不修改密码
                        existUser.setRoleIdList(user.getRoleIdList());
                        existUser.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
                        return update(existUser);
                	}
                	else{
                		result.set(ResultCode.ERROR,"手机号被使用了");
                    	return result;
                	}
                }
                else{
                	result.set(getMutiUserInfoResult.getResultCode(),getMutiUserInfoResult.getResultMessage());
                	return result;
                }
            }
            else{//其他服务商帐号/子帐号/或者客户帐号，无法添加
            	result.set(ResultCode.ERROR,"手机号被使用了");
            	return result;
            }
        }
        else{
        	// 生成6位随机数字
            String passwd = String.valueOf(getRandNum());
            user.setLoginName(user.getMobile());
            user.setCreateTime(new Date());
            user.setPasswd(PasswordUtil.encode(passwd));
            sysUserDao.save(user);
            //添加用户资金信息
            MemberWalletEntity wallet = new MemberWalletEntity();
            wallet.setAmount(0D);
            wallet.setFreezingAmount(0D);
            wallet.setUid(user.getUserId());
            wallet.setCreateUser(ShiroUtils.getUserEntity().getLoginName());
            memberWalletDao.save(wallet);
            //保存用户与角色关系
            sysMemberRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
            result.setUser(user);
            // 发送短信消息
            Map<String, String> params = new HashMap<>();
            params.put("mobile", user.getMobile());
            params.put("code", passwd);
            try {
                messageInfoService.sendSmsMessage(user.getUserId(), user.getMobile(), MessageId.NEW_PASSWORD_CODE, params);
            } catch (Exception e) {
                logger.error(String.format("发送给%s的短信失败", user.getMobile()), e);
            }
            return result;
        }

        
    }

    @Override
    @Transactional
    public SaveOrUpdateUserResult update(SysUserEntity user) {
        SaveOrUpdateUserResult result = new SaveOrUpdateUserResult();
        if (StringUtils.isBlank(user.getPasswd())) {
            user.setPasswd(null);
        } else {
            user.setPasswd(PasswordUtil.encode(user.getPasswd()));
            //user.setPasswd(new Sha256Hash(user.getPasswd()).toHex());
        }
        sysUserDao.update(user);

        //保存用户与角色关系
        sysMemberRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
        result.setUser(user);
        return result;
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] userIds) {
        for (Long userId : userIds) {
            SysUserEntity user = this.queryObject(userId);
            if (user != null) {
                user.setIsFacilitator(0);
                if (!user.isElectrician()) {
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
        return sysUserDao.updatePassword(map);
    }

    @Override
    public boolean isExistLoginName(String loginName) {
        return sysUserDao.getCountByLoginName(loginName) > 0 ? true : false;
    }

    @Override
    public boolean isExistMobile(String mobile) {
        return sysUserDao.getCountByMobile(mobile) > 0 ? true : false;
    }


    @Override
    public JsonResult register(String userName, String passwd, String msgId, String msgCode) {
        RegisterReqData data = new RegisterReqData();
        data.setMobile(userName);
        data.setPasswd(passwd);
        data.setMsgcode(msgCode);
        data.setMsgcodeid(msgId);
        RegisterResult result = userInfoService.register(data);

        JsonResult json = new JsonResult();
        if (result.isSuccess()) {

            UserInfo userInfo = result.getUserInfo();
            SysUserEntity user = new SysUserEntity();
            BeanUtils.copyProperties(userInfo, user);
            user.setUserId(userInfo.getUid());
            //设置客户和服务商角色
            List<Long> roleList = new ArrayList<Long>();
            roleList.add(1000L);
            roleList.add(1010L);
            user.setRoleIdList(roleList);
            update(user);
            //注册即登录
            Subject subject = ShiroUtils.getSubject();
            String password = PasswordUtil.encode(passwd);
            UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getLoginName(), password);
            subject.login(token);
            json.setObject(result);
            json.setSuccess(true);
        } else {
            json.setMsg(result.getResultMessage());
            json.setSuccess(false);
            json.setObject(result);
        }
        return json;
    }

    @Override
    public JsonResult resetPwd(String mobile, String password, String msgCodeId, String msgCode) {
        JsonResult json = new JsonResult();
        com.edianniu.pscp.mis.bean.Result result = userInfoService.resetPwd(mobile, password, msgCodeId, msgCode);

        if (result.isSuccess()) {
            json.setSuccess(true);
        } else {
            json.setMsg(result.getResultMessage());
            json.setSuccess(false);
            json.setObject(result);
        }
        return json;
    }
    
    @Override
	public JsonResult changeMobile(String mobile, String newMobile, String msgCodeId, String msgCode, String password) {
		JsonResult json = new JsonResult();
		com.edianniu.pscp.mis.bean.Result result = userInfoService.changeMobile(mobile, newMobile, msgCodeId, msgCode, password);
        if (result.isSuccess()) {
            json.setSuccess(true);
            // 退出登录
            ShiroUtils.logout();
        } else {
            json.setMsg(result.getResultMessage());
            json.setSuccess(false);
            json.setObject(result);
        }
		return json;
	}

    @Override
    public JsonResult getMsgCode(String mobile, Integer type) {
        GetMsgCodeResult result = userInfoService.getMsgCode(mobile, type);
        JsonResult json = new JsonResult();
        if (result.isSuccess()) {
            json.setSuccess(true);
            json.setMsg(result.getMsgCodeId());
            json.setObject(result);
        } else {
            json.setSuccess(false);
            json.setMsg(result.getResultMessage());
        }
        return json;
    }

    @Override
    public SysUserEntity getCompanyAdmin(Long companyId) {
        SysUserEntity bean = sysUserDao.getCompanyAdmin(companyId);
        return bean;
    }

    @Override
    public SysUserEntity getByUid(Long uid) {
        return sysUserDao.queryObject(uid);
    }

    /**
     * 获取6位随机数
     *
     * @return
     */
    private static int getRandNum() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }


}
