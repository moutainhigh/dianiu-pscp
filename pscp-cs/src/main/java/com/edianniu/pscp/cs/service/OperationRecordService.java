package com.edianniu.pscp.cs.service;

import com.edianniu.pscp.cs.bean.operationrecord.ListReqData;
import com.edianniu.pscp.cs.bean.operationrecord.SaveReqData;
import com.edianniu.pscp.cs.bean.operationrecord.vo.OperationRecordVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;

/**
 * @author zhoujianjian
 * @date 2017年10月18日 下午5:35:09
 */
public interface OperationRecordService {
	
	void save(SaveReqData saveReqData, UserInfo userInfo);

	PageResult<OperationRecordVO> getOperationRecordVOList(ListReqData listReqData, UserInfo userInfo);

	OperationRecordVO getOperationRecordVO(Long id);

	void deleteOperationRecord(Long id, UserInfo userInfo);

	Boolean isExistByRoomIdAndCompanyId(Long roomId, Long companyId);

}
