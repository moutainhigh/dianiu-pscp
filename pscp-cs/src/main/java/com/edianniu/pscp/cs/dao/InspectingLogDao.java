package com.edianniu.pscp.cs.dao;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cs.bean.inspectinglog.InspectingLogInfo;

public interface InspectingLogDao extends BaseDao<InspectingLogInfo>{

	int getInspectingLogVOListCount(HashMap<String, Object> queryMap);

	List<InspectingLogInfo> getInspectingLogVOList(HashMap<String, Object> queryMap);

}
