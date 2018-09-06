package com.edianniu.pscp.cs.dao;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cs.bean.dutylog.DutyLogInfo;

public interface DutyLogDao extends BaseDao<DutyLogInfo>{

	int getDutyLogVOListCount(HashMap<String, Object> queryMap);

	List<DutyLogInfo> getDutyLogVOList(HashMap<String, Object> queryMap);

	DutyLogInfo getDutyLogInfo(Long id);

}
