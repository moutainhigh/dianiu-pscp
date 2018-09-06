package com.edianniu.pscp.mis.service.dubbo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.mis.bean.user.QuerySysUserReq;
import com.edianniu.pscp.mis.bean.user.SysUserInfo;
import com.edianniu.pscp.mis.service.SysUserService;
import com.edianniu.pscp.mis.service.dubbo.SysUserInfoService;

/**
 * 系统用户
 * @author zhoujianjian
 * @date 2018年2月5日 下午5:39:35
 */
@Service
@Repository("sysUserInfoService")
public class SysUserInfoServiceImpl implements SysUserInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(SysUserInfoServiceImpl.class);
	
	@Autowired
	@Qualifier("sysUserService")
	private SysUserService sysUserService;

	@Override
	public List<SysUserInfo> getList(QuerySysUserReq req) {
		List<SysUserInfo> list = new ArrayList<>();
		try {
			HashMap<String, Object> map = new HashMap<>();
			map.put("memberAuditNotice", req.getMemberAuditNotice());
			map.put("needsAuditNotice", req.getNeedsAuditNotice());
			map.put("offLineNotice", req.getOffLineNotice());
			map.put("status", req.getStatus());
			list = sysUserService.getList(map);
		} catch (Exception e) {
			logger.error("系统异常{}", e);
		}
		return list;
	}
}
