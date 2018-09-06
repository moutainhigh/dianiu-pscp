package com.edianniu.pscp.mis.service.dubbo;

import java.util.List;

import com.edianniu.pscp.mis.bean.user.QuerySysUserReq;
import com.edianniu.pscp.mis.bean.user.SysUserInfo;

/**
 * 系统用户
 */
public interface SysUserInfoService {
	
	/**
	 * 获取系统会员列表
	 * @param req
	 * @return
	 */
	public List<SysUserInfo> getList(QuerySysUserReq req);
	
	
}
