package com.edianniu.pscp.mis.service;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.mis.bean.user.SysUserInfo;

public interface SysUserService {

	List<SysUserInfo> getList(HashMap<String, Object> map);

}
