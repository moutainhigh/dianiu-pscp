package com.edianniu.pscp.cms.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.bean.req.ChangeMemberStatusReq;
import com.edianniu.pscp.cms.commons.SaveOrUpdateUserResult;
import com.edianniu.pscp.cms.entity.MemberEntity;
import com.edianniu.pscp.mis.bean.GetMsgCodeResult;
import com.edianniu.pscp.mis.bean.Result;


/**
 * 会员服务
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface MemberService {
	
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
	MemberEntity queryByLoginName(String loginName);
	/**
	 * 根据手机号码，查询系统用户
	 * @param mobile
	 * @return
	 */
	MemberEntity queryByMobile(String mobile);
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
	MemberEntity queryObject(Long userId);
	
	/**
	 * 查询用户列表
	 */
	List<MemberEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存用户
	 */
	SaveOrUpdateUserResult save(MemberEntity user);
	
	/**
	 * 修改用户
	 */
	SaveOrUpdateUserResult update(MemberEntity user);
	
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
	public Result register( String userName, String passwd,String msgId,String msgCode);
	
	
	/**
	 * 重置密码
	 * @param userName    用户名
	 * @param newPasswd  新密码
	 * @param msgId    验证码id
	 * @param msgCode  验证码
	 */
	public Result resetPwd(String mobile,String password,String msgCodeId,String msgCode);
	
	
	/**
	 * 发送短信验证码
	 * @param mobile   手机号码
	 * @param type  类型
	 */
	public GetMsgCodeResult getMsgCode(String mobile,Integer type);
	
	/**
	 * 查询公司管理员
	 */
	public MemberEntity getCompanyAdmin(Long companyId);
	
	public Result changeMemberStatus(ChangeMemberStatusReq changeStatusReq);
}
