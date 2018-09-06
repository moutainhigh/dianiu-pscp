package com.edianniu.pscp.portal.controller.sys;

import com.edianniu.pscp.portal.commons.Result;
import com.edianniu.pscp.portal.controller.AbstractController;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.service.SysMemberRoleService;
import com.edianniu.pscp.portal.service.SysUserService;
import com.edianniu.pscp.portal.utils.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 系统用户
 *
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMemberRoleService sysMemberRoleService;

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(String loginName, String mobile, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", loginName);
        map.put("mobile", mobile);
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);
        Long companyId = 0L;
        if (this.getUser().getCompanyId() != null) {
            companyId = this.getUser().getCompanyId();
        }
        map.put("companyId", companyId);
        //查询列表数据
        if (companyId > 0) {
            List<SysUserEntity> userList = sysUserService.queryList(map);
            int total = sysUserService.queryTotal(map);

            PageUtils pageUtil = new PageUtils(userList, total, limit, page);

            return R.ok().put("page", pageUtil);
        } else {
            return R.ok();
        }

    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @RequestMapping("/passwd")
    public R passwd(String passwd, String newPasswd) {
        if (StringUtils.isBlank(newPasswd)) {
            return R.error("新密码不为能空");
        }

        //sha256加密
        //passwd = new Sha256Hash(passwd).toHex();
        passwd = PasswordUtil.encode(passwd);
        //sha256加密
        //newPasswd = new Sha256Hash(newPasswd).toHex();
        newPasswd = PasswordUtil.encode(newPasswd);

        //更新密码
        int count = sysUserService.updatePasswd(getUserId(), passwd, newPasswd);
        if (count == 0) {
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
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.queryObject(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysMemberRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUserEntity user) {
        if (StringUtils.isBlank(user.getMobile())) {
            return R.error("手机号不能为空");
        }
        if (!BizUtils.checkMobile(user.getMobile().trim())) {
            return R.error("手机号格式不正确");
        }
        if (StringUtils.isBlank(user.getRealName())) {
            return R.error("真实姓名不能为空");
        }
        if (!realNamePattern.matcher(user.getRealName()).matches()) {
            return R.error("真实姓名格式为2~7个中文字");
        }

        //后台增加默认为服务商
        user.setIsFacilitator(SysUserEntity.TAG_YES);
        user.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
        Result result = sysUserService.save(user);
        if (!result.isSuccess()) {
            return R.error(result.getMessage());
        }
        return R.ok();
    }

    /**
     * 修改用户
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity user) {
        if (StringUtils.isBlank(user.getMobile())) {
            return R.error("手机号不能为空");
        }

        sysUserService.update(user);

        return R.ok();
    }

    /**
     * 删除用户
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return R.ok();
    }

    /**
     * 注册
     */
    @RequestMapping("/register")
    @ResponseBody()
    public JsonResult register(String userName, String passwd, String msgId, String msgCode) {
        JsonResult json = sysUserService.register(userName, passwd, msgId, msgCode);
        return json;
    }
    
    /**
     * 找回密码
     */
    @RequestMapping("/resetPwd")
    @ResponseBody()
    public JsonResult resetPwd(String userName, String password, String msgCodeId, String msgCode) {
        JsonResult json = sysUserService.resetPwd(userName, password, msgCodeId, msgCode);
        return json;
    }
    
    /**
     * 更改绑定手机号（暂支持已认证的服务商）
     * userName即旧电话号码
     */
    @RequestMapping("/changeMobile")
    @ResponseBody()
    public JsonResult changeMobile(String userName, String newMobile, String msgCodeId, String msgCode, String pwd){
    	JsonResult json = sysUserService.changeMobile(userName, newMobile, msgCodeId, msgCode, pwd);
        return json;
    }

    /**
     * 获取短信验证码
     * mobile 接收验证码的手机号码
     * type 0[注册]; 1[重置密码]; 2[提现]; 3[绑定银行卡]; 4[解绑银行卡]; 5[更换绑定手机号码]
     */
    @RequestMapping("/getMsgCode")
    @ResponseBody()
    public JsonResult getMsgCode(String mobile, Integer type) {
    	JsonResult json = new JsonResult();
    	// 如果是更换手机号码，需先作验证（暂支持已认证的服务商）
    	if (Constant.GET_MSG_CODE_TYPE_CHANGE_MOBILE == type) {
    		
    		/*测试使用*/
    		Long uid = 1389L;
    		SysUserEntity userEntity = sysUserService.getByUid(uid);
    		
    		//SysUserEntity userEntity = ShiroUtils.getUserEntity();
        	// 判断是否为已认证的服务商
        	boolean isFacilitator = userEntity.isFacilitator();
        	if (!isFacilitator) {
        		json.setSuccess(false);
        		json.setMsg("仅认证通过的服务商可更换手机号码");
        		return json;
    		}
        	// 验证新旧号码是否相同
        	if (userEntity.getMobile().compareTo(mobile.trim()) == 0) {
        		json.setSuccess(false);
        		json.setMsg("新旧号码相同");
        		return json;
    		}
		}
        json = sysUserService.getMsgCode(mobile, type);
        return json;
    }
    
    /**
     * 真实姓名正则(2-7个中文字)
     */
    private Pattern realNamePattern = Pattern.compile("[\u4E00-\u9FA5]{2,7}");
}
