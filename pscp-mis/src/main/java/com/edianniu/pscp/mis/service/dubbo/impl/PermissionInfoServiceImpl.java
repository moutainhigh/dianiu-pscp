package com.edianniu.pscp.mis.service.dubbo.impl;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.mis.bean.DefaultResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.user.LoginInfo;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.CachedService;
import com.edianniu.pscp.mis.service.PermissionService;
import com.edianniu.pscp.mis.service.dubbo.PermissionInfoService;
/**
 * PermissionInfoServiceImpl
 * @author yanlin.chen  yanlin.chen@edianniu.com
 * @version V1.0  2018年5月10日 下午5:37:53
 */
@Service
@Repository("permissionInfoService")
public class PermissionInfoServiceImpl implements PermissionInfoService {
	private static final Logger logger = LoggerFactory
            .getLogger(PermissionInfoServiceImpl.class);
	@Autowired
    @Qualifier("cachedService")
    private CachedService cachedService;
	@Autowired
	private PermissionService permissionService;
	@Override
	public Result authc(Long uid, String action) {//
		Result result=new DefaultResult();
		try{
			if(StringUtils.isBlank(action)){
				return result;
			}
			LoginInfo loginInfo=cachedService.getLoginInfo(uid);
			if(loginInfo==null){
				result.setResultCode(ResultCode.ERROR_495);
	            result.setResultMessage("您的账号登录超时，请重新登陆");
	            return result;
			}
			if(loginInfo.isFacilitatorApp()){
				//根据UID获取用户的权限列表
				//根据action去匹配用户的权限列表
				//匹配到了，有权限，匹配不到，无权限
				//select app_perms from pscp_sys_menu where id in(
				//		select menu_id from pscp_sys_role_menu where role_id in(select role_id from pscp_sys_member_role where user_id=1184 and deleted=0) and deleted=0
				//		)
				Set<String> permSet=permissionService.getPermissions(uid);
				if(permSet.isEmpty()||!permSet.contains(action)){
					result.setResultCode(ResultCode.ERROR_AUTH);
		            result.setResultMessage("当前帐号无权限操作，请联系管理员");
		            return result;
				}
			}
		}
		catch(Exception e){
			logger.error("login biz:{}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
		}
		
		return result;
	}

}
