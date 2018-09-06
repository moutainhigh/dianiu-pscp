package com.edianniu.pscp.mis.service;
import com.edianniu.pscp.mis.bean.CreateUserResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.domain.UserFeedback;
import com.edianniu.pscp.mis.exception.BusinessException;

public interface UserService {
	
	/**
     * 获取用户信息   
     * @param mobile
     * @return
     */
	public UserInfo getUserInfoByMobile(String mobile);
	/**
	 * 获取用户信息
	 * @param loginName
	 * @return
	 */
	public UserInfo getUserInfoByLoginName(String loginName);
	
	/**
	 * 获取用户信息
	 * @param companyName
	 * @return
	 */
	public UserInfo getUserInfoByCompanyName(String companyName);
	
    /**
     * 获取用户信息(status in (0,1))
     * @param companyId
     * @param mobile
     * @return
     */
	public UserInfo getUserInfo(Long companyId,String mobile);
	/**
	 * 获取服务商管理员信息
	 * @param companyId
	 * @return
	 */
	public UserInfo getFacilitatorAdmin(Long companyId);
	/**
	 * 获取服务商管理员uid
	 * @param companyId
	 * @return
	 */
	public Long getFacilitatorAdminUid(Long companyId);
	
    /**
     * 获取用户信息(status=0)
     * @param companyId
     * @param mobile
     * @return
     */
	public UserInfo getSimpleUserInfo(Long companyId,String mobile);
    /**
     * 获取用户信息(status=0)
     * @param uid
     * @return
     */
	public UserInfo getUserInfo(Long uid);
    /**
     * 获取用户信息(status=0)
     * @param uid
     * @return
     */
	public UserInfo getSimpleUserInfo(Long uid);
	/**
	 * 判断手机号码是否存在(status=0,1)
	 * @param mobile
	 * @return
	 */
	public boolean existByMobile(String mobile);

	/**
	 * 创建用户
	 * @param mobile
	 * @param passwd
	 * @param registerIp
	 * @param companyId
	 * @param terminalType
	 * @return
	 */
	public CreateUserResult create(String mobile, String passwd) throws BusinessException;

	public boolean changePwd(Long uid, String opUser, String passwd);
	
	/**
	 * 修改绑定手机号码
	 * @param uid
	 * @param mobile
	 * @param newMobile
	 * @return
	 */
	public boolean changeMobile(Long uid, String mobile, String newMobile);

	public void updateUserInfo(UserInfo userInfo);
	
	void feedback(UserFeedback feedback);
	
	/**
	 * 设置开关闸操作密码
	 * @param uid
	 * @param encode
	 */
	public void setSwitchPwd(Long uid, String encode);
	
	/**
	 * 验证操作密码
	 * @param uid
	 * @param encode
	 * @return
	 */
	public Boolean checkSwitchpwd(Long uid, String encode);
	
	
	
	
	
}
