package com.edianniu.pscp.cms.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.edianniu.pscp.cms.bean.req.ChangeMemberStatusReq;
import com.edianniu.pscp.cms.controller.AbstractController;
import com.edianniu.pscp.cms.entity.MemberEntity;
import com.edianniu.pscp.cms.service.MemberMemberRoleService;
import com.edianniu.pscp.cms.service.MemberService;
import com.edianniu.pscp.cms.utils.DateUtils;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.PasswordUtil;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.cms.utils.ShiroUtils;
import com.edianniu.pscp.mis.bean.GetMsgCodeResult;
import com.edianniu.pscp.mis.bean.Result;

/**
 * 会员
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 上午10:40:10
 */
@RestController
@RequestMapping("/member/user")
public class MemberUserController extends AbstractController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberMemberRoleService memberMemberRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("member:user:list")
	public R list(Integer page, Integer limit,
			String mobile,String loginName,
			String memberName,String createTimeStart,String createTimeEnd){
		Map<String, Object> map = new HashMap<>();

		map.put("mobile", mobile);
		map.put("memberName", memberName);
		map.put("loginName", loginName);
		if(StringUtils.isNotBlank(createTimeStart))
		map.put("createTimeStart", DateUtils.parse(createTimeStart+" 00:00:00", DateUtils.DATE_TIME_PATTERN));
		if(StringUtils.isNotBlank(createTimeEnd))
		map.put("createTimeEnd", DateUtils.parse(createTimeEnd+" 23:59:59", DateUtils.DATE_TIME_PATTERN));

		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		// 查询列表数据
		List<MemberEntity> userList = memberService.queryList(map);
		int total = memberService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(userList, total, limit, page);

		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@RequestMapping("/passwd")
	public R passwd(String passwd, String newPasswd){
		if(StringUtils.isBlank(newPasswd)){
			return R.error("新密码不为能空");
		}
		
		//sha256加密
		//passwd = new Sha256Hash(passwd).toHex();
		passwd=PasswordUtil.encode(passwd);
		//sha256加密
		//newPasswd = new Sha256Hash(newPasswd).toHex();
		newPasswd=PasswordUtil.encode(newPasswd);
				
		//更新密码
		int count = memberService.updatePasswd(getUserId(), passwd, newPasswd);
		if(count == 0){
			return R.error("原密码不正确");
		}
		
		//退出
		ShiroUtils.logout();
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("member:user:info")
	public R info(@PathVariable("userId") Long userId){
		MemberEntity user = memberService.queryObject(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = memberMemberRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	
	/**
	 * 修改用户
	 */
	@RequestMapping("/changeStatus")
	@RequiresPermissions("member:user:changeStatus")
	public R changeStatus(@RequestBody ChangeMemberStatusReq req){
		Result result=memberService.changeMemberStatus(req);
		if(result.isSuccess()){
			return R.ok();
		}
		return R.error(result.getResultMessage());
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("member:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		memberService.deleteBatch(userIds);
		
		return R.ok();
	}
	
	/**
	 * 注册
	 * 
	 */
	@RequestMapping("/register")
	@ResponseBody()
	public R register( String userName, String passwd,String msgId,String msgCode){
		Result result=memberService.register(userName, passwd, msgId, msgCode);
		if(result.isSuccess()){
			return R.ok();
		}
		return R.error(result.getResultMessage());
		
	}
	/**
	 * 获取短信验证码
	 * 
	 */
	@RequestMapping("/getMsgCode")
	@ResponseBody()
	public R getMsgCode(String mobile ,Integer type){
		GetMsgCodeResult result=memberService.getMsgCode(mobile, type);
		if(result.isSuccess()){
			R.ok().put("msgCodeId", result.getMsgCodeId());
		}
		return R.error(result.getResultMessage());
		
	}
	/**
	 * 找回密码
	 * 
	 */
	@RequestMapping("/resetPwd")
	@ResponseBody()
	public R resetPwd(String userName,String password,String msgCodeId,String msgCode){
		Result result=memberService.resetPwd(userName, password, msgCodeId, msgCode);
		if(result.isSuccess()){
			R.ok();
		}
		return R.error(result.getResultMessage());
		
	}
}
