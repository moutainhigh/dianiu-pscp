/**
 *
 */
package com.edianniu.pscp.mis.service.dubbo;


import com.edianniu.pscp.mis.bean.CreateUserResult;
import com.edianniu.pscp.mis.bean.ElectricianTypeResult;
import com.edianniu.pscp.mis.bean.GetMsgCodeResult;
import com.edianniu.pscp.mis.bean.GetMutiUserInfoResult;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.user.CheckSwitchPwdResult;
import com.edianniu.pscp.mis.bean.user.FeedbackReqData;
import com.edianniu.pscp.mis.bean.user.FeedbackResult;
import com.edianniu.pscp.mis.bean.user.GetUserCenterReq;
import com.edianniu.pscp.mis.bean.user.GetUserCenterResult;
import com.edianniu.pscp.mis.bean.user.LoginInfo;
import com.edianniu.pscp.mis.bean.user.LoginReqData;
import com.edianniu.pscp.mis.bean.user.LoginResult;
import com.edianniu.pscp.mis.bean.user.RegisterReqData;
import com.edianniu.pscp.mis.bean.user.RegisterResult;
import com.edianniu.pscp.mis.bean.user.UpdateUserReqData;

/**
 * @author cyl
 */
public interface UserInfoService {
	/**
	 * 获取登录用户信息
	 * @param uid
	 * @return
	 */
	public LoginInfo getLoginInfo(Long uid);
    /**
     * 用户注册
     *
     * @param registerReqData
     * @return
     */

    public RegisterResult register(RegisterReqData registerReqData);

    /**
     * 用户登录
     *
     * @param loginReqData
     * @return
     */
    public LoginResult login(LoginReqData loginReqData);

    /**
     * 重置密码
     * @param mobile
     * @param password
     * @param msgCodeId
     * @param msgCode
     * @return
     */
    public Result resetPwd(String mobile, String password, String msgCodeId,
                           String msgCode);
    
    /**
     * 更换绑定手机号码
     * @param mobile
     * @param newMobile
     * @param msgCodeId
     * @param msgCode
     * @param password
     * @return
     */
    Result changeMobile(String mobile, String newMobile, String msgCodeId, 
    					String msgCode, String password);

    /**
     * 获取用户信息
     *
     * @param uid
     * @return
     */
    public GetUserInfoResult getUserInfo(Long uid);

    /**
     *个人中心
     * @param getUserCenterReq
     * @return
     */
    public GetUserCenterResult getUserCenter(GetUserCenterReq getUserCenterReq);

    /**
     * 根据手机号码获取用户信息
     *
     * @param mobile
     * @return
     */
    public GetUserInfoResult getUserInfoByMobile(String mobile);
    
    /**
     * 根据登录名获取用户信息
     *
     * @param loginName
     * @return
     */
    public GetUserInfoResult getUserInfoByLoginName(String loginName);

    /**
     * 修改用户信息
     *
     * @param userInfo
     * @return
     */
    public Result updateUser(UpdateUserReqData req);

    /**
     * 注销
     *
     * @param uid
     * @param token
     * @return
     */
    public Result logout(Long uid, String token);

    /**
     * 是否登录状态
     * @param uid
     * @param token
     * @return
     */
    public Result isLogin(Long uid, String token);
    /**
     * 是否登录并且有权限
     * @param uid
     * @param token
     * @param action
     * @return
     */
    public Result isLogin(Long uid, String token,String action);

    /**
     * 修改密码
     *
     * @param uid
     * @param newPwd
     * @param oldPwd
     * @return
     */
    public Result changePwd(Long uid, String newPwd, String oldPwd);

    /**
     * 是否是企业电工
     *
     * @param uid
     * @return
     */
    public ElectricianTypeResult isCompanyElectrician(Long uid);
    /**
     * 获取组合用户信息
     * 1）用户基本信息
     * 2) 用户认证信息
     * 3) 用户其他信息
     * @param uid
     * @return
     */
    public GetMutiUserInfoResult  getMutiUserInfoByUid(Long uid);
    /**
     * 获取短信验证码
     *
     * @param mobile
     * @param type
     * @return
     */
    public GetMsgCodeResult getMsgCode(String mobile,Integer type);
    
    /**
	 * 用户反馈
	 *@param feedbackReqData
	 *@return
	 */
	FeedbackResult feedback(FeedbackReqData feedbackReqData);
	
	/**
	 * 判断用户是否存在
	 * @param mobile
	 * @return
	 */
	Boolean isUserExist(String mobile);
	
	/**
	 * 创建用户
	 * @param mobile  电话
	 * @param passwd  密码  经过MD5加密后的密码
	 * @return
	 */
	CreateUserResult createUser(String mobile, String passwd);
	
	/**
	 * 设置开关闸操作密码
	 * @param uid         用户memberid
	 * @param pwd         操作密码
	 * @param rePwd       确认操作密码
	 * @param msgcodeid
	 * @param msgcode
	 * @return
	 */
	Result setSwitchPwd(Long uid, String pwd, String rePwd, String msgcodeid, String msgcode);
	
	/**
	 * 验证开关闸操作密码
	 * @param uid        用户memberid
	 * @param switchpwd  操作密码
	 * @return
	 */
	Boolean checkSwitchpwd(Long uid, String switchpwd);
	
	/**
	 * 驗證操作密碼是否存在
	 * @param uid
	 * @return
	 */
	CheckSwitchPwdResult checkSwitchPwd(Long uid);
    
}
