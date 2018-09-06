package com.edianniu.pscp.portal.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.portal.commons.SaveOrUpdateUserResult;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.utils.JsonResult;


/**
 * 系统用户
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据登录名，查询系统用户
	 */
	SysUserEntity queryByLoginName(String loginName);
	/**
	 * 根据realName，查询系统用户
	 */
	SysUserEntity queryByRealName(String realName);
	/**
	 * 根据手机号码，查询系统用户
	 */
	SysUserEntity queryByMobile(String mobile);
	/**
	 * 根据公司名称，查询系统用户
	 */
	SysUserEntity queryByCompanyName(String companyName);
	
	/**
	 * 是否存在登录名
	 * @param loginName
	 * @return
	 */
	boolean isExistLoginName(String loginName);
	/**
	 * 是否存在手机号码
	 * @param mobile
	 * @return
	 */
	boolean isExistMobile(String mobile);
	
	/**
	 * 根据用户ID，查询用户
	 * @param userId
	 * @return
	 */
	SysUserEntity queryObject(Long userId);
	
	/**
	 * 查询用户列表
	 */
	List<SysUserEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存用户
	 */
	SaveOrUpdateUserResult save(SysUserEntity user);
	
	/**
	 * 修改用户
	 */
	SaveOrUpdateUserResult update(SysUserEntity user);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);
	
	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param passwd     原密码
	 * @param newPasswd  新密码
	 */
	int updatePasswd(Long userId, String passwd, String newPasswd);
	
	
	/**
	 * 注册
	 * @param userName    用户名
	 * @param newPasswd  新密码
	 * @param msgId    验证码id
	 * @param msgCode  验证码
	 */
	public JsonResult register( String userName, String passwd,String msgId,String msgCode);
	
	
	/**
	 * 重置密码
	 * @param userName    用户名
	 * @param newPasswd  新密码
	 * @param msgId    验证码id
	 * @param msgCode  验证码
	 */
	public JsonResult resetPwd(String mobile,String password,String msgCodeId,String msgCode);
	
	/**
	 * 更换绑定手机号码
	 * @param mobile     旧号码（即用户名userName）
	 * @param newMobile  新号码
	 * @param msgCodeId  验证码id
	 * @param msgCode    验证码
	 * @param psssword 
	 * @return
	 */
	JsonResult changeMobile(String mobile, String newMobile, String msgCodeId, String msgCode, String psssword);
	
	
	/**
	 * 发送短信验证码
	 * @param mobile   手机号码
	 * @param type  类型
	 */
	public JsonResult getMsgCode(String mobile,Integer type);
	
	/**
	 * 查询公司管理员
	 */
	public SysUserEntity getCompanyAdmin(Long companyId);
	/**
	 * 查询用户
	 * @param uid
	 * @return
	 */
	public SysUserEntity getByUid(Long uid);
	

	
}
