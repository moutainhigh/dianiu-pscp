package com.edianniu.pscp.mis.service.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.mis.bean.user.SysUserInfo;
import com.edianniu.pscp.mis.dao.SysUserDao;
import com.edianniu.pscp.mis.service.SysUserService;

@Service
@Repository("sysUserService")
public class DefaultSysUserService implements SysUserService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultUserService.class);
	
	@Autowired
	private SysUserDao sysUserDao;

	@Override
	public List<SysUserInfo> getList(HashMap<String, Object> map) {
		return sysUserDao.queryList(map);
	}
}
