package com.edianniu.pscp.cms.controller.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import com.edianniu.pscp.cms.controller.AbstractController;
import com.edianniu.pscp.cms.entity.SysUserEntity;
import com.edianniu.pscp.cms.service.SysUserRoleService;
import com.edianniu.pscp.cms.service.SysUserService;
import com.edianniu.pscp.cms.utils.BizUtils;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.cms.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月16日 下午4:38:59 
 * @version V1.0
 */
@RestController
@RequestMapping("/sys/user")
public class UserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(String loginName,String mobile,String username, Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("username", username);
		map.put("loginName", loginName);
		map.put("mobile", mobile);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<SysUserEntity> userList = sysUserService.queryList(map);
		int total = sysUserService.queryTotal(map);
		
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
	public R password(String passwd, String newPasswd){
		if(StringUtils.isBlank(passwd)){
			return R.error("原密码不为能空");
		}
		if(StringUtils.isBlank(newPasswd)){
			return R.error("新密码不为能空");
		}
		//sha256加密
		passwd = new Sha256Hash(passwd).toHex();
		//sha256加密
		newPasswd = new Sha256Hash(newPasswd).toHex();
		//更新密码
		int count = sysUserService.updatePasswd(getUserId(), passwd, newPasswd);
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
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.queryObject(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 * 用户名不能为空
	 * 登录帐号不能为空
	 * 登录帐号不能重复
	 * 手机号码不能为空
	 * 手机号码不能重复
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		if(StringUtils.isBlank(user.getUsername())){
			return R.error("用户名不能为空");
		}
		if(StringUtils.isBlank(user.getLoginName())){
			return R.error("登录帐号不能为空");
		}
		if(StringUtils.isBlank(user.getMobile())){
			return R.error("手机号不能为空");
		}
		if(StringUtils.isBlank(user.getPasswd())){
			return R.error("密码不能为空");
		}
		if(!BizUtils.checkMobile(user.getMobile())){
			return R.error("手机号格式错误");
		}
		if(!BizUtils.checkLength(user.getUsername(), 64)){
			return R.error("用户名不能超过64个字符");
		}
		if(!BizUtils.checkLength(user.getLoginName(), 20)){
			return R.error("用户名不能超过64个字符");
		}
		if(!BizUtils.checkPassword(user.getPasswd())){
			return R.error("密码格式错误");
		}
		if(sysUserService.isExistLoginName(user.getLoginName())){
			return R.error("登录帐号已经存在");
		}
		if(sysUserService.isExistMobile(user.getMobile())){
			return R.error("手机号已被使用了");
		}
		Integer needsAuditNotice = user.getNeedsAuditNotice() == null ? 0 : user.getNeedsAuditNotice();
		Integer memberAuditNotice = user.getMemberAuditNotice() == null ? 0 : user.getMemberAuditNotice();
		Integer offLineNotice = user.getOffLineNotice() == null ? 0 : user.getOffLineNotice();
		user.setNeedsAuditNotice(needsAuditNotice);
		user.setMemberAuditNotice(memberAuditNotice);
		user.setOffLineNotice(offLineNotice);
		sysUserService.save(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		if(user.getUserId()==null){
			return R.error("userId不能为空");
		}
		if(StringUtils.isBlank(user.getUsername())){
			return R.error("用户名不能为空");
		}
		if(StringUtils.isBlank(user.getLoginName())){
			return R.error("登录帐号不能为空");
		}
		if(StringUtils.isBlank(user.getMobile())){
			return R.error("手机号不能为空");
		}
		SysUserEntity sysUser=sysUserService.getByUserId(user.getUserId());
		if(sysUser==null){
			return R.error("用户不存在");
		}
		
		if(!sysUser.getLoginName().equals(user.getLoginName())){
			
			return R.error("登录帐号不能修改");
		}
		if(!sysUser.getMobile().equals(user.getMobile())){
			if(sysUserService.isExistMobile(user.getMobile())){
				return R.error("手机号已被使用了");
			}
		}
		Integer needsAuditNotice = user.getNeedsAuditNotice() == null ? 0 : user.getNeedsAuditNotice();
		Integer memberAuditNotice = user.getMemberAuditNotice() == null ? 0 : user.getMemberAuditNotice();
		Integer offLineNotice = user.getOffLineNotice() == null ? 0 : user.getOffLineNotice();
		user.setNeedsAuditNotice(needsAuditNotice);
		user.setMemberAuditNotice(memberAuditNotice);
		user.setOffLineNotice(offLineNotice);
		sysUserService.update(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}
}
