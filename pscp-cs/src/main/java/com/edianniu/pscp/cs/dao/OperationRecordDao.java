package com.edianniu.pscp.cs.dao;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cs.bean.operationrecord.OperationRecordInfo;

public interface OperationRecordDao extends BaseDao<OperationRecordInfo>{

	int getOperationRecordInfoListCount(HashMap<String, Object> queryMap);

	List<OperationRecordInfo> getOperationRecordInfoList(HashMap<String, Object> queryMap);
	
	OperationRecordInfo getOperationRecordInfo(Long id);

}
